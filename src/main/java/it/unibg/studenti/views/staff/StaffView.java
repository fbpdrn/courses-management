package it.unibg.studenti.views.staff;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.data.service.StaffService;
import it.unibg.studenti.data.service.UserService;
import it.unibg.studenti.generated.tables.records.StaffRecord;
import it.unibg.studenti.generated.tables.records.UserRecord;
import it.unibg.studenti.security.AuthenticatedUser;
import it.unibg.studenti.views.MainLayout;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@PageTitle("Staff")
@Route(value = "Staff", layout = MainLayout.class)
@RolesAllowed("user")
public class StaffView extends VerticalLayout {

    private StaffService service;
    private final StaffGrid grid;
    private final UserRecord currentRecordUser;

    public StaffView(@Autowired ServiceManager service, @Autowired ResourceBundleWrapper resourceBundle, @Autowired AuthenticatedUser currentUser) {
        setSpacing(false);
        setSizeFull();
        currentRecordUser = getCurrentUser(service.getUserService(), currentUser);
        grid = new StaffGrid(service, resourceBundle, currentRecordUser);
        Button btnNew = new Button(resourceBundle.getString("component_common_button_new"));
        btnNew.addClickListener(e -> {
            StaffRecord newRecord = new StaffRecord();
            newRecord.setIdstaff(-1);
            StaffDialog dialog = new StaffDialog(service, grid, newRecord, resourceBundle, currentRecordUser);
            dialog.open();
        });
        HorizontalLayout topActions = new HorizontalLayout();
        TextField tfSearch = new TextField();
        tfSearch.setPlaceholder("Cerca per e-mail o per nome");
        tfSearch.addValueChangeListener(e -> grid.refresh(e.getValue().trim()));
        tfSearch.setValueChangeMode(ValueChangeMode.EAGER);
        topActions.add(btnNew);
        topActions.addAndExpand(tfSearch);
        add(topActions, grid);
    }

    private UserRecord getCurrentUser(UserService service, AuthenticatedUser user){
        return service.getOne(user.get().get().getIduser());
    }

}
