package it.unibg.studenti.views.staff;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.html.testbench.LabelElement;
import com.vaadin.flow.component.html.testbench.SelectElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-form-layout")
@Attribute(name = "id", value = "form-layout")
public class StaffDialogElement extends TestBenchElement {

    protected LabelElement getErrorLabel() {
        return $(LabelElement.class).id("error-label");
    }

    protected SelectElement getDepartmentSelect() {
        return $(SelectElement.class).id("department-select");
    }
}
