package it.unibg.studenti.views.settings;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import it.unibg.studenti.data.service.UserService;
import it.unibg.studenti.generated.tables.records.UserRecord;

public class UserGrid extends Grid<UserRecord> {
    private UserDialog dialog;
    private UserService userService;

    public UserGrid(UserService service){
        this.userService = service;
        dialog = new UserDialog(userService, this, false);
        addColumn(UserRecord::getUsername)
                .setHeader("Username");
        addColumn(UserRecord::getName)
                .setHeader("Name");
        addColumn(UserRecord::getSurname)
                .setHeader("Surname");
        addComponentColumn( e -> new Span("******"))
                .setHeader("Password");
        addColumn(UserRecord::getRole)
                .setHeader("Role");
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
