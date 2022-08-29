package ru.netology.dz24.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import lombok.Value;
import ru.netology.dz24.data.DataHelper;
import ru.netology.dz24.page.DashboardPage;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class MoneyTransfer {
        private SelenideElement amount = $("[data-test-id=amount] input");
        private SelenideElement fromField = $("[data-test-id=from] input");
        private SelenideElement transfer = $("[data-test-id=action-transfer]");
        private SelenideElement error = $("[data-test-id=error-notification]");

        public DashboardPage moneyTransfer(DataHelper.CardInfo from, String howMuch) {
            amount.setValue(howMuch);
            fromField.setValue(from.getNumber());
            transfer.click();
            return new DashboardPage();
        }

        public SelenideElement getError() {
            return error;
        }
    }
