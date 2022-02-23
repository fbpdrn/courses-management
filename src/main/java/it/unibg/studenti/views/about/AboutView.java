package it.unibg.studenti.views.about;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import it.unibg.studenti.views.MainLayout;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@AnonymousAllowed
public class AboutView extends VerticalLayout {
    public AboutView(@Autowired ResourceBundleWrapper resourceBundle) {
        setSpacing(false);

        Image img = new Image("images/logo.png", "Logo");
        img.setWidth("200px");
        add(img);

        add(new H2(resourceBundle.getString("app_name")));
        add(new Paragraph(resourceBundle.getString("about_studentid")));
        add(new Paragraph(resourceBundle.getString("about_email")));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
