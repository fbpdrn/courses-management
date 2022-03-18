package it.unibg.studenti.views.studyplan;

import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.orderedlayout.testbench.HorizontalLayoutElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;


@Element("vaadin-vertical-layout")
@Attribute(name = "id", value = "studyplan-view")
public class StudyPlanViewElement extends TestBenchElement {

    protected HorizontalLayoutElement getTopLayout() {
        return $(HorizontalLayoutElement.class).id("top-layout");
    }

    protected SelectStudyPlanElement getSelectYear() {
        return $(SelectStudyPlanElement.class).id("year-select");
    }

    protected SelectStudyPlanElement getSelectDegree() {
        return $(SelectStudyPlanElement.class).id("degree-select");
    }

    protected SelectStudyPlanElement getSelectYearAcc() {
        return $(SelectStudyPlanElement.class).id("accyear-select");
    }

    protected GridElement getGrid() {
        return $(GridElement.class).id("planner-grid");
    }

}
