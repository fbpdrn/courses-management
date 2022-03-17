package it.unibg.studenti;

import com.vaadin.testbench.RetryRule;
import com.vaadin.testbench.TestBenchTestCase;
import io.github.bonigarcia.wdm.WebDriverManager;
import it.unibg.studenti.views.login.LoginViewElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class AbstractIT extends TestBenchTestCase {

    @Rule
    public RetryRule rule = new RetryRule(3);

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu");
        WebDriver chrome = new ChromeDriver(options);
        setDriver(chrome);
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
