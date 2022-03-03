package it.unibg.studenti.views.courses;

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

@PageTitle("Courses")
@Route(value = "courses", layout = MainLayout.class)
@RolesAllowed("user")
public class CoursesView extends AbstractView {
    private final CoursesLogic logic;

    public CoursesView(@Autowired ServiceManager service, @Autowired ResourceBundleWrapper resourceBundle, @Autowired AuthenticatedUser currentUser) {
        UserInfo userInfo = getUserInfo(service, currentUser);
        setSpacing(false);
        setSizeFull();

        logic = new CoursesLogic(this, service, resourceBundle, userInfo);

        CoursesGrid grid = new CoursesGrid(logic, resourceBundle);

        HorizontalLayout topLayout = new HorizontalLayout();
        Button btnNew = new Button(resourceBundle.getString("component_common_button_new"));
        btnNew.addClickListener(e -> new CoursesDialog(logic,grid,null, resourceBundle).open());
        TextField searchBar = new TextField();
        searchBar.setPlaceholder(resourceBundle.getString("component_courses_searchbar"));
        searchBar.addValueChangeListener(e -> grid.refresh(e.getValue()));
        searchBar.setValueChangeMode(ValueChangeMode.EAGER);
        topLayout.add(btnNew);
        topLayout.addAndExpand(searchBar);

        add(topLayout, grid);
    }
}
