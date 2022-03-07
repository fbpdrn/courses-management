package it.unibg.studenti.views.planner;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public class PlannerCourseDialog extends Dialog {

    private final PlannerLogic logic;
    private final PlannerGrid grid;
    private final ResourceBundleWrapper resourceBundle;
    public PlannerCourseDialog(PlannerLogic logic, PlannerGrid grid, ResourceBundleWrapper resourceBundle) {
        this.logic = logic;
        this.grid = grid;
        this.resourceBundle = resourceBundle;

        VerticalLayout layout = new VerticalLayout();
        Paragraph p = new Paragraph();
        p.setText(resourceBundle.getString("component_planner_selectDegree") + "[" + logic.getCurrentDegree().getCode() + "] " + logic.getCurrentDegree().getName());

        Button btnSave = new Button(resourceBundle.getString("component_common_button_save"));

        Select<CourseRecord> selectCourse = new Select<>();
        selectCourse.setLabel(resourceBundle.getString("component_planner_course"));
        selectCourse.setItemLabelGenerator(e -> "[" + e.getYearoff() + "] " + e.getCode() + " | " + e.getName() + " (C:" + e.getCredits()+")");
        selectCourse.setEnabled(false);
        selectCourse.addValueChangeListener(e -> btnSave.setEnabled(e.getValue() != null));

        btnSave.addClickListener(e -> {
            if(selectCourse.getValue() != null)
                if(logic.addCourse(logic.getCurrentDegree(), selectCourse.getValue())) {
                    grid.refresh(logic.getCurrentDegree());
                    this.close();
                }
        });

        Select<String> selectYearOff = new Select<>();
        selectYearOff.setLabel(resourceBundle.getString("component_courses_yearoff"));
        selectYearOff.setItems(logic.getService().getCourseService().getAllYearOff());
        selectYearOff.addValueChangeListener(e -> {
            if(e.getValue() != null) {
               selectCourse.setEnabled(true);
               selectCourse.setItems(logic.getService().getCourseService().getAllByYearOff(e.getValue()));
            } else
                selectCourse.setEnabled(false);
        });
        layout.add(p);
        layout.add(selectYearOff, selectCourse, btnSave);
        add(layout);
    }




}
