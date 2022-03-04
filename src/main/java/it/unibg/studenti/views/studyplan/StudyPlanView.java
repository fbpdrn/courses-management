package it.unibg.studenti.views.studyplan;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import it.unibg.studenti.views.MainLayout;

@PageTitle("Study Plans")
@Route(value = "studyplans", layout = MainLayout.class)
@AnonymousAllowed
public class StudyPlanView extends VerticalLayout {

    public StudyPlanView() {
        add(new Paragraph("Not Implemented"));
    }

}
