package incerpay.paygate.infrastructure.external.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import incerpay.paygate.infrastructure.external.IncerPaymentApiDataViewDeserializer;

@JsonDeserialize(using = IncerPaymentApiDataViewDeserializer.class)
public interface IncerPaymentApiDataView { }