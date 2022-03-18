package it.unibg.studenti.views;

import com.vaadin.flow.component.html.testbench.ParagraphElement;
import it.unibg.studenti.AbstractIT;
import org.junit.Assert;
import org.junit.Test;

public class RouteNotFoundIT extends AbstractIT {

    @Test
    public void viewNotFoundRoute() {
        String wrongRoute = "wrongRoute";
        performLogin("http://localhost:8080/"+wrongRoute);
        ParagraphElement element = $(ParagraphElement.class).first();
        Assert.assertTrue("wrong route exposed.", element.getText().contains(wrongRoute));
    }

}