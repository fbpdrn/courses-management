package it.unibg.studenti.views.staff;

import it.unibg.studenti.AbstractIT;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StaffViewIT extends AbstractIT {

    @Test
    public void checkUpdateGrid() {
        performLogin("http://localhost:8080/staff");
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("staff-view")), 5);
        StaffViewElement view = $(StaffViewElement.class).first();
        int rows = view.getGrid().getRowCount();
        view.getSearchBarField().setValue("DISA");
        int newRows = view.getGrid().getRowCount();
        Assert.assertTrue("filter get less rows", rows > newRows);
    }
}