package incerpay.payment.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PaymentWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long balance;

    public void conduct(Long amount) {
        balance += amount;
    }
    public void deduct(Long amount) {
        balance -= amount;
    }

    public boolean isEmpty() {
        return balance == 0L;
    }
}
