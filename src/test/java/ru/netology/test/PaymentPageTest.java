package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DBUtils;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getEmptyField;

public class PaymentPageTest {
    private MainPage mainPage = new MainPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        DBUtils.cleanTable();
    }

    @Test
    void shouldGetPaymentPage() {
        mainPage.payByCard();
    }

    @Test
    void shouldGetCreditPage() {
        mainPage.payByCredit();
    }

    @Test
    void shouldSuccessMessageSendValidFormUseApprovedCard() {
        var payByCard = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getValidUser();
        payByCard.fillForm(cardInfo);
        payByCard.successfulPayment();
        assertEquals("APPROVED", DBUtils.getPaymentStatus());
    }

    @Test
    void shouldGetErrorMessageSendValidFormUseDeclinedCard() {
        var payByCard = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getDeclinedUser();
        payByCard.fillForm(cardInfo);
        payByCard.declinedPayment();
        assertEquals("DECLINED", DBUtils.getPaymentStatus());
    }

    // pass
    @Test
    void shouldPayByCardSuccessfully() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getValidUser();
        paymentPage.fillForm(cardInfo);
        paymentPage.successfulPayment();
    }


    //   pass, should failed
    // bug
    @Test
    void shouldNotPayWithDeclinedCard() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getDeclinedUser();
        paymentPage.fillForm(cardInfo);
        paymentPage.declinedPayment();
    }

    //    pass
    @Test
    void shouldNotPayWithAllEmptyFields() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAllEmptyFields();
        paymentPage.fillForm(cardInfo);
        paymentPage.notFilledForm();
    }

    //    pass
    @Test
    void shouldNotPayByRandomCard() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyCardNumberUser(DataHelper.getRandomCardNumber());
        paymentPage.fillForm(cardInfo);
        paymentPage.declinedPayment();
    }

    //   pass
    @Test
    void shouldNotPayByOneNumberCard() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyCardNumberUser(DataHelper.getOneNumber());
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    //   failed, should pass
    // bug
    @Test
    void shouldPayBy15NumberCard() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyCardNumberUser(DataHelper.getCardNumberWithFifteenNumber());
        paymentPage.fillForm(cardInfo);
        paymentPage.successfulPayment();
    }

    //   failed, should pass
    // bug
    @Test
    void shouldPayBy19NumberCard() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyCardNumberUser(DataHelper.getCardNumberWithNineteenNumber());
        paymentPage.fillForm(cardInfo);
        paymentPage.successfulPayment();
    }

    //   failed
    @Test
    void shouldNotPayBy20NumberCard() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyCardNumberUser(DataHelper.getCardNumberWithTwentyNumber());
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberShouldHave(cardInfo.getCardNumber());
    }

    // failed
    @Test
    void shouldNotPayByCardWithSigns() {
        var paymentPage = mainPage.payByCard();
        var cardNumber = DataHelper.getCardNumberWithFifteenNumber();
        var digit = DataHelper.getTwoSigns();
        var cardInfo = DataHelper.Registration.getAnyCardNumberUser(cardNumber + digit);
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberShouldHave(cardInfo.getCardNumber());
    }

    // not pass
    @Test
    void shouldNotPayByCardWithLetters() {
        var paymentPage = mainPage.payByCard();
        var cardNumber = DataHelper.getCardNumberWithFifteenNumber();
        var letter = DataHelper.getTwoLetter();
        var cardInfo = DataHelper.Registration.getAnyCardNumberUser(cardNumber + letter);
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberShouldHave(cardInfo.getCardNumber());
    }

    // pass
    @Test
    void shouldNotPayWithoutCard() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyCardNumberUser(getEmptyField());
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    // pass, should failed
    @Test
    void shouldNotPayWithMonthWithNulls() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyMonthCard(DataHelper.getMonthDoubleZero());
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    //    pass
    @Test
    void shouldNotPayWithMonthOver12() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyMonthCard(DataHelper.getMonthThirteen());
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    //   pass
    @Test
    void shouldNotPayWithMonthWithOneDigit() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyMonthCard(DataHelper.getOneNumber());
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    // failed
    @Test
    void shouldNotPayWithMonthMoreThenTwoDigit() {
        var paymentPage = mainPage.payByCard();
        var month = DataHelper.getValidMonth();
        var number = DataHelper.getOneNumber();
        var cardInfo = DataHelper.Registration.getAnyMonthCard(month + number);
        paymentPage.fillForm(cardInfo);
        paymentPage.monthShouldHave(cardInfo.getMonth());
    }

    // failed
    @Test
    void shouldNotPayWithMonthWithLetters() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyMonthCard(DataHelper.getTwoLetter());
        paymentPage.fillForm(cardInfo);
        paymentPage.monthShouldHave(cardInfo.getMonth());
    }

    //  failed
    @Test
    void shouldNotPayWithMonthWithSigns() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyMonthCard(DataHelper.getTwoSigns());
        paymentPage.fillForm(cardInfo);
        paymentPage.monthShouldHave(cardInfo.getMonth());
    }

    //   pass
    @Test
    void shouldNotPayWithoutMonth() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyMonthCard(getEmptyField());
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    // pass
    @Test
    void shouldNotPayWithPastYear() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyYearCard(DataHelper.getPastYear());
        paymentPage.fillForm(cardInfo);
        paymentPage.expiredCardErrorVisible();
    }

    // pass
    @Test
    void shouldNotPayWithFutureYear() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyYearCard(DataHelper.getFutureYear());
        paymentPage.fillForm(cardInfo);
        paymentPage.expiredCardErrorVisible();
    }

    // pass
    @Test
    void shouldNotPayWithYearWithOneDigit() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyYearCard(DataHelper.getOneNumber());
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    //   failed
    @Test
    void shouldNotPayWithYearMoreThenTwoDigit() {
        var paymentPage = mainPage.payByCard();
        var year = DataHelper.getValidYear();
        var number = DataHelper.getOneNumber();
        var cardInfo = DataHelper.Registration.getAnyYearCard(year + number);
        paymentPage.fillForm(cardInfo);
        paymentPage.yearShouldHave(cardInfo.getYear());
    }

    // failed
    @Test
    void shouldNotPayWithYearWithLetters() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyYearCard(DataHelper.getTwoLetter());
        paymentPage.fillForm(cardInfo);
        paymentPage.yearShouldHave(cardInfo.getYear());
    }

    // failed
    @Test
    void shouldNotPayWithYearWithSigns() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyYearCard(DataHelper.getTwoSigns());
        paymentPage.fillForm(cardInfo);
        paymentPage.yearShouldHave(cardInfo.getYear());
    }

    // pass
    @Test
    void shouldNotPayWithoutYear() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyYearCard(getEmptyField());
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // pass
    @Test
    void shouldPayWithCurrentMonthAndYear() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getCurrentMonthAndYearCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.successfulPayment();
    }

    // failed, should pass
    @Test
    void shouldNotPayWithNameInRussian() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyOwnerCard(DataHelper.getOwnerNameInRussia());
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // failed, should pass
    @Test
    void shouldNotPayWithOneLetter() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyOwnerCard(DataHelper.getOwnerOneLetterName());
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // pass
    @Test
    void shouldPayWithDoubleName() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyOwnerCard(DataHelper.getOwnerTwoName());
        paymentPage.fillForm(cardInfo);
        paymentPage.successfulPayment();
    }

    // pass
    @Test
    void shouldPayWithDoubleLastName() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyOwnerCard(DataHelper.getOwnerTwoLastName());
        paymentPage.fillForm(cardInfo);
        paymentPage.successfulPayment();
    }

    // failed, should pass
    @Test
    void shouldNotPayWithNameWithDigits() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyOwnerCard(DataHelper.getOneNumber());
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // failed, should pass
    @Test
    void shouldNotPayWithNameOnlyWithSigns() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyOwnerCard(DataHelper.getOwnerNameOnlyWithSigns());
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // failed, should pass
    @Test
    void shouldNotPayWithNameWithSigns() {
        var paymentPage = mainPage.payByCard();
        var name = DataHelper.getOwnerName();
        var sign = DataHelper.getTwoSigns();
        var cardInfo = DataHelper.Registration.getAnyOwnerCard(name + sign);
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // pass
    @Test
    void shouldNotPayWithoutName() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyOwnerCard(getEmptyField());
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // pass
    @Test
    void shouldNotPayWithCVCWithOneDigit() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyCVCCard(DataHelper.getOneNumber());
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    // failed
    @Test
    void shouldNotPayWithCVCMoreThenTreeDigit() {
        var paymentPage = mainPage.payByCard();
        var cvc = DataHelper.getCVC();
        var digit = DataHelper.getOneNumber();
        var cardInfo = DataHelper.Registration.getAnyCVCCard(cvc + digit);
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcShouldHave(cardInfo.getCardCVC());
    }

    // failed
    @Test
    void shouldNotPayWithCVCWithLetters() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyCVCCard(DataHelper.getTwoLetter());
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcShouldHave(cardInfo.getCardCVC());
    }

    //  failed
    @Test
    void shouldNotPayWithCVCWithSigns() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyCVCCard(DataHelper.getCVCWithSigns());
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcShouldHave(cardInfo.getCardCVC());
    }

    //  pass
    @Test
    void shouldNotPayWithoutCVC() {
        var paymentPage = mainPage.payByCard();
        var cardInfo = DataHelper.Registration.getAnyCVCCard(getEmptyField());
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }
}
