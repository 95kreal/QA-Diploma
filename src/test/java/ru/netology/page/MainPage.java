package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private SelenideElement bueCardButton = $(byText("Купить"));
    private SelenideElement bueCreditCardButton = $(byText("Купить в кредит"));
    private SelenideElement fieldPayment = $("#root > div >h3");

    public DebitCardPage selectDebitCard() {
        bueCardButton.click();
        fieldPayment.shouldHave(exactText("Оплата по карте"));
        return new DebitCardPage();
    }

    public CreditCardPage selectCreditCard() {
        bueCreditCardButton.click();
        fieldPayment.shouldHave(exactText("Кредит по данным карты"));
        return new CreditCardPage();
    }
}