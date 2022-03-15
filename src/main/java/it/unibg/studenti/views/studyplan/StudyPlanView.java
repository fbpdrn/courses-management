package it.unibg.studenti.views.studyplan;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.generated.tables.records.DegreeRecord;
import it.unibg.studenti.generated.tables.records.YearRecord;
import it.unibg.studenti.views.MainLayout;
import it.unibg.studenti.views.planner.PlannerGrid;
import it.unibg.studenti.views.planner.PlannerLogic;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import it.unibg.studenti.views.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@PageTitle("Study Plans")
@Route(value = "studyplans", layout = MainLayout.class)
@AnonymousAllowed
public class StudyPlanView extends VerticalLayout {

    private final PlannerLogic logic;
    private final PlannerGrid grid;
    private DegreeRecord selectedDegree;
    private Select<YearRecord> selectYear;

    public StudyPlanView(@Autowired ServiceManager service, @Autowired ResourceBundleWrapper resourceBundle) {
        setSpacing(false);
        setSizeFull();
        logic = new PlannerLogic(null, service, resourceBundle, null);
        grid = new PlannerGrid(logic, resourceBundle);
        grid.getActionsColumn().setVisible(false);
        grid.getBadgeColumn().setVisible(false);
        add(createTopBar(logic, resourceBundle));
        add(grid);
    }

    private HorizontalLayout createTopBar(PlannerLogic logic, ResourceBundleWrapper resourceBundle){
        HorizontalLayout topLayout = new HorizontalLayout();

        selectYear = new Select<>();
        Select<DegreeRecord> selectDegree = new Select<>();
        Select<Double> selectAccYear = new Select<>();
        selectAccYear.setItemLabelGenerator(e -> {
            if (e.intValue() < 0)
                return (resourceBundle.getString("component_planner_all"));
            else
                return String.valueOf(e.intValue());
        });
        selectDegree.setEnabled(false);
        selectAccYear.setEnabled(false);
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
                selectDegree.setItems(logic.getService().getDegreeService().getDegreePublishedByYear(e.getValue()));
            logic.setCurrentDegree(null);
        });
        selectDegree.addValueChangeListener(e -> {
            grid.setItems(new ArrayList<>());
            selectAccYear.setValue(null);
            ArrayList<Double> list = new ArrayList<>();
            if(selectAccYear.isEnabled()) {
                selectAccYear.clear();
                selectAccYear.setValue(null);
            } else
                selectAccYear.setEnabled(true);
            if(e.getValue()!=null) {
                list.add(-1.0);
                for (int i = 1; i <= e.getValue().getAccyear(); i++)
                    list.add(Utility.convertToDouble(i));
                setSelectedDegree(e.getValue());
                logic.setCurrentDegree(getSelectedDegree());
            }
            selectAccYear.setItems(list);
        });

        selectAccYear.addValueChangeListener(e -> {
            if(e.getValue() != null) {
                logic.setCurrentDegree(getSelectedDegree());
                grid.refresh(getSelectedDegree(), e.getValue());
            }
        });
        selectYear.setItemLabelGenerator(e -> e.getYearstart() + " → " +e.getYearend());
        selectDegree.setItemLabelGenerator(e-> e.getCode() + " - " + e.getName());
        topLayout.add(selectYear, selectDegree, selectAccYear);
        return topLayout;
    }

    public PlannerGrid getGrid() {
        return grid;
    }

    public DegreeRecord getSelectedDegree() {
        return selectedDegree;
    }

    public void setSelectedDegree(DegreeRecord selectedDegree) {
        this.selectedDegree = selectedDegree;
    }
}
