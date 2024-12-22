package incerpay.paygate.infrastructure.internal.dto;

import incerpay.paygate.domain.enumeration.SellerCardCompany;
import incerpay.paygate.domain.enumeration.SellerPaymentMethod;
import incerpay.paygate.presentation.dto.out.CardDataView;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<CardDataView> getCardDataViewList() {
        return Optional.ofNullable(cardCompanies)
                .orElse(Collections.emptyList())
                .stream()
                .map(cardCompany -> new CardDataView(
                        cardCompanies.indexOf(cardCompany) + 1,
                        cardCompany.name(),
                        cardCompany.getCardName(),
                        12,  // 임의 삽입: API로 개선되어야 함
                        3    // 임의 삽입: API로 개선되어야 함
                ))
                .collect(Collectors.toList());
    }


}
