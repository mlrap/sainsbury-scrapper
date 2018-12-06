package com.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Basket;
import org.apache.log4j.Logger;

public class JsonUtils {

    final static Logger logger = Logger.getLogger(JsonUtils.class);

    public String convertToJson(Basket basket) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(basket);
        } catch (JsonProcessingException e) {
            logger.error(String.format("JSON object couldn't be created from {}", basket), e);
        }

        return null;
    }
}
