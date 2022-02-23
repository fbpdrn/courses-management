package it.unibg.studenti.views.about;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import it.unibg.studenti.views.MainLayout;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@AnonymousAllowed
public class AboutView extends VerticalLayout {
    public AboutView() {
        setSpacing(false);

        Image img = new Image("images/logo.png", "Logo");
        img.setWidth("200px");
        add(img);

        add(new H2("Course Management"));
        add(new Paragraph("Matricola: 1042015"));
        add(new Paragraph("e-mail: f.pedrini1@studenti.unibg.it"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
