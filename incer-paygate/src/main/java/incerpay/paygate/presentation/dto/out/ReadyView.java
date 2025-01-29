package incerpay.paygate.presentation.dto.out;

import incerpay.paygate.domain.enumeration.SellerPaymentMethod;
import incerpay.paygate.infrastructure.internal.dto.SellerApiView;
import incerpay.paygate.infrastructure.internal.dto.TermsApiView;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record ReadyView(
        List<String> paymentMethods,
        List<CardDataView> cardlist,
        List<String> termsOfService
) {

    public static ReadyView from(SellerApiView sellerApiView, TermsApiView termView) {
        return new ReadyView(
                extractPaymentMethods(sellerApiView),
                extractCardList(sellerApiView),
                extractTerms(termView)
        );
    }

    private static List<String> extractPaymentMethods(SellerApiView sellerApiView) {
        return Optional.ofNullable(sellerApiView.getPaymentMethods())
                .orElse(Collections.emptyList())
                .stream()
                .map(SellerPaymentMethod::name)
                .collect(Collectors.toList());
    }

    private static List<CardDataView> extractCardList(SellerApiView sellerApiView) {
        return Optional.ofNullable(sellerApiView.getCardCompanies())
                .orElse(Collections.emptyList())
                .stream()
                .map(cardCompany -> new CardDataView(
                        sellerApiView.getCardCompanies().indexOf(cardCompany) + 1,
                        cardCompany.name(),
                        cardCompany.getCardName(),
                        12,  // 임의 삽입: API로 개선되어야 함
                        3    // 임의 삽입: API로 개선되어야 함
                ))
                .collect(Collectors.toList());
    }

    private static List<String> extractTerms(TermsApiView termView) {
        return Optional.ofNullable(termView.getTerms())
                .orElse(Collections.emptyList());
    }

}
