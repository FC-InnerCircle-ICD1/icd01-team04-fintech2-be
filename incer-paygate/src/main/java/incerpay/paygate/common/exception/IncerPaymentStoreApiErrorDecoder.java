package incerpay.paygate.common.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@Component
public class IncerPaymentStoreApiErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            String errorMessage = "";

            if (response.body() != null) {
                errorMessage = new BufferedReader(
                        new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
            }

            return new IncerPaymentStoreApiFeignException(errorMessage, response.status());

        } catch (IOException | RuntimeException e) {
            return new RuntimeException("IncerPaymentStoreApi error occurred");
        }
    }
}
