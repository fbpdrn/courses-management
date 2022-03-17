package it.unibg.studenti.views.departments;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-vertical-layout")
@Attribute(name = "id", value = "departments-view")
public class DepartmentsViewElement extends TestBenchElement {

    protected GridElement getGrid() {
        return $(GridElement.class).id("departments-grid");
    }

    protected ButtonElement getNewButton() {
        return $(ButtonElement.class).id("new-button");
    }
}
