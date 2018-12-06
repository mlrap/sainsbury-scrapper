package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Basket {

    private List<Item> items;
    private Price price;

    @JsonProperty("results")
    public List<com.model.Item> getItems() {
        return items;
    }

    public void setItems(List<com.model.Item> items) {
        this.items = items;
    }

    @JsonProperty("total")
    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "items=" + items +
                ", price=" + price +
                '}';
    }
}
