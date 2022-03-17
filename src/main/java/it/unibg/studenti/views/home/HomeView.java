package it.unibg.studenti.views.home;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import it.unibg.studenti.views.MainLayout;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public HomeView(@Autowired ResourceBundleWrapper resourceBundle) {
        setId("home-view");
        add(new H2(resourceBundle.getString("component_home_welcome")));
        add(new Paragraph(resourceBundle.getString("component_home_intro")));
        add(new Paragraph(resourceBundle.getString("component_home_howto")));
    }

}
