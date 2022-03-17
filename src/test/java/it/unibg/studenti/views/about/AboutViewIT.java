package it.unibg.studenti.views.about;

import it.unibg.studenti.AbstractIT;
import org.junit.Assert;
import org.junit.Test;

public class AboutViewIT extends AbstractIT {

    @Test
    public void checkTitle() {
        getDriver().get("http://localhost:8080/about");
        AboutViewElement aboutView = $(AboutViewElement.class).first();
        Assert.assertTrue("Title exists and is not empty.", aboutView.getTitle().getText().length() > 0);
    }

    @Test
    public void checkId() {
        getDriver().get("http://localhost:8080/about");
        AboutViewElement aboutView = $(AboutViewElement.class).first();
        Assert.assertTrue("ID exists and is correct.", aboutView.getStudentId().getText().contains("1042015"));
    }


    @Test
    public void checkEmail() {
        getDriver().get("http://localhost:8080/about");
        AboutViewElement aboutView = $(AboutViewElement.class).first();
        Assert.assertTrue("Email with right domain.", aboutView.getStudentEmail().getText().contains("@studenti.unibg.it"));
    }
}
