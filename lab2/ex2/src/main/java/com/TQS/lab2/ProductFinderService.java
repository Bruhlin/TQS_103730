package com.TQS.lab2;

import java.io.IOException;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductFinderService {
    private static final String API_PRODUCTS = "https://fakestoreapi.com/products";
    private final ISimpleHttpClient httpClient;

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Product> findProductDetails(Integer productId) throws IOException {
        String url = API_PRODUCTS + "/" + productId;
        String response = httpClient.doHttpGet(url);
        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(response, Product.class);
            if (product.getId() == null) {
                return Optional.empty();
            }
            return Optional.of(product);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
