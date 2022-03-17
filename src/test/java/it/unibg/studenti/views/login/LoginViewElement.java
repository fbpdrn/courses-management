package it.unibg.studenti.views.login;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Element("vaadin-login-form")
@Attribute(name = "id", value = "vaadinLoginForm")
public class LoginViewElement extends TestBenchElement {

    public void login(String username, String password) {
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("vaadinLoginUsername")));

        $(TextFieldElement.class).id("vaadinLoginUsername")
                .setValue(username);
        $(PasswordFieldElement.class).id("vaadinLoginPassword")
                .setValue(password);
        $(ButtonElement.class).first()
                .click();
    }

    protected ButtonElement getButton() {
        return $(ButtonElement.class).first();
    }
}
