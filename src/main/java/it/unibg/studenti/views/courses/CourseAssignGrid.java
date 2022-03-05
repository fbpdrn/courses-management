package it.unibg.studenti.views.courses;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import it.unibg.studenti.data.WorkingHour;
import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.generated.tables.records.ReferentRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public class CourseAssignGrid extends Grid<ReferentRecord>{
    private final CoursesLogic logic;
    private final CourseRecord record;
    private final ResourceBundleWrapper resourceBundle;
    private final Column<ReferentRecord> hoursColumn;

    public CourseAssignGrid(CoursesLogic logic, CourseRecord record, ResourceBundleWrapper resourceBundle) {
        this.logic = logic;
        this.resourceBundle = resourceBundle;
        this.record = record;

        addColumn(new ComponentRenderer<>(e -> new Span(logic.getService().getStaffService().getOne(e.getStaffIdstaff()).getEmail())))
                .setHeader(resourceBundle.getString("component_courses_staff"));
        addColumn(new ComponentRenderer<>(e -> {
            Span span = new Span(e.getType());
            if(e.getType().equals(WorkingHour.LESSON.getTypeName()))
                span.getElement().getThemeList().add("badge");
            else if(e.getType().equals(WorkingHour.TRAINING.getTypeName()))
                span.getElement().getThemeList().add("badge success");
            else if(e.getType().equals(WorkingHour.TUTORING.getTypeName()))
                span.getElement().getThemeList().add("badge error");
            else
                span.getElement().getThemeList().add("badge contrast");
            return span;
        }))
                .setHeader(resourceBundle.getString("component_courses_type"));
        hoursColumn = addColumn(ReferentRecord::getHours)
                .setHeader(resourceBundle.getString("component_courses_hours"));
        addColumn(new ComponentRenderer<>(e -> {
            Button btnDelete = new Button();
            Icon icon = new Icon(VaadinIcon.TRASH);
            icon.setColor("red");
            btnDelete.setIcon(icon);
            btnDelete.addClickListener(j -> {
                logic.delete(e);
                refresh();
            });
            return btnDelete;
        }))
                .setHeader(resourceBundle.getString("component_common_actions"));
        refresh();
    }

    public void refresh() {
        hoursColumn.setFooter(new Span(logic.getService().getCourseService().getHoursAssigned(record) + "/" + record.getHours()));
        setItems(logic.getService().getCourseService().getWorkAssigned(record));
    }
}
