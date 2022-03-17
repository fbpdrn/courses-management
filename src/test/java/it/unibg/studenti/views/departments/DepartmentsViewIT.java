package it.unibg.studenti.views.departments;

import com.vaadin.flow.component.grid.testbench.GridElement;
import it.unibg.studenti.AbstractIT;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.UUID;

public class DepartmentsViewIT extends AbstractIT {

    @Test
    public void testForm() {
        performLogin("http://localhost:8080/departments");
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("departments-view")), 5);
        DepartmentsViewElement view = $(DepartmentsViewElement.class).first();
        view.getNewButton().click();
        Assert.assertTrue("form opened and it exists.", $(DepartmentsDialogElement.class).exists());
    }

    @Test
    public void testGrid() {
        performLogin("http://localhost:8080/departments");
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("departments-view")), 5);
        Assert.assertTrue("grid exits.", $(GridElement.class).exists());
    }

    @Test
    public void binderSetItemInForm()  {
        performLogin("http://localhost:8080/departments");
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("departments-view")), 5);
        DepartmentsViewElement view = $(DepartmentsViewElement.class).first();
        view.getGrid().getCell(0, 0).click();
        DepartmentsDialogElement dialog = $(DepartmentsDialogElement.class).first();
        Assert.assertTrue("binder set item.", dialog.getNameField().getText().length() > 0);
    }

    @Test
    public void binderNewItem() {
        performLogin("http://localhost:8080/departments");
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("departments-view")), 5);
        DepartmentsViewElement view = $(DepartmentsViewElement.class).first();
        int firstVal = view.getGrid().getRowCount();
        view.getNewButton().click();
        DepartmentsDialogElement dialog = $(DepartmentsDialogElement.class).first();
        String random = String.valueOf(UUID.randomUUID());
        dialog.getNameField().setValue("RandomName" + random);
        dialog.getDescriptionField().setValue("Description" + random);
        dialog.getLocationField().setValue("City" + random);
        dialog.getStreetField().setValue("Street" + random);
        dialog.getStreetNumField().setValue("N"+random);
        dialog.getPhoneField().setValue("+39 000000000");
        dialog.getSaveButton().click();
        int secondVal = view.getGrid().getRowCount();
        Assert.assertTrue("binder set item and create.", firstVal < secondVal);
    }

    @Test
    public void binderSetItemAndDelete() {
        performLogin("http://localhost:8080/departments");
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("departments-view")), 5);
        DepartmentsViewElement view = $(DepartmentsViewElement.class).first();
        view.getGrid().getCell(0, 0).click();
        DepartmentsDialogElement dialog = $(DepartmentsDialogElement.class).first();
        String valueFirstItem = dialog.getNameField().getValue(); //save pk value
        dialog.getDeleteButton().click();
        view.getGrid().getCell(0, 0).click();
        DepartmentsDialogElement dialogCheck = $(DepartmentsDialogElement.class).first();
        String valueSecondItem = dialogCheck.getNameField().getValue();
        Assert.assertNotEquals("binder set item and delete.", valueSecondItem, valueFirstItem);
    }
}