package com.service;

import com.model.Basket;
import com.model.Item;
import com.model.Price;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public class WebScrapperServiceImpl implements com.service.WebScrapperService {

    final static Logger logger = Logger.getLogger(com.service.WebScrapperService.class);
    @Value("${url.to.parse}")
    private String urlToParse;
    @Value("${product.urls.selector}")
    private String productUrlsSelector;

    @Value("${fruit.filter}")
    private String fruitFilter;
    @Value("${base.url}")
    private String baseUrl;
    @Value("${url.replace.pattern}")
    private String urlReplacePattern;
    @Value("${price.selector}")
    private String priceSelector;
    @Value("${price.regex}")
    private String priceRegex;

    @Value("${title.selector}")
    private String titleSelector;
    @Value("${description.selector}")
    private String descriptionSelector;
    @Value("${kcal.selector}")
    private String kcalSelector;
    @Value("${fallback.kcal.selector}")
    private String fallbackKcalSelector;
    @Value("${kcal.regex}")
    private String kcalRegex;

    private static final String HREF_ATTRIBUTE = "href";

    @Override
    public Document getWebpageDocument(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document;
        } catch (Exception e) {
            logger.error("Website couldn't be parsed.", e);
        }

        return null;
    }

    @Override
    public List<String> fetchProductUrls(Document document) {

        Elements elements = document.select(productUrlsSelector);

        return elements.stream()
                .map(element -> element.attr(HREF_ATTRIBUTE))
                .map(element -> baseUrl + element.replaceFirst(urlReplacePattern, ""))
                .filter(element -> element.contains(fruitFilter))
                .collect(Collectors.toList());
    }

    @Override
    public Basket parseProductUrls(List<String> productUrls) {

        Basket basket = new Basket();
        List<Item> items = new ArrayList<>();
        double gross = 0;


        for (String url : productUrls) {
            try {
                Document productPage = Jsoup.connect(url).get();
                Item item = new Item();

                String productDescription = getProductDescription(productPage);
                String productTitle = getProductTitle(productPage);
                Double productKCal = getProductKCal(productPage);
                Double productPrice = getProductPrice(productPage);

                item.setDescription(productDescription);
                if (productKCal != null) {
                    item.setKcal(productKCal);
                }
                item.setTitle(productTitle);
                item.setUnitPrice(productPrice);

                items.add(item);
                gross += productPrice;

            } catch (IOException e) {
                logger.error(String.format("Couldn't parse {}", url), e);
            }
        }

        Price price = new Price(gross);
        basket.setItems(items);
        basket.setPrice(price);

        return basket;
    }

    private String getProductTitle(Document document) {
        Element productElement = document.select(titleSelector).first();
        return productElement.text();
    }

    private String getProductDescription(Document document) {
        Element descriptionElement = document.select(descriptionSelector).first();
        return descriptionElement.text();
    }

    private Double getProductPrice(Document document) {
        Element priceElement = document.select(priceSelector).first();
        return Double.parseDouble(priceElement.text().replaceAll(priceRegex, ""));
    }

    private Double getProductKCal(Document document) {
        Element kcalElement = null;
        if(document.select(kcalSelector).first() != null) {
            kcalElement = document.select(kcalSelector).first();
        } else if (document.select(fallbackKcalSelector).first() != null) {
            kcalElement = document.select(fallbackKcalSelector).first();
        } else {
            return null;
        }
        return Double.parseDouble(kcalElement.selectFirst("td").text().replaceAll(kcalRegex, ""));
    }
}
