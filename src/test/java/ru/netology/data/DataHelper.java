package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Value;
import com.github.javafaker.Faker;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    static String generateDate(int plusMonth, String formatPattern) {
        return LocalDate.now().plusMonths(plusMonth).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    @Value
    @AllArgsConstructor
    public static class CardInfo {
        private String number;
        private String month;
        private String year;
        private String owner;
        private String cvc;
    }

//  ВСЕ ПОЛЯ ВАЛИДНЫ

    public static CardInfo getApprovedCard() {
        return new CardInfo("4444 4444 4444 4441", "09", "25", "Pyotr Tchaikovsky", "159");
    } // Карта со статусом Approved

    public static CardInfo getDeclinedCard() {
        return new CardInfo("4444 4444 4444 4442", "09", "25", "Pyotr Tchaikovsky", "159");
    } // Карта со статусом Declined

    //  ПОЛЕ "НОМЕР КАРТЫ"

    public static CardInfo getEmptyCardNumberField() {
        return new CardInfo("", generateDate(5, "MM"), generateDate(24, "yy"), faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // Пустое поле

    public static CardInfo get1DigitInTheCardNumberField() {
        return new CardInfo("4", generateDate(5, "MM"), generateDate(24, "yy"), faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // 1 цифра в поле

    public static CardInfo get15DigitsInTheCardNumberField() {
        return new CardInfo("4444 4444 4444 444", generateDate(5, "MM"), generateDate(24, "yy"), faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // 15 цифр в поле

    public static CardInfo getCardThatIsNotInTheDatabase() {
        return new CardInfo("4444 4444 4444 4443", generateDate(5, "MM"), generateDate(24, "yy"), faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // Номер карты, которой нет в БД

//  ПОЛЕ "МЕСЯЦ"

    public static CardInfo getEmptyMonthField() {
        return new CardInfo("4444 4444 4444 4441", "", generateDate(24, "yy"), faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // Пустое поле

    public static CardInfo getZeroValueInTheMonthField() {
        return new CardInfo("4444 4444 4444 4441", "00", generateDate(24, "yy"), faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // Нулевое значение в поле

    public static CardInfo get1DigitInTheMonthField() {
        return new CardInfo("4444 4444 4444 4441", "1", generateDate(24, "yy"), faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // 1 цифра в поле

    public static CardInfo getNonExistentMonth() {
        return new CardInfo("4444 4444 4444 4441", "13", generateDate(24, "yy"), faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // Несуществующий месяц

//  ПОЛЕ "ГОД"

    public static CardInfo getEmptyYearField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), "", faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // Пустое поле

    public static CardInfo getZeroValueInTheYearField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), "00", faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // Нулевое значение в поле

    public static CardInfo get1DigitInTheYearField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), "2", faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // 1 цифра в поле

    public static CardInfo getYearPrecedingTheCurrentOne() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), "21", faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // Год, предшествующий текущему

    public static CardInfo getYearExceeding5YearsFromTheCurrentOne() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), "28", faker.name().fullName(), String.valueOf(faker.number().numberBetween(100, 999)));
    } // Год, превышающий 5 лет от текущего

//  ПОЛЕ "ВЛАДЕЛЕЦ"

    public static CardInfo getEmptyOwnerField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), "", String.valueOf(faker.number().numberBetween(100, 999)));
    } // Пустое поле

    public static CardInfo getOneLetterInLatin() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), "P", String.valueOf(faker.number().numberBetween(100, 999)));
    } // Одна буква на латинице

    public static CardInfo getOnlyTheNameInLatin() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), "Pyotr", String.valueOf(faker.number().numberBetween(100, 999)));
    } // Только имя на латинице

    public static CardInfo get3WordsInLatin() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), "Pyotr Ilyich Tchaikovsky", String.valueOf(faker.number().numberBetween(100, 999)));
    } // 3 слова на латинице

    public static CardInfo getFirstAndLastNameInCyrillic() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), "Пётр Чайковский", String.valueOf(faker.number().numberBetween(100, 999)));
    } // Имя и фамилия на кириллице

    public static CardInfo getNumbersInTheField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), "1591703", String.valueOf(faker.number().numberBetween(100, 999)));
    } // Цифры, например 1591703

    public static CardInfo getSpaceInTheOwnerField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), " ", String.valueOf(faker.number().numberBetween(100, 999)));
    } // Пробел

    public static CardInfo getSpecialSymbolsInTheField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), "!@#$%^&*()", String.valueOf(faker.number().numberBetween(100, 999)));
    } // Спецсимволы

    public static CardInfo getSpecialSymbolsInTheOwnerField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), "PyOtR TcHaIkOvSkY", String.valueOf(faker.number().numberBetween(100, 999)));
    } // Разный регистр на латинице

//  ПОЛЕ "CVC/CVV"

    public static CardInfo getEmptyCvcCvvField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), faker.name().fullName(), "");
    } // Пустое поле

    public static CardInfo get1DigitInTheCvcCvvField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), faker.name().fullName(), "1");
    } // 1 цифра в поле

    public static CardInfo get2DigitsInTheCvcCvvField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), faker.name().fullName(), "15");
    } // 2 цифры в поле

    public static CardInfo getZeroValueInTheCvcCvvField() {
        return new CardInfo("4444 4444 4444 4441", generateDate(5, "MM"), generateDate(24, "yy"), faker.name().fullName(), "000");
    } // Нулевое значение в поле

//  ВСЕ ПОЛЯ ПУСТЫЕ

    public static CardInfo getAllFieldsAreEmpty() {
        return new CardInfo("", "", "", "", "");
    }
}
