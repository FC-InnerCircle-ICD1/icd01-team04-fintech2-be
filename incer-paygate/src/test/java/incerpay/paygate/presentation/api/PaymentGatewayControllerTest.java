package incerpay.paygate.presentation.api;

import incerpay.paygate.domain.enumeration.PaymentState;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiDataView;
import incerpay.paygate.infrastructure.internal.IncerPaymentApi;
import incerpay.paygate.infrastructure.internal.IncerPaymentStoreApi;
import incerpay.paygate.infrastructure.internal.dto.IncerPaymentApiView;
import incerpay.paygate.presentation.dto.in.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentGatewayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        initMockAuthSeller();
        initMockPaymentApi();
    }

    private static final String SELLER_KEY = "test_c3e5c018926140c19e7a35f14ab6e99f";
    private static final String AUTHORIZATION_HEADER = "Bearer " + SELLER_KEY;
    private static final String API_KEY_STATE_HEADER = "TEST";
    private static final String CLIENT_ID_HEADER = "1";
    private static final int RANDOM_PAYMENT_ID = new Random().nextInt(100000);


    @Test
    public void 결제요청_성공() throws Exception {
        String jsonContent = readJson("src/test/resources/paygate/CardPaymentRequest.json");

        mockMvc.perform(post("/payment/request")
                        .header("Authorization", AUTHORIZATION_HEADER)
                        .header("X-Api-Key-State", API_KEY_STATE_HEADER)
                        .header("X-Client-Id", CLIENT_ID_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void 결제승인_성공() throws Exception {
        String jsonContent = readJson("src/test/resources/paygate/CardPaymentApprove.json");

        mockMvc.perform(post("/payment/" + RANDOM_PAYMENT_ID + "/confirm")
                        .header("Authorization", AUTHORIZATION_HEADER)
                        .header("X-Api-Key-State", API_KEY_STATE_HEADER)
                        .header("X-Client-Id", CLIENT_ID_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void 결제취소_성공() throws Exception {
        String jsonContent = readJson("src/test/resources/paygate/CardPaymentCancel.json");

        mockMvc.perform(put("/payment/" + RANDOM_PAYMENT_ID + "/cancel")
                        .header("Authorization", AUTHORIZATION_HEADER)
                        .header("X-Api-Key-State", API_KEY_STATE_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void 결제환불_성공() throws Exception {
        String jsonContent = readJson("src/test/resources/paygate/CardPaymentReject.json");

        mockMvc.perform(put("/payment/" + RANDOM_PAYMENT_ID + "/reject")
                        .header("Authorization", AUTHORIZATION_HEADER)
                        .header("X-Api-Key-State", API_KEY_STATE_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }


    private String readJson(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(path)));
    }


    @MockBean
    private IncerPaymentApi incerPaymentApi;

    @MockBean
    private IncerPaymentStoreApi incerPaymentStoreApi;

    private void initMockAuthSeller() {
        when(incerPaymentStoreApi.getApiKeyInfo(anyLong(), anyString(), any()))
                .thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
    }


    // Mock 설정 함수
    private void initMockPaymentApi() {

        when(incerPaymentApi.request(any(IncerPaymentApiRequestCommand.class)))
                .thenReturn(createMockApiView(200, "OK", UUID.randomUUID(), "12345", PaymentState.PENDING, 10000L));

        when(incerPaymentApi.approve(any(IncerPaymentApiApproveCommand.class)))
                .thenReturn(createMockApiView(200, "OK", UUID.randomUUID(), "12345", PaymentState.APPROVED, 10000L));

        when(incerPaymentApi.cancel(any(IncerPaymentApiCancelCommand.class)))
                .thenReturn(createMockApiView(200, "OK", UUID.randomUUID(), "12345", PaymentState.REJECTED, 10000L));

        when(incerPaymentApi.reject(any(IncerPaymentApiRejectCommand.class)))
                .thenReturn(createMockApiView(200, "OK", UUID.randomUUID(), "12345", PaymentState.REJECTED, 10000L));

    }
    private IncerPaymentApiView createMockApiView(int resultCode, String resultMsg,
                                                  UUID paymentId, String sellerId, PaymentState state, Long price) {
        IncerPaymentApiDataView dataView = new IncerPaymentApiDataView(paymentId, sellerId, state, price);
        return new IncerPaymentApiView(resultCode, resultMsg, dataView);
    }


}

