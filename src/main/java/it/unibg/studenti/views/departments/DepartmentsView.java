package it.unibg.studenti.views.departments;

import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.unibg.studenti.data.service.DepartmentService;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.generated.tables.records.DepartmentRecord;
import it.unibg.studenti.security.AuthenticatedUser;
import it.unibg.studenti.views.AbstractView;
import it.unibg.studenti.views.MainLayout;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@PageTitle("Departments")
@Route(value = "departments", layout = MainLayout.class)
@RolesAllowed("admin")
public class DepartmentsView extends AbstractView {
    private final DepartmentsLogic logic;

    public DepartmentsView(@Autowired ServiceManager service, @Autowired ResourceBundleWrapper resourceBundle, @Autowired AuthenticatedUser currentUser) {
        setId("departments-view");
        UserInfo userInfo = getUserInfo(service, currentUser);
        setSpacing(false);
        setSizeFull();

        logic = new DepartmentsLogic(this, service, resourceBundle, userInfo);
        DepartmentsGrid grid = new DepartmentsGrid(logic, resourceBundle);
        Button btnNew = new Button(resourceBundle.getString("component_common_button_new"));
        btnNew.setId("new-button");
        btnNew.addClickListener(e -> {
                DepartmentsDialog dialog = new DepartmentsDialog(logic, grid, true, resourceBundle);
                dialog.openAndSetBinder(new DepartmentRecord());
        });
        add(btnNew, grid);
    }
}
