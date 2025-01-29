package incerpay.paygate.domain.status;

import incerpay.paygate.presentation.dto.in.IncerPaymentApiApproveCommand;
import incerpay.paygate.presentation.dto.in.PaymentApproveCommand;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Getter
@Document(collection = "paymentRealtimeState")
public class PaymentRealtimeState {

    @Id
    private String transactionId;
    private String paymentId;
    private int saveRetryCount;
    private boolean isPaid;
    private boolean isSaved;
    private PaymentApproveCommand paymentApproveCommand;

    public PaymentRealtimeState(PaymentApproveCommand paymentApproveCommand) {
        this.paymentId = paymentApproveCommand.paymentId().toString();
        this.transactionId = paymentApproveCommand.transactionId().toString();
        this.saveRetryCount = 0;
        this.isPaid = false;
        this.isSaved = false;
        this.paymentApproveCommand = paymentApproveCommand;
    }

    public void addRetryCount() {
        if(saveRetryCount >= 3){
            throw new RuntimeException("재시도 가능 횟수를 초과했습니다.");
        }
        saveRetryCount++;
    }

    public void addBatchRetryCount() {
        if(saveRetryCount >= 5){
            throw new RuntimeException("재시도 가능 횟수를 초과했습니다.");
        }
        saveRetryCount++;
    }

    public void makePaid() {
        if (this.isPaid) {
            throw new IllegalStateException("이미 결제된 상태입니다.");
        }
        this.isPaid = true;
    }

    public void makeSaved() {
        if (this.isSaved) {
            throw new IllegalStateException("이미 저장된 상태입니다.");
        }
        this.isSaved = true;
    }

    public IncerPaymentApiApproveCommand getPaymentApproveCommand() {
        return new IncerPaymentApiApproveCommand(UUID.fromString(this.paymentId));
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

