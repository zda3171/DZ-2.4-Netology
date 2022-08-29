package ru.netology.dz24.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.dz24.data.DataHelper;
import ru.netology.dz24.page.DashboardPage;
import ru.netology.dz24.page.LoginPage;
import ru.netology.dz24.page.MoneyTransfer;

import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }
    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        var dashboardPage = new DashboardPage();
        int expected = dashboardPage.getCardBalance("1") + 1000;
        dashboardPage.depositToFirst();
        var transferPage = new MoneyTransfer();
        transferPage.moneyTransfer(DataHelper.CardInfo.getCardTwo(), "1000");
        int actual = dashboardPage.getCardBalance("1");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var dashboardPage = new DashboardPage();
        int expected = dashboardPage.getCardBalance("2") + 1000;
        dashboardPage.depositToSecond();
        var transferPage = new MoneyTransfer();
        transferPage.moneyTransfer(DataHelper.CardInfo.getCardOne(), "1000");
        int actual = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldTransferTotalDeposit() {
        var dashboardPage = new DashboardPage();
        String balance = toString(dashboardPage.getCardBalance("1"));
        dashboardPage.depositToSecond();
        var transferPage = new MoneyTransfer();
        transferPage.moneyTransfer(DataHelper.CardInfo.getCardOne(), balance);
        int expected = 0;
        int actual = dashboardPage.getCardBalance("1");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldTransferTwice() {
        var dashboardPage = new DashboardPage();
        int expected = dashboardPage.getCardBalance("2") + 4000;
        dashboardPage.depositToSecond();
        var transferPage = new MoneyTransfer();
        transferPage.moneyTransfer(DataHelper.CardInfo.getCardOne(), "1000");
        dashboardPage.depositToSecond();
        transferPage.moneyTransfer(DataHelper.CardInfo.getCardOne(), "3000");
        int actual = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);
    }


    @Test
    void shouldNotTransferMoreThanBalance() {
        var dashboardPage = new DashboardPage();
        String balance = toString(dashboardPage.getCardBalance("1") + 1000);
        dashboardPage.depositToSecond();
        var transferPage = new MoneyTransfer();
        transferPage.moneyTransfer(DataHelper.CardInfo.getCardOne(), balance);

        transferPage.getError().shouldBe(Condition.visible);
    }

    private String toString(int cardBalance) {
        String result = Integer.toString(cardBalance);
        return result;
    }

}
