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
    private SelenideElement cardNumber = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private SelenideElement owner = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvc = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement cardNumberError = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement monthError = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement yearError = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement expiredCardError = $(byText("Истек срок действия карты")).parent().$(".input__sub");
    private SelenideElement ownerError = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvcError = $(byText("CVC/CVV")).parent().$(".input__sub");

    public void fillForm(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        owner.setValue(cardInfo.getOwner());
        cvc.setValue(cardInfo.getCardCVC());
        continueButton.click();
    }

    public void notFilledForm() {
        cardNumberError.shouldBe(visible);
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        ownerError.shouldBe(visible);
        cvcError.shouldBe(visible);
    }

    public void cardNumberErrorVisible() {
        cardNumberError.shouldBe(visible);
    }

    public void cardNumberShouldHave(String number) {
        cardNumber.shouldHave(value(number));
    }

    public void monthErrorVisible() {
        monthError.shouldBe(visible);
    }

    public void monthShouldHave(String number) {
        month.shouldHave(value(number));
    }

    public void yearErrorVisible() {
        yearError.shouldBe(visible);
    }

    public void yearShouldHave(String number) {
        year.shouldHave(value(number));
    }

    public void expiredCardErrorVisible() {
        expiredCardError.shouldBe(visible);
    }

    public void ownerErrorVisible() {
        ownerError.shouldBe(visible);
    }

    public void cvcErrorVisible() {
        cvcError.shouldBe(visible);
    }

    public void cvcShouldHave(String number) {
        cvc.shouldHave(value(number));
    }

    public void successfulPayment() {
        $(".notification_status_ok").shouldBe(Condition.visible, Duration.ofSeconds(30));
    }

    public void declinedPayment() {
        $(byCssSelector("div.notification.notification_status_error.notification_has-closer.notification_stick-to_right.notification_theme_alfa-on-white")).shouldBe(Condition.visible, Duration.ofSeconds(20));
    }
}
