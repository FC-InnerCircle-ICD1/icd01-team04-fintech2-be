package incerpay.payment;

import org.springframework.boot.SpringApplication;

public class TestIncerPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.from(IncerPaymentApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
