package incerpay.paygate.infrastructure.internal;

import org.springframework.stereotype.Component;

@Component
public class StoreServiceCaller {

    public boolean verifyPublicKey(String apiKey){
        return true;
    }

    public boolean verifySecretKey(String apiKey){
        return true;
    }

}
