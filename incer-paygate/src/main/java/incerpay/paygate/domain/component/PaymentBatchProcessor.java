package incerpay.paygate.domain.component;

import incerpay.paygate.common.lib.notification.DiscordWebhookService;
import incerpay.paygate.domain.status.PaymentRealtimeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class PaymentBatchProcessor {

    private final MongoTemplate mongoTemplate;
    private final DiscordWebhookService discordWebhookService;

    public PaymentBatchProcessor(MongoTemplate mongoTemplate,
                                 DiscordWebhookService discordWebhookService) {
        this.mongoTemplate = mongoTemplate;
        this.discordWebhookService = discordWebhookService;
    }


    @Scheduled(cron = "0 0 4 * * *")
    public void processBatch() {
        List<PaymentRealtimeState> payments = findAllRealtimeState();
        payments.forEach(payment -> {
            PaymentStatus status = determinePaymentStatus(payment);
            processPaymentByStatus(payment, status);
        });
    }

    private List<PaymentRealtimeState> findAllRealtimeState() {
        return mongoTemplate.findAll(PaymentRealtimeState.class);
    }

    private PaymentStatus determinePaymentStatus(PaymentRealtimeState payment) {
        if (payment.isPaid()) {
            return payment.isSaved() ? PaymentStatus.COMPLETED : PaymentStatus.RETRY;
        } else {
            return PaymentStatus.UNPAID;
        }
    }

    private void processPaymentByStatus(PaymentRealtimeState payment, PaymentStatus status) {
        switch (status) {
            case COMPLETED:
                processCompletedPayment(payment);
                break;
            case RETRY:
                processRetryPayment(payment);
                break;
            case UNPAID:
                processUnpaidPayment(payment);
                break;
        }
    }

    private void processCompletedPayment(PaymentRealtimeState payment) {
        mongoTemplate.save(payment, "completedPayments");
        mongoTemplate.remove(payment);
    }

    private void processRetryPayment(PaymentRealtimeState payment) {
        if (payment.getSaveRetryCount() >= 5) {
            mongoTemplate.save(payment, "emergencyPayments");
            mongoTemplate.remove(payment);
            discordWebhookService.sendMessage("저장 5회 실패!! 응급!!" + payment.getTransactionId());
        } else {
            payment.addBatchRetryCount();
            mongoTemplate.save(payment);
        }
    }

    private void processUnpaidPayment(PaymentRealtimeState payment) {
        boolean isPaid = checkCardApiResponse(payment.getTransactionId());
        if (isPaid) {
            payment.makePaid();
            mongoTemplate.save(payment);
            discordWebhookService.sendMessage("결제 확인 필요!! " + payment.getTransactionId());
        } else {
            mongoTemplate.save(payment, "unpaidPayments");
            mongoTemplate.remove(payment);
        }
    }

    private boolean checkCardApiResponse(String transactionId) {
        return false;
    }

    public enum PaymentStatus {
        COMPLETED, RETRY, UNPAID
    }
}
