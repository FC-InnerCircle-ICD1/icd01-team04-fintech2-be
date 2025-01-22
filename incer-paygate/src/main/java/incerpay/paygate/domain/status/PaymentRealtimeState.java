package incerpay.paygate.domain.status;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Document(collection = "paymentRealtimeState")
public class PaymentRealtimeState {

    @Id
    private String transactionId;
    private String paymentId;
    private int saveRetryCount;
    private boolean isPaid;
    private boolean isSaved;

    public PaymentRealtimeState(String paymentId, String transactionId) {
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
        if (this.isPaid) {
            throw new IllegalStateException("이미 결제된 상태입니다.");
        }
        this.isPaid = true;
    }

    public void save() {
        if (this.isSaved) {
            throw new IllegalStateException("이미 저장된 상태입니다.");
        }
        this.isSaved = true;
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

