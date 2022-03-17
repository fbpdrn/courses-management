package it.unibg.studenti;

import com.vaadin.testbench.RetryRule;
import com.vaadin.testbench.TestBenchTestCase;
import it.unibg.studenti.views.login.LoginViewElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.safari.SafariDriver;

public abstract class AbstractIT extends TestBenchTestCase {

    @Rule
    public RetryRule rule = new RetryRule(3);

    @Before
    public void setUp() {
        setDriver(new SafariDriver());
    }

    @After
    public void tearDown() {
        getDriver().quit();
    }

    protected void performLogin(String url) {
        getDriver().get(url);
        LoginViewElement loginViewElement = $(LoginViewElement.class).first();
        loginViewElement.login("admin", "admin");
    }
}
