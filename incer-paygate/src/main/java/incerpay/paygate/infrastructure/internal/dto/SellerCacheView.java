package incerpay.paygate.infrastructure.internal.dto;

import incerpay.paygate.domain.enumeration.SellerCardCompany;
import incerpay.paygate.domain.enumeration.SellerPaymentMethod;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellerCacheView {
    private Long sellerId;
    private String sellerName;
    private List<ApiKeyInfo> apiKeyInfos;
    private List<SellerPaymentMethod> paymentMethods;
    private List<SellerCardCompany> cardCompanies;

    public static SellerCacheView from(SellerApiView sellerApiView) {
        return new SellerCacheView(
                sellerApiView.getSellerId(),
                sellerApiView.getSellerName(),
                sellerApiView.getApiKeyInfos(),
                sellerApiView.getPaymentMethods(),
                sellerApiView.getCardCompanies()
        );
    }

    public SellerApiView toSellerApiView() {
        return SellerApiView.create(
                sellerId,
                sellerName,
                apiKeyInfos,
                paymentMethods,
                cardCompanies
        );
    }
}