package it.unibg.studenti.views.departments;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.unibg.studenti.data.service.DepartmentService;
import it.unibg.studenti.generated.tables.records.DepartmentRecord;
import it.unibg.studenti.views.MainLayout;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@PageTitle("Departments")
@Route(value = "Departments", layout = MainLayout.class)
@RolesAllowed("admin")
public class DepartmentsView extends VerticalLayout {

    private DepartmentService service;
    private final DepartmentsGrid grid;

    public DepartmentsView(@Autowired DepartmentService service, @Autowired ResourceBundleWrapper resourceBundle) {
        setSpacing(false);
        setSizeFull();

        grid = new DepartmentsGrid(service, resourceBundle);
        Button btnNew = new Button(resourceBundle.getString("component_common_button_new"));
        btnNew.addClickListener(e -> {
                DepartmentsDialog dialog = new DepartmentsDialog(service, grid, true, resourceBundle);
                dialog.openAndSetBinder(new DepartmentRecord());
        });
        add(btnNew, grid);
    }
}
