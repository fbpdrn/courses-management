package it.unibg.studenti.views.courses;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.unibg.studenti.views.MainLayout;

import javax.annotation.security.RolesAllowed;

@PageTitle("Courses")
@Route(value = "courses", layout = MainLayout.class)
@RolesAllowed("user")
public class CoursesView extends VerticalLayout {

    public CoursesView() {
        add(new Paragraph("Not Implemented"));
    }

}
