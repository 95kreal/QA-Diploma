package ru.netology.test;

import ru.netology.page.MainPage;

import static ru.netology.page.DebitCardPage.RegexDebit;
import static ru.netology.page.CreditCardPage.RegexCredit;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestUI {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    // ПРОВЕРКА ПЕРЕХОДОВ МЕЖДУ СТРАНИЦАМИ

    @Test
    @DisplayName("Переход с главной страницы сервиса покупки тура к странице покупки с помощью обычной оплаты")
    public void shouldGoToTheRegularPurchasePage() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val paymentCardPage = mainPage.selectDebitCard();
    } // ОР: Осуществляется переход на страницу обычной покупки.
    // ОК

    @Test
    @DisplayName("Переход с главной страницы сервиса покупки тура к странице покупки в кредит")
    public void shouldGoToThePurchaseOnCreditPage() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val creditCardPage = mainPage.selectCreditCard();
    } // ОР: Осуществляется переход на страницу покупки в кредит.
    // ОК

    @Test
    @DisplayName("Переход со страницы покупки тура с помощью обычной оплаты к странице покупки в кредит")
    public void shouldFromARegularPurchaseToAPurchaseOnCredit() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val paymentCardPage = mainPage.selectDebitCard();
        val creditCardPage = mainPage.selectCreditCard();
    } // ОР: Осуществляется переход на страницу покупки в кредит.
    // ОК

    @Test
    @DisplayName("Переход со страницы покупки тура в кредит к странице покупки с помощью обычной оплаты")
    public void shouldFromPurchaseOnCreditToARegularPurchase() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val creditCardPage = mainPage.selectCreditCard();
        val paymentCardPage = mainPage.selectDebitCard();
    } // ОР: Осуществляется переход на страницу обычной покупки.
    // ОК

    // ПРОВЕРКА ОТОБРАЖЕНИЯ ПЛЕЙСХОЛДЕРОВ В ПОЛЯХ ФОРМЫ

    @DisplayName("Проверка отображения и значения плейсхолдера поля «Номер карты» на вкладке «Купить»")
    @Test
    public void shouldShowValidPlaceholderInDebitCardNumberField() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val debitCardPage = mainPage.selectDebitCard();
        debitCardPage.inputNumber(null);
        var actualPlaceholder = debitCardPage.getNumberFieldPlaceholder();

        assertTrue(actualPlaceholder.matches(RegexDebit.numberPlaceholder));
    }// ОР: В Поле "Номер карты" отображается валидный плейсхолдер
    // ОК

    @DisplayName("Проверка отображения и значения плейсхолдера поля «Месяц» на вкладке «Купить»")
    @Test
    public void shouldShowValidPlaceholderInDebitCardMonthField() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val debitCardPage = mainPage.selectDebitCard();
        debitCardPage.inputMonth(null);
        var actualPlaceholder = debitCardPage.getMonthFieldPlaceholder();

        assertTrue(actualPlaceholder.matches(RegexDebit.monthPlaceholder));
    }// ОР: В Поле "Месяц" отображается валидный плейсхолдер
    // ОК

    @DisplayName("Проверка отображения и значения плейсхолдера поля «Год» на вкладке «Купить»")
    @Test
    public void shouldShowValidPlaceholderInDebitCardYearField() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val debitCardPage = mainPage.selectDebitCard();
        debitCardPage.inputYear(null);
        var actualPlaceholder = debitCardPage.getYearFieldPlaceholder();

        assertTrue(actualPlaceholder.matches(RegexDebit.yearPlaceholder));
    }// ОР: В Поле "Год" отображается валидный плейсхолдер
    // ОК

    @DisplayName("Проверка отображения и значения плейсхолдера поля «Владелец» на вкладке «Купить»")
    @Test
    public void shouldShowValidPlaceholderInDebitCardOwnerField() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val debitCardPage = mainPage.selectDebitCard();
        debitCardPage.inputOwner(null);
        var actualPlaceholder = debitCardPage.getOwnerFieldPlaceholder();

        assertTrue(actualPlaceholder.matches(RegexDebit.ownerPlaceholder));
    } // ОР: В Поле "Владелец" отображается валидный плейсхолдер
    // ФР: Плейсхолдер не отображается

    @DisplayName("Проверка отображения и значения плейсхолдера поля «CVC/CVV» на вкладке «Купить»")
    @Test
    public void shouldShowValidPlaceholderInDebitCardCvcCvvField() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val debitCardPage = mainPage.selectDebitCard();
        debitCardPage.inputCvcCvvCode(null);
        var actualPlaceholder = debitCardPage.getCodeFieldPlaceholder();

        assertTrue(actualPlaceholder.matches(RegexDebit.codePlaceholder));
    } // ОР: В Поле "CVC/CVV" отображается валидный плейсхолдер
    // ОК

    @DisplayName("Проверка отображения и значения плейсхолдера поля «Номер карты» на вкладке «Купить в кредит»")
    @Test
    public void shouldShowValidPlaceholderInCreditCardNumberField() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val creditCardPage = mainPage.selectCreditCard();
        creditCardPage.inputNumber(null);
        var actualPlaceholder = creditCardPage.getNumberFieldPlaceholder();

        assertTrue(actualPlaceholder.matches(RegexCredit.numberPlaceholder));
    }// ОР: В Поле "Номер карты" отображается валидный плейсхолдер
    // ОК

    @DisplayName("Проверка отображения и значения плейсхолдера поля «Месяц» на вкладке «Купить в кредит»")
    @Test
    public void shouldShowValidPlaceholderInCreditCardMonthField() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val creditCardPage = mainPage.selectCreditCard();
        creditCardPage.inputMonth(null);
        var actualPlaceholder = creditCardPage.getMonthFieldPlaceholder();

        assertTrue(actualPlaceholder.matches(RegexCredit.monthPlaceholder));
    }// ОР: В Поле "Месяц" отображается валидный плейсхолдер
    // ОК

    @DisplayName("Проверка отображения и значения плейсхолдера поля «Год» на вкладке «Купить в кредит»")
    @Test
    public void shouldShowValidPlaceholderInCreditCardYearField() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val creditCardPage = mainPage.selectCreditCard();
        creditCardPage.inputYear(null);
        var actualPlaceholder = creditCardPage.getYearFieldPlaceholder();

        assertTrue(actualPlaceholder.matches(RegexCredit.yearPlaceholder));
    }// ОР: В Поле "Год" отображается валидный плейсхолдер
    // ОК

    @DisplayName("Проверка отображения и значения плейсхолдера поля «Владелец» на вкладке «Купить в кредит»")
    @Test
    public void shouldShowValidPlaceholderInCreditCardOwnerField() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val creditCardPage = mainPage.selectCreditCard();
        creditCardPage.inputOwner(null);
        var actualPlaceholder = creditCardPage.getOwnerFieldPlaceholder();

        assertTrue(actualPlaceholder.matches(RegexCredit.ownerPlaceholder));
    } // ОР: В Поле "Владелец" отображается валидный плейсхолдер
    // ФР: Плейсхолдер не отображается

    @DisplayName("Проверка отображения и значения плейсхолдера поля «CVC/CVV» на вкладке «Купить в кредит»")
    @Test
    public void shouldShowValidPlaceholderInCreditCardCvcCvvField() {
        open("http://localhost:8080/");
        val mainPage = new MainPage();
        val creditCardPage = mainPage.selectCreditCard();
        creditCardPage.inputCvcCvvCode(null);
        var actualPlaceholder = creditCardPage.getCodeFieldPlaceholder();

        assertTrue(actualPlaceholder.matches(RegexCredit.codePlaceholder));
    } // ОР: В Поле "CVC/CVV" отображается валидный плейсхолдер
    // ОК
}


























