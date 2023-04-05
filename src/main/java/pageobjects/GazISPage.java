package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GazISPage extends BasePage {

    // описание локаторов страницы

    // меню Продукты
    private final By menuProducts = By.xpath(".//li[@id = 'item-109']/a[text() = 'Продукты']");

    // ссылка на страницу продукта Ankey IDM
    private final By productAnkeyIDM = By.xpath(".//li[@id = 'div431']//a[text() = 'Ankey IDM']");

    // закладка Материалы на странице продукта Ankey IDM
    private final By tabMaterials = By.xpath(".//a[@id = 'tab-materialy']//*[text() = 'Материалы']");

    // ссылка на Руководство пользователя Ankey IDM на закладке Материалы
    private final By linkAnkeyIDMUserGuide
            = By.xpath(".//a[contains(@href, 'ankey-idm-manual-user.html') " +
            "and text() = 'Руководство пользователя Ankey IDM']");

    // список элементов меню Продукты
    private final By listOfProducts = By.xpath("//*[@id= 'div109']/ul/li");


    // конструктор объекта страницы
    public GazISPage(WebDriver driver) {
        super(driver);
    }


    // методы страницы

    // клик по меню Продукты
    public void clickOnMenuProducts() {

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(menuProducts));

        driver.findElement(menuProducts).click();

        // явное ожидание до момента полного открытия меню Продукты
        // чтобы гарантированно был доступен клик по любому продукту из списка
        // поскольку меню всплывающее и полное открытие занимает какое-то время
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfAllElements(driver.findElements(listOfProducts)));
    }

    // клик по ссылке на страницу продукта Ankey IDM
    public void clickOnProductAnkeyIDM() {

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(productAnkeyIDM));

        driver.findElement(productAnkeyIDM).click();
    }

    // клик по закладке Материалы на странице продукта Ankey IDM
    public void clickOnTabMaterials() {

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(tabMaterials));

        driver.findElement(tabMaterials).click();
    }

    // клик по ссылке на Руководство пользователя Ankey IDM на закладке Материалы
    public void clickOnAnkeyIDMUserGuideLink() {

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(linkAnkeyIDMUserGuide));

        WebElement ankeyIDMUserGuide = driver.findElement(linkAnkeyIDMUserGuide);

        // при открытии страницы нужный элемент может быть вне экрана и не кликабельным
        // скрипт для прокрутки страницы до нужного элемента
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView();", ankeyIDMUserGuide);

        ankeyIDMUserGuide.click();
    }
}
