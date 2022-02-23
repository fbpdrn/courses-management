package it.unibg.studenti.views.settings;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import it.unibg.studenti.data.service.UserService;
import it.unibg.studenti.generated.tables.records.UserRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGrid extends Grid<UserRecord> {
    private UserDialog dialog;
    private UserService userService;

    @Autowired
    ResourceBundleWrapper resourceBundle;

    public UserGrid(UserService service, ResourceBundleWrapper resourceBundle){
        this.resourceBundle = resourceBundle;
        this.userService = service;
        dialog = new UserDialog(userService, this, false, resourceBundle);
        addColumn(UserRecord::getUsername)
                .setHeader(resourceBundle.getString("component_settings_users_username"));
        addColumn(UserRecord::getName)
                .setHeader(resourceBundle.getString("component_settings_users_name"));
        addColumn(UserRecord::getSurname)
                .setHeader(resourceBundle.getString("component_settings_users_surname"));
        addComponentColumn( e -> new Span("******"))
                .setHeader(resourceBundle.getString("component_settings_users_password"));
        addColumn(UserRecord::getRole)
                .setHeader(resourceBundle.getString("component_settings_users_role"));
        addItemClickListener(e -> {
           dialog.openAndSetBinder(e.getItem());
        });
    }

    public UserDialog getDialog() {
        return dialog;
    }

    public void setDialog(UserDialog dialog) {
        this.dialog = dialog;
    }

    public void refresh() {
        setItems(userService.getAll());
    }
}
