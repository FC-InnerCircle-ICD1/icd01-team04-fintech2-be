package incerpay.paygate.presentation.dto.out;

public record CardDataView(
    int sort,
    String cardCompanyCode,
    String cardCompany,
    int installmentPeriod,
    int interestFreePeriod
) {
}
