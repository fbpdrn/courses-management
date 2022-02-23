package it.unibg.studenti.views.settings;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.unibg.studenti.data.service.UserService;
import it.unibg.studenti.data.service.YearService;
import it.unibg.studenti.generated.tables.records.UserRecord;
import it.unibg.studenti.generated.tables.records.YearRecord;
import it.unibg.studenti.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
@RolesAllowed("admin")
public class SettingsView extends HorizontalLayout {
    private final Tab appSettings;
    private final Tab userSettings;
    private VerticalLayout content;
    private UserGrid userGrid;
    private YearGrid yearGrid;
    @Autowired
    private UserService userService;

    @Autowired
    private YearService yearService;

    public SettingsView() {
        this.userService = userService;
        this.yearService = yearService;
        setSpacing(false);
        setSizeFull();

        appSettings = new Tab(VaadinIcon.COG.create(),new Span("Miscellaneous"));
        userSettings = new Tab(VaadinIcon.USER.create(),new Span("Users"));
        Tabs tabs = new Tabs(
                appSettings,userSettings
        );
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setWidth("300px");
        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );
        tabs.setSelectedTab(appSettings);
        add(tabs);
        content = new VerticalLayout();
        content.add(new Paragraph("Please select an option on the left navbar."));
        add(content);
    }

    private void setContent(Tab tab) {
        content.removeAll();

        if (tab.equals(appSettings)) {
            content.add(createAppSettingsContent());
        } else if (tab.equals(userSettings)) {
            content.add(createUserSettingsContent());
        }
    }

    private VerticalLayout createAppSettingsContent(){
        VerticalLayout layout = new VerticalLayout();
        yearGrid = new YearGrid(yearService);
        yearGrid.setItems(yearService.getAll());
        Button btNewYear = new Button("+");
        btNewYear.addClickListener(e -> {
            YearDialog yearDialog = new YearDialog(yearService,yearGrid, true);
            yearDialog.openAndSetBinder(new YearRecord());
        });
        layout.add(btNewYear,yearGrid);
        return layout;
    }

    private VerticalLayout createUserSettingsContent(){
        VerticalLayout layout = new VerticalLayout();
        userGrid = new UserGrid(userService);
        userGrid.setItems(userService.getAll());
        Button btNewUser = new Button("+");
        btNewUser.addClickListener(e -> {
            UserDialog userDialog = new UserDialog(userService,userGrid,true);
            userDialog.openAndSetBinder(new UserRecord());
        });
        layout.add(btNewUser,userGrid);
        return layout;
    }
}
