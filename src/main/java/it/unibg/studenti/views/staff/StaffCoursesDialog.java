package it.unibg.studenti.views.staff;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.generated.tables.records.StaffRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

import java.text.MessageFormat;
import java.util.List;

public class StaffCoursesDialog extends Dialog {

    private final StaffLogic logic;
    private final StaffRecord record;
    private final ResourceBundleWrapper resourceBundle;

    public StaffCoursesDialog(StaffLogic logic, StaffRecord record, ResourceBundleWrapper resourceBundle) {
        setId("staffcourses-dialog");
        this.logic = logic;
        this.record = record;
        this.resourceBundle = resourceBundle;
        add(createCoursesLayout());
    }

    private VerticalLayout createCoursesLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("staffcourses-layout");
        layout.add(new H2(resourceBundle.getString("component_staff_coursesof") + record.getFirstname() + " " + record.getMiddlename() + " " + record.getSurname()));
        layout.add(createCoursesVisualizer(logic.getService().getCourseService().getCoursesByStaff(record)));
        return layout;
    }

    private VerticalLayout createCoursesVisualizer(List<CourseRecord> list) {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("staffcoursesvisualizer-layout");
        Span coursesCounterParagraph = new Span();
        coursesCounterParagraph.setId("counter-span");
        if(list.size() == 0)
            coursesCounterParagraph.setText(resourceBundle.getString("component_staff_coursesnotfound"));
        else
            coursesCounterParagraph.setText(MessageFormat.format(resourceBundle.getString("component_staff_coursesfound"), list.size()));
        layout.add(coursesCounterParagraph);
        String before = "";
        for(CourseRecord r : list) {
            if(!r.getYearoff().equals(before)) {
                layout.add(new H3(resourceBundle.getString("component_courses_yearoff") + " " + r.getYearoff()));
                before = r.getYearoff();
            }
            layout.add(new Span(r.getCode() + " " + r.getSsd() + " " + r.getName() + " (" + r.getCredits() + ")"));
        }
        return  layout;
    }
}
