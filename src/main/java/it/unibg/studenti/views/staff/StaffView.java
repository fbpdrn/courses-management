package it.unibg.studenti.views.staff;

import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.security.AuthenticatedUser;
import it.unibg.studenti.views.AbstractView;
import it.unibg.studenti.views.MainLayout;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@PageTitle("Staff")
@Route(value = "staff", layout = MainLayout.class)
@RolesAllowed("user")
public class StaffView extends AbstractView {
    private final StaffLogic logic;

    public StaffView(@Autowired ServiceManager service, @Autowired ResourceBundleWrapper resourceBundle, @Autowired AuthenticatedUser currentUser) {
        setId("staff-view");
        UserInfo userInfo = getUserInfo(service, currentUser);
        setSpacing(false);
        setSizeFull();

        logic = new StaffLogic(this, service, resourceBundle, userInfo);

        StaffGrid grid = new StaffGrid(logic, resourceBundle);

        HorizontalLayout topLayout = new HorizontalLayout();
        Button btnNew = new Button(resourceBundle.getString("component_common_button_new"));
        btnNew.setId("new-button");
        btnNew.addClickListener(e ->
                new StaffDialog(logic,grid,null, resourceBundle).open());
        TextField searchBar = new TextField();
        searchBar.setId("searchbar-field");
        searchBar.setPlaceholder(resourceBundle.getString("component_staff_searchbar"));
        searchBar.addValueChangeListener(e -> grid.refresh(e.getValue()));
        searchBar.setValueChangeMode(ValueChangeMode.EAGER);
        topLayout.add(btnNew);
        topLayout.addAndExpand(searchBar);
        add(topLayout, grid);
    }
}
