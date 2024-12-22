package incerpay.paygate.domain.component;

import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.domain.enumeration.SellerCardCompany;
import incerpay.paygate.infrastructure.internal.IncerPaymentStoreCaller;
import incerpay.paygate.infrastructure.internal.dto.SellerApiView;
import incerpay.paygate.infrastructure.internal.dto.TermsApiView;
import incerpay.paygate.presentation.dto.out.CardDataView;
import incerpay.paygate.presentation.dto.out.ReadyView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CardPaymentMethodAdapter implements PaymentMethodAdapter{

    @Autowired
    @Qualifier("caffeineCacheManager")
    private CacheManager cacheManager;
    private final IncerPaymentStoreCaller caller;

    public CardPaymentMethodAdapter(IncerPaymentStoreCaller caller) {
        this.caller = caller;
    }

    @Override
    public List<CardDataView> getCardsInfo(PaymentType pv, Long sellerKey) {
        SellerApiView sellerView = caller.getSeller(sellerKey).toSellerApiView();
        return getCardDataViewList(sellerView.getCardCompanies());
    }

    public ReadyView getPaymentInfo(Long sellerKey) {

        SellerApiView sellerView = caller.getSeller(sellerKey).toSellerApiView();
        log.info("SellerApiView: " + sellerView.toString());

        TermsApiView termView = caller.getTerms();
        inspectCache();
        log.info("TermsApiView: " + termView.toString());

        ReadyView view = ReadyView.from(sellerView, termView);
        log.info("ReadyView: " + view.toString());

        return view;
    }


    private void inspectCache() {
        CaffeineCache cache = (CaffeineCache) cacheManager.getCache("terms");
        if (cache != null) {
            com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = cache.getNativeCache();
            log.info("Cache stats: {}", nativeCache.stats());
            nativeCache.asMap().forEach((key, value) ->
                    log.info("Cache key: {}, Cache value: {}", key, value)
            );
        } else {
            log.warn("Cache 'terms' not found or not initialized.");
        }
    }


    public List<CardDataView> getCardDataViewList(List<SellerCardCompany> cardCompanies) {
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
