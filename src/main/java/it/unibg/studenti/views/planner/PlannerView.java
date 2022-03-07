package it.unibg.studenti.views.planner;

import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.unibg.studenti.data.Color;
import it.unibg.studenti.data.Published;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.generated.tables.records.DegreeRecord;
import it.unibg.studenti.generated.tables.records.YearRecord;
import it.unibg.studenti.security.AuthenticatedUser;
import it.unibg.studenti.views.AbstractView;
import it.unibg.studenti.views.MainLayout;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import it.unibg.studenti.views.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

@PageTitle("Planner")
@Route(value = "Planner", layout = MainLayout.class)
@RolesAllowed("user")
public class PlannerView extends AbstractView {

    private final PlannerLogic logic;
    private final PlannerGrid grid;
    private DegreeRecord selectedDegree;
    private Select<YearRecord> selectYear;
    private final Button btnCourse;

    public PlannerView(@Autowired ServiceManager service, @Autowired ResourceBundleWrapper resourceBundle, @Autowired AuthenticatedUser currentUser) {
        UserInfo userInfo = getUserInfo(service, currentUser);
        setSpacing(false);
        setSizeFull();

        logic = new PlannerLogic(this, service, resourceBundle, userInfo);

        grid = new PlannerGrid(logic, resourceBundle);
        add(createTopBar(logic, resourceBundle));
        add(grid);

        btnCourse = new Button(resourceBundle.getString("component_planner_button_addcourse"));
        btnCourse.addClickListener(e -> {
            new PlannerCourseDialog(logic, grid, resourceBundle).open();
        });
        btnCourse.setEnabled(false);

        btnCourse.getElement().getStyle()
                .set("float", "right")
                .set("margin-right", "10px")
                .set("margin-left","auto");

        add(btnCourse);
    }

    private HorizontalLayout createTopBar(PlannerLogic logic, ResourceBundleWrapper resourceBundle){
        HorizontalLayout topLayout = new HorizontalLayout();
        Button btnNew = new Button(resourceBundle.getString("component_planner_button_newdegree"));
        btnNew.addClickListener(e -> new PlannerDegreeDialog(logic, null, resourceBundle).open());
        Button btnEdit = new Button(resourceBundle.getString("component_common_button_edit"));
        btnEdit.addClickListener(e -> new PlannerDegreeDialog(logic, getSelectedDegree(), resourceBundle).open());
        btnEdit.setEnabled(false);

        Button btnPost = new Button(resourceBundle.getString("component_planner_remove"));
        btnPost.addClickListener(e->{
           if(getSelectedDegree().getIspublished() == Published.PUBLISHED.getPublishedValue()) {
               getSelectedDegree().setIspublished(Published.NOTPUBLISHED.getPublishedValue());
               if(logic.update(getSelectedDegree()))
                   btnPost.setText(resourceBundle.getString("component_planner_remove"));
           } else {
               getSelectedDegree().setIspublished(Published.PUBLISHED.getPublishedValue());
               if(logic.update(selectedDegree))
                   btnPost.setText(resourceBundle.getString("component_planner_post"));
           }
        });
        btnPost.setEnabled(false);

        selectYear = new Select<>();
        Select<DegreeRecord> selectDegree = new Select<>();
        Select<Double> selectAccYear = new Select<>();
        selectDegree.setEnabled(false);
        selectAccYear.setEnabled(false);
        selectAccYear.setItemLabelGenerator(e -> {
            if (e.intValue() < 0.0) return (resourceBundle.getString("component_planner_all"));
            else return String.valueOf(e.intValue());
        });
        selectYear.setItems(logic.getService().getYearService().getAll());

        selectYear.addValueChangeListener(e -> {
            if(selectDegree.isEnabled()) {
                selectDegree.clear();
                selectAccYear.clear();
                selectAccYear.setValue(null);
                selectDegree.setValue(null);
            }
            else
                selectDegree.setEnabled(true);
            if(e.getValue() != null)
                selectDegree.setItems(logic.getService().getDegreeService().getDegreeByYear(e.getValue()));
            btnEdit.setEnabled(false);
            btnPost.setEnabled(false);
            logic.setCurrentDegree(null);
            btnCourse.setEnabled(false);
        });
        selectDegree.addValueChangeListener(e -> {
            ArrayList<Double> list = new ArrayList<>();
            if(selectAccYear.isEnabled()) {
                selectAccYear.clear();
                selectAccYear.setValue(null);
                btnEdit.setEnabled(false);
            } else
                selectAccYear.setEnabled(true);
            if(e.getValue()!=null) {
                list.add(-1.0);
                for (int i = 1; i <= e.getValue().getAccyear(); i++)
                    list.add(Utility.convertToDouble(i));
                setSelectedDegree(e.getValue());
                logic.setCurrentDegree(getSelectedDegree());
                btnCourse.setEnabled(true);
            }
            selectAccYear.setItems(list);
            btnEdit.setEnabled(true);
            btnPost.setEnabled(true);
            if(getSelectedDegree().getIspublished() == Published.NOTPUBLISHED.getPublishedValue())
                btnPost.setText(resourceBundle.getString("component_planner_post"));
            else
                btnPost.setText(resourceBundle.getString("component_planner_remove"));
        });

        selectAccYear.addValueChangeListener(e -> {
            logic.setCurrentDegree(getSelectedDegree());
            grid.refresh(getSelectedDegree(),e.getValue());
        });
        selectYear.setItemLabelGenerator(e -> e.getYearstart() + " → " +e.getYearend());
        selectDegree.setItemLabelGenerator(e-> e.getCode() + " - " + e.getName());
        selectAccYear.setItemLabelGenerator(e -> String.valueOf(e.intValue()));
        topLayout.add(btnNew, selectYear, selectDegree, selectAccYear, btnEdit, btnPost);
        return topLayout;
    }

    public DegreeRecord getSelectedDegree() {
        return selectedDegree;
    }

    public void setSelectedDegree(DegreeRecord selectedDegree) {
        this.selectedDegree = selectedDegree;
    }

    public PlannerGrid getGrid() {
        return grid;
    }

    public void changeStatusIcon(Icon icon, Color color){
        icon.setColor(color.getColorValue());
    }

    public Select<YearRecord> getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(Select<YearRecord> selectYear) {
        this.selectYear = selectYear;
    }
}
