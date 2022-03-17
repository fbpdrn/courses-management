package it.unibg.studenti.views.login;

import it.unibg.studenti.AbstractIT;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginViewIT extends AbstractIT {
    @Test
    public void doAdminLogin() {
        getDriver().get("http://localhost:8080/login");
        LoginViewElement loginViewElement = $(LoginViewElement.class).first();
        loginViewElement.login("admin", "admin");
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("home-view")), 5);

        Assert.assertTrue("Admin login success.", getDriver().getTitle().contains("Home"));
    }

    @Test
    public void failAdminLogin() {
        getDriver().get("http://localhost:8080/departments");
        LoginViewElement loginViewElement = $(LoginViewElement.class).first();
        loginViewElement.login("NotAdmin", "NotAdmin");
        Assert.assertTrue("Admin login failed.", getDriver().getCurrentUrl().contains("error"));
    }
}