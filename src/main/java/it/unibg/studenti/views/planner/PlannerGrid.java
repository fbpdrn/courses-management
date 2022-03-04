package it.unibg.studenti.views.planner;

import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.avatar.AvatarGroup.AvatarGroupItem;
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
    public PlannerGrid(PlannerLogic logic, ResourceBundleWrapper resourceBundle) {
        this.logic = logic;

        addColumn(new ComponentRenderer<>(e-> new Span("[" + e.getYearoff() + "] " + e.getSsd() + "/" + e.getCode())))
                .setHeader("Y.OFF/SSD/Code/");
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
                .setHeader("Staff");
        addColumn(CourseRecord::getPeriod)
                .setSortable(true)
                .setHeader(resourceBundle.getString("component_courses_period"));
        addColumn(new ComponentRenderer<>(e -> {
            Icon status;
            double val = logic.getService().getCourseService().getOne(e.getIdcourse()).getHours() - logic.getService().getCourseService().getHoursAssigned(e.getIdcourse());
            if(val > 0)
                status = createIcon(VaadinIcon.WARNING, "Hours not assigned", "badge error");
            else if (val < 0)
                status = createIcon(VaadinIcon.CLOSE_SMALL, "Too many hours assigned", "badge error");
            else
                status = createIcon(VaadinIcon.CHECK, "Hours assigned", "badge success");
            return status;
        }))
                .setHeader("Hours Assigned");

        addItemClickListener(e -> {
            logic.getView().getNotification("Not implemented", true);
        });
    }

    public void refresh(CourseRecord item) {
        getDataCommunicator().refresh(item);
    }

    public void refresh(DegreeRecord degreeRecord, Double year) {
        setItems(logic.getService().getCourseService().getCoursesByDegreeAndYear(degreeRecord, year));
    }

    private Icon createIcon(VaadinIcon vaadinIcon, String label, String theme) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        icon.getElement().setAttribute("aria-label", label);
        icon.getElement().setAttribute("title", label);
        icon.getElement().getThemeList().add(theme);
        return icon;
    }
}
