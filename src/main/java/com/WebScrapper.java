package com;

import com.model.Basket;
import com.util.JsonUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.service.WebScrapperService;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class WebScrapper implements CommandLineRunner {

    final static Logger logger = Logger.getLogger(WebScrapper.class);

    @Value("${url.to.parse}")
    private String urlToParse;

    @Autowired
    private WebScrapperService webScrapperService;

    public static void main(String[] args) {
        logger.info("STARTING THE APPLICATION");
        //SpringApplication.run(WebScrapper.class, args);
        logger.info("APPLICATION FINISHED");

        try {
            Document dd = Jsoup.connect("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html").get();
            Elements els = dd.select("ul.productLister a");
            System.out.println(els);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(String[] args) {
        Document document = webScrapperService.getWebpageDocument(urlToParse);
        List<String> urlsToParse = webScrapperService.fetchProductUrls(document);
        Basket basket = webScrapperService.parseProductUrls(urlsToParse);

        JsonUtils jsonUtils = new JsonUtils();
        logger.info(jsonUtils.convertToJson(basket));
    }
}
