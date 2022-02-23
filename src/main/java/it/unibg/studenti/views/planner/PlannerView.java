package it.unibg.studenti.views.planner;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.unibg.studenti.views.MainLayout;

import javax.annotation.security.RolesAllowed;

@PageTitle("Planner")
@Route(value = "Planner", layout = MainLayout.class)
@RolesAllowed("user")
public class PlannerView extends VerticalLayout {

    public PlannerView() {
        add(new Paragraph("Not Implemented"));
    }

}
