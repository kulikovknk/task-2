package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;

    // при входе на yandex.ru срабатывает перенаправление на Яндекс Дзен
    // поэтому использую упрощенный вариант поисковой страницы Яндекса
    public static final String YANDEX_PAGE = "https://ya.ru/";

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // проверка что указанный файл загружен
    public boolean checkIfFileIsDownloaded(String fileName) {

        // правильно будет получить текущую папку для скачивания файлов из настроек браузера
        // не нашел такой способ, сделал более простой вариант
        String downloadDirectory = System.getProperty("user.home") + "/Downloads/";

        // явное ожидание до момента загрузки файла
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until((ExpectedCondition<Boolean>) driver -> {
                    File downloadedFile = new File(downloadDirectory + fileName);
                    return downloadedFile.exists();
                });
    }

    // проверка что страница по указанной ссылке загружена
    public boolean checkIfPageIsLoaded(String pageURL) {

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.urlContains(pageURL));

        return driver.getCurrentUrl().contains(pageURL);
    }
}
