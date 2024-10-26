package incerpay.paygate.domain.enumeration;

public enum SellerCardCompany {
    NH("농협카드"),
    SH("신한카드"),
    KB("국민카드"),
    LOTTE("롯데카드"),
    HD("현대카드");

    final String cardName;

    SellerCardCompany(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return this.cardName;
    }
}
