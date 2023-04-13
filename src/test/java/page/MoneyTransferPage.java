package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private final SelenideElement dashboard = $("[data-test-id=dashboard]");
    private final SelenideElement amountOfTransfer = $("[data-test-id=amount] input");
    private final SelenideElement from = $("[data-test-id=from] input");
    private final SelenideElement to = $("[data-test-id=to]");
    private final SelenideElement refillButton = $("[data-test-id=action-transfer]");
    private final SelenideElement cancelTransfer = $("[data-test-id=action-cancel]");
    private final SelenideElement errorMessage = $("[data-test-id='error-message']");

    public void makeTransfer(String amount, DataHelper.CardInfo cardInfo) {
        amountOfTransfer.setValue(amount);
        from.setValue(cardInfo.getCardNumber());
        refillButton.click();
    }

    public DashboardPage validTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public DashboardPage cancelTransfer() {
        dashboard.shouldBe(visible);
        amountOfTransfer.setValue("300");
        from.setValue(DataHelper.getFirstCardInfo().getCardNumber());
        to.should(visible);
        cancelTransfer.click();
        return new DashboardPage();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
