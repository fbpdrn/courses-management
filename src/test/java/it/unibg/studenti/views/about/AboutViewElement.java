package it.unibg.studenti.views.about;

import com.vaadin.flow.component.html.testbench.H2Element;
import com.vaadin.flow.component.html.testbench.ParagraphElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-vertical-layout")
@Attribute(name = "id", value = "about-view")
public class AboutViewElement extends TestBenchElement {

    protected H2Element getTitle() {
        return $(H2Element.class).id("title");
    }

    protected ParagraphElement getStudentId() {
        return $(ParagraphElement.class).id("studentid");
    }

    protected ParagraphElement getStudentEmail() {
        return $(ParagraphElement.class).id("studentemail");
    }
}
