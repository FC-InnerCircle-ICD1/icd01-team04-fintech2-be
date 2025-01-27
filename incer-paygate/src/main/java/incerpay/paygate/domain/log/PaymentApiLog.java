package incerpay.paygate.domain.log;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "payment_api_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentApiLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentApiType type;

    @Column(nullable = false)
    private String paymentId;

    @Column(nullable = false)
    private String requestBody;

    @Column
    private String responseBody;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    @Column
    private LocalDateTime respondedAt;

    // TODO BaseTimeEntity 분리?
    // TODO Clock 사용?
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private PaymentApiLog(PaymentApiType type, UUID paymentId, String requestBody) {
        this.type = type;
        this.paymentId = paymentId.toString();
        this.requestBody = requestBody;
        this.requestedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public static PaymentApiLog ofRequest(UUID paymentId, String requestBody) {
        return new PaymentApiLog(PaymentApiType.REQUEST, paymentId, requestBody);
    }

    public static PaymentApiLog ofResponse(UUID paymentId, String responseBody) {
        PaymentApiLog log = new PaymentApiLog(PaymentApiType.RESPONSE, paymentId, "");
        log.updateResponse(responseBody);
        return log;
    }

    public static PaymentApiLog ofError(UUID paymentId, String errorMessage) {
        return new PaymentApiLog(PaymentApiType.ERROR, paymentId, errorMessage);
    }

    private void updateResponse(String responseBody) {
        this.responseBody = responseBody;
        this.respondedAt = LocalDateTime.now();
    }
}
