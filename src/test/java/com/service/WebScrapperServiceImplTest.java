package com.service;

import com.model.Basket;
import com.model.Item;
import com.model.Price;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class WebScrapperServiceImplTest {

    @InjectMocks
    private WebScrapperServiceImpl webScrapperService = new WebScrapperServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(webScrapperService, "productUrlsSelector", "ul.productLister a");
        ReflectionTestUtils.setField(webScrapperService, "baseUrl", "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/");
        ReflectionTestUtils.setField(webScrapperService, "urlReplacePattern", "(../)+");
        ReflectionTestUtils.setField(webScrapperService, "fruitFilter", "berries-cherries-currants");
        ReflectionTestUtils.setField(webScrapperService, "titleSelector", "div.productSummary div.productTitleDescriptionContainer h1");
        ReflectionTestUtils.setField(webScrapperService, "descriptionSelector", "div.productText p");
        ReflectionTestUtils.setField(webScrapperService, "priceSelector", "div.productSummary div.priceTabContainer div.pricing p.pricePerUnit");
        ReflectionTestUtils.setField(webScrapperService, "priceRegex", "[\\s+a-zA-Z /£]");
        ReflectionTestUtils.setField(webScrapperService, "kcalSelector", "div.productText div.tableWrapper table.nutritionTable tbody tr.tableRow0");
        ReflectionTestUtils.setField(webScrapperService, "fallbackKcalSelector", "div.textualNutrition div.tableWrapper table.nutritionTable tbody tr:nth-child(2) td");
        ReflectionTestUtils.setField(webScrapperService, "kcalRegex", "[a-zA-Z]");
    }

    @Test
    public void test_getWebpageDocument_notNull() {
        Document document = webScrapperService.getWebpageDocument("http://www.sainsburys.co.uk/");
        assertNotNull(document);
    }

    @Test
    public void test_getWebpageDocument_Null() {
        Document document = webScrapperService.getWebpageDocument("http://random_string");
        assertNull(document);
    }

    @Test
    public void test_fetchProductUrls() throws IOException {
        String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
        Document document = Jsoup.connect(url).get();

        List<String> actualUrls = webScrapperService.fetchProductUrls(document);

        List<String> expectedUrls = new ArrayList<>();
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-british-strawberries-400g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-blueberries-200g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-raspberries-225g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-blackberries--sweet-150g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-blueberries-400g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-blueberries--so-organic-150g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-raspberries--taste-the-difference-150g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherries-350g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-blackberries--tangy-150g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-strawberries--taste-the-difference-300g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherry-punnet-200g-468015-p-44.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-mixed-berries-300g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-mixed-berry-twin-pack-200g-7696255-p-44.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-redcurrants-150g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherry-punnet--taste-the-difference-250g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-blackcurrants-150g.html");
        expectedUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-british-cherry---strawberry-pack-600g.html");

        assertEquals(17, actualUrls.size());
        assertEquals(expectedUrls, actualUrls);
    }

    @Test
    public void test_parseProductUrls_doubleRow_On_KCal() {
        String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-british-strawberries-400g.html";
        List<String> urls = new ArrayList<>();
        urls.add(url);

        Basket testBasket = webScrapperService.parseProductUrls(urls);
        List<Item> items = testBasket.getItems();
        Price price = testBasket.getPrice();

        assertEquals(items.size(), 1);
        assertEquals(items.get(0).getDescription(), "by Sainsbury's strawberries");
        assertEquals(items.get(0).getKcal(), new Double(33));
        assertEquals(items.get(0).getUnitPrice(), new Double(1.75));
        assertEquals(items.get(0).getTitle(), "Sainsbury's Strawberries 400g");

        assertEquals(price.getGross(), new Double(1.75));
        assertEquals(price.getVat(), new Double(0.35));
    }

    @Test
    public void test_parseProductUrls_singleRow_On_KCal() {
        String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherry-punnet-200g-468015-p-44.html";
        List<String> urls = new ArrayList<>();
        urls.add(url);

        Basket testBasket = webScrapperService.parseProductUrls(urls);
        List<Item> items = testBasket.getItems();
        Price price = testBasket.getPrice();

        assertEquals(items.size(), 1);
        assertEquals(items.get(0).getDescription(), "Cherries");
        assertEquals(items.get(0).getKcal(), new Double(52));
        assertEquals(items.get(0).getUnitPrice(), new Double(1.50));
        assertEquals(items.get(0).getTitle(), "Sainsbury's Cherry Punnet 200g");

        assertEquals(price.getGross(), new Double(1.50));
        assertEquals(price.getVat(), new Double(0.30));
    }

    @Test
    public void test_parseProductUrls_no_KCal() {
        String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-british-cherry---strawberry-pack-600g.html";
        List<String> urls = new ArrayList<>();
        urls.add(url);

        Basket testBasket = webScrapperService.parseProductUrls(urls);
        List<Item> items = testBasket.getItems();
        Price price = testBasket.getPrice();

        assertEquals(items.size(), 1);
        assertEquals(items.get(0).getDescription(), "British Cherry & Strawberry Mixed Pack");
        assertNull(items.get(0).getKcal());
        assertEquals(items.get(0).getUnitPrice(), new Double(4.00));
        assertEquals(items.get(0).getTitle(), "Sainsbury's British Cherry & Strawberry Pack 600g");

        assertEquals(price.getGross(), new Double(4.00));
        assertEquals(price.getVat(), new Double(0.80));
    }
}