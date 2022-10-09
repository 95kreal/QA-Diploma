package ru.netology.page;

import ru.netology.data.DataHelper;

import java.time.Duration;

import com.codeborne.selenide.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.*;

public class CreditCardPage {
    private SelenideElement form = $("form fieldset");
    private SelenideElement numberField = findFieldByText("Номер карты");
    private SelenideElement monthField = findFieldByText("Месяц");
    private SelenideElement yearField = findFieldByText("Год");
    private SelenideElement ownerField = findFieldByText("Владелец");
    private SelenideElement cvcField = findFieldByText("CVC/CVV");

    private SelenideElement cardNumField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement cardMonthField = $("[placeholder='08']");
    private SelenideElement cardYearField = $("[placeholder='22']");
    private SelenideElement cardOwnerField = $$(".input__inner").find(Condition.text("Владелец")).$(".input__control");
    private SelenideElement cardCvcField = $("[placeholder='999']");
    private static SelenideElement continueButton = $$(".button__content").find(Condition.text("Продолжить"));

    private SelenideElement notificationSuccess = $(".notification_status_ok");
    private SelenideElement notificationError = $(".notification_status_error");

    private SelenideElement invalidFormat = $$(".input__sub").find(Condition.text("Неверный формат"));
    private SelenideElement invalidCardExpirationDate = $$(".input__sub").find(Condition.text("Неверно указан срок действия карты"));
    private SelenideElement cardExpired = $$(".input__sub").find(Condition.text("Истёк срок действия карты"));
    private SelenideElement emptyField = $$(".input__sub").find(Condition.text("Поле обязательно для заполнения"));

    public void checkAllFieldsRequired() {
        $$(".input__sub").shouldHave(CollectionCondition.size(5)).shouldHave(CollectionCondition.texts("Поле обязательно для заполнения"));
    } // "Поле обязательно для заполнения" под всеми полями

    public void allCardInformation(DataHelper.CardInfo cardInfo) {
        cardNumField.setValue(cardInfo.getNumber());
        cardMonthField.setValue(cardInfo.getMonth());
        cardYearField.setValue(cardInfo.getYear());
        cardOwnerField.setValue(cardInfo.getOwner());
        cardCvcField.setValue(cardInfo.getCvc());
        continueButton.click();
    }

    // СООБЩЕНИЯ ПОСЛЕ ОТПРАВКИ ФОРМЫ

    public static class Notifications {
        public static String[] paymentSuccess = {"Успешно", "Операция одобрена Банком."};
        public static final String[] paymentNoSuccess = {"Ошибка", "Ошибка! Банк отказал в проведении операции."};
    }

    public void checkNotification(SelenideElement notification, int waitingTimeInSeconds, String title, String content) {
        notification.shouldBe(visible, Duration.ofSeconds(waitingTimeInSeconds));
        notification.$(".notification__title").shouldHave(text(title));
        notification.$(".notification__content").shouldHave(text(content));
    } // Проверяем сообщение о результате

    public void checkSuccessNotification(int waitingTimeInSeconds) {
        checkNotification(notificationSuccess, waitingTimeInSeconds, DebitCardPage.Notifications.paymentSuccess[0], DebitCardPage.Notifications.paymentSuccess[1]);
    }

    public void checkErrorNotification(int waitingTimeInSeconds) {
        checkNotification(notificationError, waitingTimeInSeconds, DebitCardPage.Notifications.paymentNoSuccess[0], DebitCardPage.Notifications.paymentNoSuccess[1]);
    }

    // КЛИК В ПОЛЕ ВВОДА

    private SelenideElement findFieldByText(String fieldName) {
        return form.$x(".//span[@class='input__top'][contains(text(),'" + fieldName + "')]//..");
    }

    public void inputNumber(String number) {
        cardNumField.click();
    } // Клик в поле "Номер карты"

    public void inputMonth(String month) {
        cardMonthField.click();
    } // Клик в поле "Месяц"

    public void inputYear(String year) {
        cardYearField.click();
    } // Клик в поле "Год"

    public void inputOwner(String owner) {
        cardOwnerField.click();
    } // Клик в поле "Владелец"

    public void inputCvcCvvCode(String code) {
        cardCvcField.click();
    } // Клик в поле "CVC/CVV"

    // ОТОБРАЖЕНИЯ ПЛЕЙСХОЛДЕРОВ В ПОЛЯХ ФОРМЫ

    public String getFieldPlaceholder(SelenideElement field) {
        field.parent().shouldHave(cssClass("input_focused"), Duration.ofMillis(20));
        return field.$("input.input__control")
                .shouldBe(visible, Duration.ofMillis(20))
                .getAttribute("placeholder");
    } // Проверяем, появляется ли плейсхолдер в пустом поле и получаем его значение

    public String getNumberFieldPlaceholder() {
        return getFieldPlaceholder(numberField);
    }

    public String getMonthFieldPlaceholder() {
        return getFieldPlaceholder(monthField);
    }

    public String getYearFieldPlaceholder() {
        return getFieldPlaceholder(yearField);
    }

    public String getOwnerFieldPlaceholder() {
        return getFieldPlaceholder(ownerField);
    }

    public String getCodeFieldPlaceholder() {
        return getFieldPlaceholder(cvcField);
    }

    public void invalidFormat() {
        invalidFormat.shouldBe(visible);
    } // Неверный формат

    public void invalidCardExpirationDate() {
        invalidCardExpirationDate.shouldBe(visible);
    } // Неверно указан срок действия карты

    public void cardExpired() {
        cardExpired.shouldBe(visible);
    } // Истёк срок действия карты

    public void emptyField() {
        emptyField.shouldBe(visible);
    } // Пустое поле

    public static class RegexCredit {
        public static final String numberPlaceholder = "^(\\d{4}\\s){3}\\d{4}$";
        public static final String monthPlaceholder = "^0[1-9]|1[0-3]$";
        public static final String yearPlaceholder = "^\\d{2}$";
        public static final String ownerPlaceholder = "^(?=.{3,27}$)[A-Z]+\\s[A-Z]+$";
        public static final String codePlaceholder = "^\\d{3}$";
    } // Допустимый формат отображения плейсхолдеров
}