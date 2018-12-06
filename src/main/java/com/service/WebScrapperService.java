package com.service;

import com.model.Basket;
import org.jsoup.nodes.Document;

import java.util.List;


public interface WebScrapperService {

    Document getWebpageDocument(String url);

    List<String> fetchProductUrls(Document document);

    Basket parseProductUrls(List<String> products);
}
