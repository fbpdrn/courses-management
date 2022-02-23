package it.unibg.studenti.views.departments;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.unibg.studenti.views.MainLayout;

import javax.annotation.security.RolesAllowed;

@PageTitle("Departments")
@Route(value = "Departments", layout = MainLayout.class)
@RolesAllowed("user")
public class DepartmentsView extends VerticalLayout {

    public DepartmentsView() {
        add(new Paragraph("Not Implemented"));
    }

}
