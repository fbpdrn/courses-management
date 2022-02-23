package it.unibg.studenti.views.staff;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.unibg.studenti.views.MainLayout;

import javax.annotation.security.RolesAllowed;

@PageTitle("Staff")
@Route(value = "Staff", layout = MainLayout.class)
@RolesAllowed("user")
public class StaffView extends VerticalLayout {

    public StaffView() {
        add(new Paragraph("Not Implemented"));
    }

}
