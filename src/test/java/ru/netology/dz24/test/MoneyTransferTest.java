package ru.netology.dz24.test;

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
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldTransferMoneyFromFirstInSecond() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage dashboardPage = new DashboardPage();
        var startingBalance1 = dashboardPage.getCardBalance(0);
        var startingBalance2 = dashboardPage.getCardBalance(1);
        var moneyTransferPage = new MoneyTransfer();
        var amount = moneyTransferPage.getAmount();
        moneyTransferPage.transferFromFirstToSecond(amount);
        var actualBalance1 = dashboardPage.getCardBalance(0);
        var actualBalance2 = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(startingBalance1 + amount, actualBalance1);
        Assertions.assertEquals(startingBalance2, actualBalance2 + amount);
    }

    @Test
    void shouldTransferMoneyFromSecondInFirst() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage dashboardPage = new DashboardPage();
        var startingBalance1 = dashboardPage.getCardBalance(0);
        var startingBalance2 = dashboardPage.getCardBalance(1);
        var moneyTransferPage = new MoneyTransfer();
        var amount = moneyTransferPage.getAmount();
        moneyTransferPage.transferFromSecondToFirst(amount);
        var actualBalance1 = dashboardPage.getCardBalance(0);
        var actualBalance2 = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(startingBalance1 - amount, actualBalance1);
        Assertions.assertEquals(startingBalance2 + amount, actualBalance2);
    }

    @Test
    void shouldTransferMoneyFromSecondInSecond() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage dashboardPage = new DashboardPage();
        var startingBalance1 = dashboardPage.getCardBalance(0);
        var startingBalance2 = dashboardPage.getCardBalance(1);
        var moneyTransferPage = new MoneyTransfer();
        var amount = moneyTransferPage.getAmount();
        moneyTransferPage.transferFromSecondToSecond(amount);
        var actualBalance1 = dashboardPage.getCardBalance(0);
        var actualBalance2 = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(startingBalance1, actualBalance1);
        Assertions.assertEquals(startingBalance2, actualBalance2);
    }

    @Test
    void shouldTransferMoneyFromFirstInFirst() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage dashboardPage = new DashboardPage();
        var startingBalance1 = dashboardPage.getCardBalance(0);
        var startingBalance2 = dashboardPage.getCardBalance(1);
        var moneyTransferPage = new MoneyTransfer();
        var amount = moneyTransferPage.getAmount();
        moneyTransferPage.transferFromFirstToFirst(amount);
        var actualBalance1 = dashboardPage.getCardBalance(0);
        var actualBalance2 = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(startingBalance1, actualBalance1);
        Assertions.assertEquals(startingBalance2, actualBalance2);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondNegative() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage dashboardPage = new DashboardPage();
        var startingBalance1 = dashboardPage.getCardBalance(0);
        var startingBalance2 = dashboardPage.getCardBalance(1);
        var moneyTransferPage = new MoneyTransfer();
        var amount = 100000;
        moneyTransferPage.transferFromFirstToSecond(amount);
        var actualBalance1 = dashboardPage.getCardBalance(0);
        var actualBalance2 = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(startingBalance1 - amount, actualBalance1);
        Assertions.assertEquals(startingBalance2 + amount, actualBalance2);
    }
}
