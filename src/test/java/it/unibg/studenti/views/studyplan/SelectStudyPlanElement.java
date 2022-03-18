package it.unibg.studenti.views.studyplan;

import com.vaadin.flow.component.html.testbench.SelectElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-select")
public class SelectStudyPlanElement extends TestBenchElement {

    protected SelectElement getById(String id) {
        return $(SelectElement.class).id(id);
    }
}
