package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;

import static ru.netology.data.DataHelper.*;
import static ru.netology.data.RestApiHelper.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAPI {

    @Test
    void shouldGetStatusValidApprovedCardPayment() {
        val validApprovedCard = getApprovedCard();
        val status = PaymentPageForm(validApprovedCard);
        assertTrue(status.contains("APPROVED"));
    }

    @Test
    void shouldGetStatusValidDeclinedCardPayment() {
        val validApprovedCard = getApprovedCard();
        val status = CreditRequestPageForm(validApprovedCard);
        assertTrue(status.contains("APPROVED"));
    }

    @Test
    void shouldGetStatusValidApprovedCardCreditRequest() {
        val validDeclinedCard = getDeclinedCard();
        val status = PaymentPageForm(validDeclinedCard);
        assertTrue(status.contains("DECLINED"));
    }

    @Test
    void shouldGetStatusValidDeclinedCardCreditRequest() {
        val validDeclinedCard = getDeclinedCard();
        val status = CreditRequestPageForm(validDeclinedCard);
        assertTrue(status.contains("DECLINED"));
    }
}