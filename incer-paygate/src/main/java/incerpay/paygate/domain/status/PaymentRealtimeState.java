package incerpay.paygate.domain.status;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Document(collection = "paymentRealtimeState")
public class PaymentRealtimeState {

    @Id
    private UUID transactionId;
    private UUID paymentId;
    private int saveRetryCount;
    private boolean isPaid;
    private boolean isSaved;

    public PaymentRealtimeState(UUID paymentId, UUID transactionId) {
        this.paymentId = paymentId;
        this.transactionId = transactionId;
        this.saveRetryCount = 0;
        this.isPaid = false;
        this.isSaved = false;
    }

    public void addRetryCount() {
        if(saveRetryCount >= 3){
            throw new RuntimeException("재시도 가능 횟수를 초과했습니다.");
        }
        saveRetryCount++;
    }

    public void pay() {
        isPaid = true;
    }


    public void save() {
        isSaved = true;
    }

    @Override
    public String toString() {
        return "PaymentRealtimeState{" +
                "transactionId=" + transactionId +
                ", paymentId=" + paymentId +
                ", saveRetryCount=" + saveRetryCount +
                ", isPaid=" + isPaid +
                ", isSaved=" + isSaved +
                '}';
    }

}

