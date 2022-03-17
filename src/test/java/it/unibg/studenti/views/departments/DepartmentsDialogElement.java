package it.unibg.studenti.views.departments;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.LabelElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-form-layout")
@Attribute(name = "id", value = "form-layout")
public class DepartmentsDialogElement extends TestBenchElement {

    protected LabelElement getErrorLabel() {
        return $(LabelElement.class).id("error-label");
    }

    protected TextFieldElement getNameField() {
        return $(TextFieldElement.class).id("name-field");
    }

    protected TextFieldElement getDescriptionField() {
        return $(TextFieldElement.class).id("description-field");
    }

    protected TextFieldElement getLocationField() {
        return $(TextFieldElement.class).id("location-field");
    }

    protected TextFieldElement getStreetField() {
        return $(TextFieldElement.class).id("street-field");
    }

    protected TextFieldElement getStreetNumField() {
        return $(TextFieldElement.class).id("streetnum-field");
    }

    protected TextFieldElement getPhoneField() {
        return $(TextFieldElement.class).id("phone-field");
    }

    protected ButtonElement getSaveButton() {
        return $(ButtonElement.class).id("save-button");
    }

    protected ButtonElement getAbortButton() {
        return $(ButtonElement.class).id("abort-button");
    }

    protected ButtonElement getDeleteButton() {
        return $(ButtonElement.class).id("delete-button");
    }

}
