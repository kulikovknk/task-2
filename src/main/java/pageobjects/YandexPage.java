package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class YandexPage extends BasePage {

    // описание локаторов страницы

    // поле поиска
    private final By searchBox = By.id("text");

    // конструктор объекта страницы
    public YandexPage(WebDriver driver) {
        super(driver);
    }


    // методы страницы


    // открыть страницу
    public void openPage() {

        driver.manage().window().maximize();
        driver.get(YANDEX_PAGE);
    }

    // вести текстовый запрос в строку поиска
    public void inputQueryInSearchBox(String searchQuery) {

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(searchBox));

        driver.findElement(searchBox).sendKeys(searchQuery);
    }

    // отправить поисковый запрос
    public void submitSearchQuery() {
        driver.findElement(searchBox).submit();
    }

    // получить список строк с результатами поиска, в которых содержится определенный URL,
    // используется метод findElements(), так как может быть несколько результатов, которые соответствуют этому критерию
    // также при отсутствии результата метод findElement() вернет ошибку и тест остановится,
    // а метод findElements() вернет null, который можно потом обработать
    public List<WebElement> findSearchResultContainingUrl(String url) {

        String searchXpath = String.format(".//a[contains(@href, '%s')]", url);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(searchXpath), 0));

        return driver.findElements(By.xpath(searchXpath));
    }

    public void clickFirstSearchResult(List<WebElement> searchResults) {

        if (searchResults.isEmpty()) {
            return;
        }

        // кликаем на первой строке результата поиска как наиболее релевантной
        searchResults.get(0).click();

        // при клике на поисковый запрос открывается новая закладка,
        // явное ожидание до момента открытия второй закладки
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.numberOfWindowsToBe(2));

        // Handle текущей вкладки окна браузера
        String originalWindowHandle = driver.getWindowHandle();

        // переключим драйвер на новую вкладку окна браузера
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
}
