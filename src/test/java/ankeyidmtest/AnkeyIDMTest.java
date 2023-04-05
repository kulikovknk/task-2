package ankeyidmtest;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import pageobjects.*;


public class AnkeyIDMTest extends BaseTest {

    @Test
    @DisplayName("Тест: Скачать руководство пользователя Ankey IDM с сайта www.gaz-is.ru")
    public void AnkeyIDMUserGuideDownloadPositiveTest() {

        // параметры теста
        String searchQuery = "Газинформсервис";
        String searchURL = "www.gaz-is.ru";
        String ankeyIDMPageURL = "https://www.gaz-is.ru/produkty/upravlenie-ib/ankey-idm.html";
        String ankeyIDMUserGuideFileName = "ankey-idm-manual-user.pdf";

        // тестовые шаги
        findWebPageInYandex(searchQuery, searchURL);
        downloadAnkeyIDMUserGuide(ankeyIDMPageURL, ankeyIDMUserGuideFileName);
    }

    @Step("Найти веб страницу по заданному поисковому запросу и URL")
    public void findWebPageInYandex(String searchQuery, String searchURL) {

        YandexPage objYandexPage = new YandexPage(driver);

        // зайти на сайт Яндекса
        objYandexPage.openPage();
        // ввести запрос в поисковую строку
        objYandexPage.inputQueryInSearchBox(searchQuery);
        // отправить поисковый запрос
        objYandexPage.submitSearchQuery();
        // среди результатов поиска найти тот, который соответствует определенному сайту
        List<WebElement> searchResults = objYandexPage.findSearchResultContainingUrl(searchURL);

        // проверить что результат поискового запроса не пустой
        Assert.assertNotNull(String.format("URL: %s не найден в результатах поиска", searchURL), searchResults);

        // перейти по ссылке найденного результата
        objYandexPage.clickFirstSearchResult(searchResults);
    }

    @Step("Найти ссылку и скачать руководство пользователя Ankey IDM")
    public void downloadAnkeyIDMUserGuide(String pageURL, String fileName) {

        // на предыдущих шагах страница уже была открыта и драйвер переключен на нее
        // открытвать ее отдельно через метод get() не требуется
        GazISPage objGasISPage = new GazISPage(driver);

        // на странице кликнуть по меню «Продукты»
        objGasISPage.clickOnMenuProducts();
        // в списке продуктов кликнуть по ссылке на продукте «Ankey IDM»
        objGasISPage.clickOnProductAnkeyIDM();

        // проверить что URL открытой страницы соответствует заданному
        boolean isAnkeyIDMPageLoaded = objGasISPage.checkIfPageIsLoaded(pageURL);

        // убедиться, что загрузилась страница с информацией о продукте
        Assert.assertTrue("Не удалось открыть страницу продукта Ankey IDM"
                , isAnkeyIDMPageLoaded);

        // перейти за закладку «Материалы»
        objGasISPage.clickOnTabMaterials();
        // скачать руководство пользователя Ankey IDM
        objGasISPage.clickOnAnkeyIDMUserGuideLink();

        boolean isAnkeyIDMUserGuideDownloaded = objGasISPage.checkIfFileIsDownloaded(fileName);

        // убедиться что файл загружен
        Assert.assertTrue("Не удалось скачать руководство пользователя Ankey IDM"
                , isAnkeyIDMUserGuideDownloaded);
    }
}
