package it.unibg.studenti.views.staff;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-vertical-layout")
@Attribute(name = "id", value = "staff-view")
public class StaffViewElement extends TestBenchElement {

    protected GridElement getGrid() {
        return $(GridElement.class).id("staff-grid");
    }

    protected ButtonElement getNewButton() {
        return $(ButtonElement.class).id("new-button");
    }

    protected TextFieldElement getSearchBarField() {
        return $(TextFieldElement.class).id("searchbar-field");
    }
}
