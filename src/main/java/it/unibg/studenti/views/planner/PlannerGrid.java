package it.unibg.studenti.views.planner;

import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.avatar.AvatarGroup.AvatarGroupItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.generated.tables.records.DegreeRecord;
import it.unibg.studenti.generated.tables.records.StaffRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public class PlannerGrid extends Grid<CourseRecord> {

    private final PlannerLogic logic;
    private Double lastYear = -1.0;
    private final Column<CourseRecord> actionsColumn;
    private final Column<CourseRecord> badgeColumn;
    public PlannerGrid(PlannerLogic logic, ResourceBundleWrapper resourceBundle) {
        setId("planner-grid");
        this.logic = logic;
        addColumn(new ComponentRenderer<>(e-> new Span("[" + e.getYearoff() + "] " + e.getSsd() + "/" + e.getCode())))
                .setHeader(resourceBundle.getString("component_courses_abbr"));
        addColumn(CourseRecord::getName)
                .setHeader(resourceBundle.getString("component_courses_name"));
        addColumn(CourseRecord::getCredits)
                .setHeader(resourceBundle.getString("component_courses_credits"));
        addColumn(new ComponentRenderer<>(e-> {
            AvatarGroup avatarGroup = new AvatarGroup();
            for(StaffRecord r: logic.getService().getStaffService().getStaffByCourse(e.getIdcourse())){
                avatarGroup.add(new AvatarGroupItem(r.getFirstname()+ " " + r.getSurname()));
            }
            return avatarGroup;
        }))
                .setHeader(resourceBundle.getString("component_courses_staff"));
        addColumn(CourseRecord::getPeriod)
                .setSortable(true)
                .setHeader(resourceBundle.getString("component_courses_period"));
        addColumn(CourseRecord::getYear)
                .setSortable(true)
                .setHeader(resourceBundle.getString("component_courses_year"));
        badgeColumn = addColumn(new ComponentRenderer<>(e -> {
            Icon status = createIcon(VaadinIcon.EXCLAMATION_CIRCLE, resourceBundle.getString("error_planner_errorhours"), "badge error");
            try {
                double val = logic.getService().getCourseService().getOne(e.getIdcourse()).getHours() - logic.getService().getCourseService().getHoursAssigned(e.getIdcourse());
                if (val > 0)
                    status = createIcon(VaadinIcon.WARNING, resourceBundle.getString("error_planner_lefthours"), "badge error");
                else if (val < 0)
                    status = createIcon(VaadinIcon.CLOSE_SMALL, resourceBundle.getString("error_planner_toomanyhours"), "badge error");
                else
                    status = createIcon(VaadinIcon.CHECK, resourceBundle.getString("error_planner_hoursok"), "badge success");
                return status;
            } catch (NullPointerException n) {
                return status;
            }
        }))
                .setHeader(resourceBundle.getString("component_courses_hours"));
        actionsColumn = addColumn(new ComponentRenderer<>(e -> {
            Button btnDelete = new Button();
            Icon icon = new Icon(VaadinIcon.TRASH);
            icon.setColor("red");
            btnDelete.setIcon(icon);
            btnDelete.addClickListener(j -> {
                logic.getService().getDegreeService().removeCourse(logic.getCurrentDegree(), e);
                refresh(logic.getCurrentDegree());
            });
            return btnDelete;
        }))
                .setHeader(resourceBundle.getString("component_common_actions"));
    }

    public void refresh(CourseRecord item) {
        getDataCommunicator().refresh(item);
    }

    public void refresh(DegreeRecord degreeRecord, Double year) {
        setItems(logic.getService().getCourseService().getCoursesByDegreeAndYear(degreeRecord, year));
        lastYear = year;
    }

    public void refresh(DegreeRecord degreeRecord) {
        setItems(logic.getService().getCourseService().getCoursesByDegreeAndYear(degreeRecord, lastYear));
    }

    private Icon createIcon(VaadinIcon vaadinIcon, String label, String theme) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        icon.getElement().setAttribute("aria-label", label);
        icon.getElement().setAttribute("title", label);
        icon.getElement().getThemeList().add(theme);
        return icon;
    }

    public Column<CourseRecord> getActionsColumn() {
        return actionsColumn;
    }
    public Column<CourseRecord> getBadgeColumn() {
        return badgeColumn;
    }
}
