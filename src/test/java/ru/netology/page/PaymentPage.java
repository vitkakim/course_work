package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement cardNumberError = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement monthError = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement yearError = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement expiredCardError = $(byText("Истек срок действия карты")).parent().$(".input__sub");
    private SelenideElement ownerError = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvcError = $(byText("CVC/CVV")).parent().$(".input__sub");

    public void fillForm(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getOwner());
        cvcField.setValue(cardInfo.getCardCVC());
        continueButton.click();
    }

    public void notFilledForm() {
        cardNumberError.shouldBe(visible);
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        ownerError.shouldBe(visible);
        cvcError.shouldBe(visible);
    }

    public void cardNumberErrorVisible(String number) {
        cardNumberField.setValue(number);
        cardNumberError.shouldBe(visible);
    }

    public void cardNumberShouldHave(String number) {
        cardNumberErrorVisible(number);
        cardNumberField.shouldHave(value(number));
    }

    public void monthErrorVisible(String month) {
        monthField.setValue(month);
        monthError.shouldBe(visible);
    }

    public void monthShouldHave(String month) {
        monthErrorVisible(month);
        monthField.shouldHave(value(month));
    }

    public void yearErrorVisible(String year) {
        yearField.setValue(year);
        yearError.shouldBe(visible);
    }

    public void yearShouldHave(String year) {
        yearErrorVisible(year);
        yearField.shouldHave(value(year));
    }

    public void expiredCardErrorVisible() {expiredCardError.shouldBe(visible);
    }

    public void ownerErrorVisible() {
        ownerError.shouldBe(visible);
    }

    public void cvcErrorVisible(String cvc) {
        cvcField.setValue(cvc);
        cvcError.shouldBe(visible);
    }

    public void cvcShouldHave(String cvc) {
        cvcErrorVisible(cvc);
        cvcField.shouldHave(value(cvc));
    }

    public void successfulPayment() {
        $(".icon_name_ok").shouldBe(Condition.visible, Duration.ofSeconds(30));
    }

    public void declinedPayment() {
        $(byCssSelector(".icon_name_error")).shouldBe(Condition.visible, Duration.ofSeconds(20));
    }
}
