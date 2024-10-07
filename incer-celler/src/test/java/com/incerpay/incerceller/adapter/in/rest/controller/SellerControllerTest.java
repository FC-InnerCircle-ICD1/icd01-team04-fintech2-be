package com.incerpay.incerceller.adapter.in.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incerpay.incerceller.application.dto.CardRegisterRequest;
import com.incerpay.incerceller.domain.CardCompany;
import com.incerpay.incerceller.domain.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void paymentMethod가_NULL_cardCompany가_NULL_실패() throws Exception {
        CardRegisterRequest request = new CardRegisterRequest(0L,null, null);

        mockMvc.perform(post("/seller/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void paymentMethod가_CREDIT_cardCompany값_존재_성공() throws Exception {
        CardRegisterRequest request = new CardRegisterRequest(0L,Collections.singletonList(PaymentMethod.CREDIT_CARD),
                Collections.singletonList(CardCompany.HD));

        mockMvc.perform(post("/seller/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void paymentMethod가_NULL_cardCompany값_존재_실패() throws Exception {
        CardRegisterRequest request = new CardRegisterRequest(0L,Collections.emptyList(),
                Collections.singletonList(CardCompany.HD));

        mockMvc.perform(post("/seller/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void paymentMethod가_TOSS_cardCompany값_존재_실패() throws Exception {
        CardRegisterRequest request = new CardRegisterRequest(0L,Collections.singletonList(PaymentMethod.TOSS),
                Collections.singletonList(CardCompany.HD));

        mockMvc.perform(post("/seller/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void paymentMethod가_TOSS_cardCompany가_NULL_성공() throws Exception {
        CardRegisterRequest request = new CardRegisterRequest(0L,Collections.singletonList(PaymentMethod.TOSS),
                Collections.emptyList());

        mockMvc.perform(post("/seller/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

}
