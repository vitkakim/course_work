package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker();
    private static final Faker fakerRU = new Faker(new Locale("ru"));
    private static final LocalDate localDate = LocalDate.now();

    private DataHelper() {
    }

    @Value
    @Data
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cardCVC;
    }

    //    Заполнение поля Номер карты
    public static String getApprovedCardNumber() {
        return "1111 2222 3333 4444";
    }

    public static String getDeclinedCardNumber() {
        return "5555 6666 7777 8888";
    }

    public static String getRandomCardNumber() {
        return faker.numerify("#### #### #### ####");
    }

    public static String getCardNumberWithFifteenNumber() {
        return faker.numerify("#### #### #### ###");
    }

    public static String getCardNumberWithNineteenNumber() {
        return faker.numerify("#### #### #### #### ###");
    }

    public static String getCardNumberWithTwentyNumber() {
        return faker.numerify("#### #### #### #### ####");
    }

    //    Заполнение поля Месяц
    public static String getValidMonth() {
        return localDate.plusMonths(faker.number().numberBetween(1, 12)).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getCurrentMonth() {
        return localDate.format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getMonthDoubleZero() {
        return "00";
    }

    public static String getMonthThirteen() {
        return "13";
    }

    //    Заполнение поля Год
    public static String getValidYear() {
        return localDate.plusYears(faker.number().numberBetween(1, 4)).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getCurrentYear() {
        return localDate.format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getPastYear() {
        return localDate.minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getFutureYear() {
        return localDate.plusYears(faker.number().numberBetween(10, 20)).format(DateTimeFormatter.ofPattern("yy"));
    }

    //    Заполнение поля Владелец
    public static String getOwnerName() {
        return faker.name().fullName();
    }

    public static String getOwnerTwoName() {
        return faker.name().firstName() + "-" + faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getOwnerTwoLastName() {
        return faker.name().firstName() + " " + faker.name().lastName() + "-" + faker.name().lastName();
    }

    public static String getOwnerNameInRussia() {
        return fakerRU.name().fullName();
    }

    public static String getOwnerNameOnlyWithSigns() {
        return "!@@#$%^";
    }

    public static String getOwnerOneLetterName() {
        return faker.letterify("?");
    }

    //    Заполнение поля CVC
    public static String getCVC() {
        return faker.numerify("###");
    }

    public static String getCVCWithSigns() {
        return "2+3";
    }

    // общее для всех полей
    public static String getOneNumber() {
        return faker.numerify("#");
    }

    public static String getTwoLetter() {
        return faker.letterify("??");
    }

    public static String getTwoSigns() {
        return ("?&");
    }

    public static String getEmptyField() {
        return null;
    }

    public static class Registration {
        private Registration() {
        }

        public static CardInfo getValidUser() {
            return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        }


        public static CardInfo getDeclinedUser() {
            return new CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        }

        public static CardInfo getAllEmptyFields() {
            return new CardInfo(getEmptyField(), getEmptyField(), getEmptyField(), getEmptyField(), getEmptyField());
        }

        public static CardInfo getAnyCardNumberUser(String cardInfo) {
            return new CardInfo(cardInfo, getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        }

        public static CardInfo getAnyMonthCard(String cardInfo) {
            return new CardInfo(getApprovedCardNumber(), cardInfo, getValidYear(), getOwnerName(), getCVC());
        }

        public static CardInfo getAnyYearCard(String cardInfo) {
            return new CardInfo(getApprovedCardNumber(), getValidMonth(), cardInfo, getOwnerName(), getCVC());
        }

        public static CardInfo getCurrentMonthAndYearCard() {
            return new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerName(), getCVC());
        }

        public static CardInfo getAnyOwnerCard(String cardInfo) {
            return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), cardInfo, getCVC());
        }

        public static CardInfo getAnyCVCCard(String cardInfo) {
            return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), cardInfo);
        }


    }
}
