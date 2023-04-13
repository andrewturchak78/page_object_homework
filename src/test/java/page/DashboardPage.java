package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import lombok.val;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.awt.SystemColor.info;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.trim;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private static final ElementsCollection cards = $$(".list__item div");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

     public int getCardBalance(DataHelper.CardInfo info) {
       val text = cards.find(text(info.getCardNumber().substring(15))).getText();
        // TODO: перебрать все карты и найти по атрибуту data-test-id
        return extractBalance(text);
     }
    public MoneyTransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        cards.find(text(cardInfo.getCardNumber().substring(15))).$("button").click();
        return new MoneyTransferPage();
    }
    public DashboardPage reloadButton(){
        $("[data-test-id=action-reload]").click();
        return new DashboardPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
