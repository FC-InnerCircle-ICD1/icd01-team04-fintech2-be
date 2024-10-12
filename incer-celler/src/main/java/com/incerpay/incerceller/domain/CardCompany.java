package com.incerpay.incerceller.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CardCompany {
    NH("농협카드"),
    SH("신한카드"),
    KB("국민카드"),
    LOTTE("롯데카드"),
    HD("현대카드");

    CardCompany(String CardName) {
    }

}
