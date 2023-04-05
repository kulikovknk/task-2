package ankeyidmtest;

import config.WebDriverFactory;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.WebStorage;


public class BaseTest {

    protected static WebDriver driver;

    @BeforeClass
    public static void startUp() {
        driver = WebDriverFactory.getWebDriver();
    }

    @After
    public void clearData() {

        // после завершения каждого теста очищаем куки и storage
        driver.manage().deleteAllCookies();
        ((WebStorage) driver).getSessionStorage().clear();
        ((WebStorage) driver).getLocalStorage().clear();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

}
