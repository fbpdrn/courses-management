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
        setId("about-view");
        setSpacing(false);

        Image img = new Image("images/logo.png", "Logo");
        img.setId("logo");
        img.setWidth("200px");
        add(img);

        H2 title = new H2(resourceBundle.getString("app_name"));
        title.setId("title");
        Paragraph p1 = new Paragraph(resourceBundle.getString("about_studentid"));
        p1.setId("studentid");
        Paragraph p2= new Paragraph(resourceBundle.getString("about_email"));
        p2.setId("studentemail");

        add(title);
        add(p1);
        add(p2);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
