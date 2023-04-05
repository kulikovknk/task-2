package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    // метод возвращает веб драйвер в зависимости от значения системного параметра browser
    // если параметр не задан, возвращается драйвер браузера Chrome
    public static WebDriver getWebDriver() {

        String driver = System.getProperty("browser", "chrome");

        // путь к файлу YandexDriver
        // заменить на актуальный если планируется для тестов использовать Яндекс браузер
        String binaryYandexDriverFile = "C:\\Program Files\\WebDriver\\bin\\yandexdriver.exe";

        switch (driver) {
            case "chrome":

                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                // параметр нужен для устранения ошибки при запуске Chrome версии 111
                // the tests throw exception when initializing the ChromiumDriver (version 111.0.5563.19):
                options.addArguments("--remote-allow-origins=*");

                return new ChromeDriver(options);

            case "yandex":

                System.setProperty("webdriver.chrome.driver", binaryYandexDriverFile);

                return new ChromeDriver();

            default:
                throw new IllegalStateException("Задано некорректное наименование браузера");
        }
    }
}
