package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.BdHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.page.CreditCardPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.qameta.allure.selenide.AllureSelenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCreditCardPage {
    MainPage mainPage = new MainPage();
    CreditCardPage paymentCardPage = new CreditCardPage();

    @BeforeEach
    public void startBrowser() {
        open("http://localhost:8080/");
        paymentCardPage = mainPage.selectCreditCard();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

//  ВСЕ ПОЛЯ ВАЛИДНЫ

    @Test
    @DisplayName("Успешная покупка с помощью карты, на которой достаточно ДС на счёте")
    public void shouldApprovedCard() {
        val validCardInfo = DataHelper.getApprovedCard();
        paymentCardPage.allCardInformation(validCardInfo);
        paymentCardPage.checkSuccessNotification(15);

        assertEquals("APPROVED", BdHelper.getPaymentStatus());
    } // ОР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."
    // ОК

    @Test
    @DisplayName("Отказ в покупке при оплате с карты, на которой недостаточно ДС")
    public void shouldDeclinedCard() {
        val validCardInfo = DataHelper.getDeclinedCard();
        paymentCardPage.allCardInformation(validCardInfo);
        paymentCardPage.checkErrorNotification(15);

        assertEquals("DECLINED", BdHelper.getPaymentStatus());
    } // ОР: В правом верхнем углу появляется поп-ап с текстом "Ошибка! Банк отказал в проведении операции."
    // ФР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."

//  ПОЛЕ "НОМЕР КАРТЫ"

    @Test
    @DisplayName("Пустое поле")
    public void shouldEmptyCardNumberField() {
        val invalidCardInfo = DataHelper.getEmptyCardNumberField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.emptyField();
    } // ОР: Форма не отправляется. Под полем ошибка "Поле обязательно для заполнения"
    // ФР: Форма не отправляется. Под полем ошибка "Неверный формат"

    @Test
    @DisplayName("1 цифра в поле")
    public void should1DigitInTheCardNumberField() {
        val invalidCardInfo = DataHelper.get1DigitInTheCardNumberField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ОК

    @Test
    @DisplayName("15 цифр в поле")
    public void should15DigitsInTheCardNumberField() {
        val invalidCardInfo = DataHelper.get15DigitsInTheCardNumberField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ОК

    @Test
    @DisplayName("Номер карты, которой нет в БД")
    public void shouldCardThatIsNotInTheDatabase() {
        val invalidCardInfo = DataHelper.getCardThatIsNotInTheDatabase();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.checkErrorNotification(15);
    } // ОР: В правом верхнем углу появляется поп-ап с текстом "Ошибка! Банк отказал в проведении операции."
    // ОК

//  ПОЛЕ "МЕСЯЦ"

    @Test
    @DisplayName("Пустое поле")
    public void shouldEmptyMonthField() {
        val invalidCardInfo = DataHelper.getEmptyMonthField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.emptyField();
    } // ОР: Форма не отправляется. Под полем ошибка "Поле обязательно для заполнения"
    // ФР: Форма не отправляется. Под полем ошибка "Неверный формат"

    @Test
    @DisplayName("Нулевое значение в поле")
    public void shouldZeroValueInTheMonthField() {
        val invalidCardInfo = DataHelper.getZeroValueInTheMonthField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ФР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."

    @Test
    @DisplayName("1 цифра в поле")
    public void should1DigitInTheMonthField() {
        val invalidCardInfo = DataHelper.get1DigitInTheMonthField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ОК

    @Test
    @DisplayName("Несуществующий месяц")
    public void shouldNonExistentMonth() {
        val invalidCardInfo = DataHelper.getNonExistentMonth();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidCardExpirationDate();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверно указан срок действия карты"
    // ОК

//  ПОЛЕ "ГОД"

    @Test
    @DisplayName("Пустое поле")
    public void shouldEmptyYearField() {
        val invalidCardInfo = DataHelper.getEmptyYearField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.emptyField();
    } // ОР: Форма не отправляется. Под полем ошибка "Поле обязательно для заполнения"
    // ФР: Форма не отправляется. Под полем ошибка "Неверный формат"

    @Test
    @DisplayName("Нулевое значение в поле")
    public void shouldZeroValueInTheYearField() {
        val invalidCardInfo = DataHelper.getZeroValueInTheYearField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.cardExpired();
    } // ОР: Форма не отправляется. Под полем ошибка "Истёк срок действия карты"
    // ОК

    @Test
    @DisplayName("1 цифра в поле")
    public void should1DigitInTheYearField() {
        val invalidCardInfo = DataHelper.get1DigitInTheYearField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ОК

    @Test
    @DisplayName("Год, предшествующий текущему")
    public void shouldYearPrecedingTheCurrentOne() {
        val invalidCardInfo = DataHelper.getYearPrecedingTheCurrentOne();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.cardExpired();
    } // ОР: Форма не отправляется. Под полем ошибка "Истёк срок действия карты"
    // ОК

    @Test
    @DisplayName("Год, превышающий 5 лет от текущего")
    public void shouldYearExceeding5YearsFromTheCurrentOne() {
        val invalidCardInfo = DataHelper.getYearExceeding5YearsFromTheCurrentOne();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidCardExpirationDate();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверно указан срок действия карты"
    // ОК

//  ПОЛЕ "ВЛАДЕЛЕЦ"

    @Test
    @DisplayName("Пустое поле")
    public void shouldEmptyOwnerField() {
        val invalidCardInfo = DataHelper.getEmptyOwnerField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.emptyField();
    } // ОР: Форма не отправляется. Под полем ошибка "Поле обязательно для заполнения"
    // ОК

    @Test
    @DisplayName("Одна буква на латинице")
    public void shouldOneLetterInLatin() {
        val invalidCardInfo = DataHelper.getOneLetterInLatin();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ФР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."

    @Test
    @DisplayName("Только имя на латинице")
    public void shouldOnlyTheNameInLatin() {
        val invalidCardInfo = DataHelper.getOnlyTheNameInLatin();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ФР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."

    @Test
    @DisplayName("3 слова на латинице")
    public void should3WordsInLatin() {
        val invalidCardInfo = DataHelper.get3WordsInLatin();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ФР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."

    @Test
    @DisplayName("Имя и фамилия на кириллице")
    public void shouldFirstAndLastNameInCyrillic() {
        val invalidCardInfo = DataHelper.getFirstAndLastNameInCyrillic();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ФР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."

    @Test
    @DisplayName("Цифры")
    public void shouldNumbersInTheField() {
        val invalidCardInfo = DataHelper.getNumbersInTheField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ФР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."

    @Test
    @DisplayName("Пробел")
    public void shouldSpaceInTheOwnerField() {
        val invalidCardInfo = DataHelper.getSpaceInTheOwnerField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.emptyField();
    } // ОР: Форма не отправляется. Под полем ошибка "Поле обязательно для заполнения"
    // ОК

    @Test
    @DisplayName("Спецсимволы")
    public void shouldSpecialSymbolsInTheField() {
        val invalidCardInfo = DataHelper.getSpecialSymbolsInTheField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ФР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."

    @Test
    @DisplayName("Разный регистр на латинице")
    public void shouldSpecialSymbolsInTheOwnerField() {
        val invalidCardInfo = DataHelper.getSpecialSymbolsInTheOwnerField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ФР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."

//  ПОЛЕ "CVC/CVV"

    @Test
    @DisplayName("Пустое поле")
    public void shouldEmptyCvcCvvField() {
        val invalidCardInfo = DataHelper.getEmptyCvcCvvField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.emptyField();
    } // ОР: Форма не отправляется. Под полем ошибка "Поле обязательно для заполнения"
    // ФР: Форма не отправляется. Под полем CVC/CVV ошибка "Неверный формат"
    // Под полем Владелец ошибка "Поле обязательно для заполнения"

    @Test
    @DisplayName("1 цифра в поле")
    public void should1DigitInTheCvcCvvField() {
        val invalidCardInfo = DataHelper.get1DigitInTheCvcCvvField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ОК

    @Test
    @DisplayName("2 цифры в поле")
    public void should2DigitsInTheCvcCvvField() {
        val invalidCardInfo = DataHelper.get2DigitsInTheCvcCvvField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.invalidFormat();
    } // ОР: Форма не отправляется. Под полем ошибка "Неверный формат"
    // ОК

    @Test
    @DisplayName("Нулевое значение в поле")
    public void shouldZeroValueInTheCvcCvvField() {
        val invalidCardInfo = DataHelper.getZeroValueInTheCvcCvvField();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.checkSuccessNotification(15);
    } // ОР: В правом верхнем углу появляется поп-ап с текстом "Успешно Операция одобрена банком."
    // ОК

//  ВСЕ ПОЛЯ ПУСТЫЕ

    @Test
    @DisplayName("Все поля пустые")
    public void shouldAllFieldsAreEmpty() {
        val invalidCardInfo = DataHelper.getAllFieldsAreEmpty();
        paymentCardPage.allCardInformation(invalidCardInfo);
        paymentCardPage.checkAllFieldsRequired();
    } // ОР: Форма не отправляется. Под полями ошибка "Поле обязательно для заполнения"
    // ФР: Под полем Владелец ошибка "Поле обязательно для заполнения"
    // Под остальными полями ошибка "Неверный формат"
}

