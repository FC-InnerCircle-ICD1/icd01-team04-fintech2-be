package incerpay.paygate.infrastructure.external;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import incerpay.paygate.domain.enumeration.PaymentState;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiDataView;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentMessageData;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentSuccessData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class IncerPaymentApiDataViewDeserializer extends JsonDeserializer<IncerPaymentApiDataView> {

    @Override
    public IncerPaymentApiDataView deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (!node.asText().equals("")) {
            return new IncerPaymentMessageData(node.toString());
        }

        return new IncerPaymentSuccessData(
                UUID.fromString(node.get("paymentId").asText()),
                node.get("sellerId").asText(),
                parsePaymentState(node),
                node.get("amount").asLong()
        );

    }

    private PaymentState parsePaymentState(JsonNode node) {
        try {
            return PaymentState.valueOf(node.get("state").asText());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid state value: " + node.get("state").asText(), e);
        }
    }
}
