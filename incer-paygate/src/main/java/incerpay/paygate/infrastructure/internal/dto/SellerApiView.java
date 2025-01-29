package incerpay.paygate.infrastructure.internal.dto;

import incerpay.paygate.domain.enumeration.SellerCardCompany;
import incerpay.paygate.domain.enumeration.SellerPaymentMethod;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class SellerApiView {
    private Long sellerId;
    private String sellerName;
    private List<ApiKeyInfo> apiKeyInfos;
    private List<SellerPaymentMethod> paymentMethods;
    private List<SellerCardCompany> cardCompanies;

    @Builder
    public static SellerApiView create(Long sellerId, String sellerName, List<ApiKeyInfo> apiKeyInfos,
                                       List<SellerPaymentMethod> paymentMethods, List<SellerCardCompany> cardCompanies) {
        return new SellerApiView(sellerId, sellerName, apiKeyInfos, paymentMethods, cardCompanies);
    }


}
