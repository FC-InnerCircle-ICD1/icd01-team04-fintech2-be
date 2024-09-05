package incerpay.paygate.domain.vo;

public record TransactionIdentification(
        String transactionId,
        String publicKey
) {
}
