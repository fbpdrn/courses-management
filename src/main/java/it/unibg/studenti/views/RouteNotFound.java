package it.unibg.studenti.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import javax.servlet.http.HttpServletResponse;

@Tag(Tag.DIV)
@ParentLayout(MainLayout.class)
public class RouteNotFound extends Component implements HasErrorParameter<NotFoundException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<NotFoundException> parameter) {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("error-layout");
        Paragraph p = new Paragraph("404 Page Not Found: " + event.getLocation().getPath());
        p.setId("error-paragraph");
        layout.add(p);
        getElement().appendChild(layout.getElement());
        return HttpServletResponse.SC_NOT_FOUND;
    }
}