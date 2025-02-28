package com.TQS.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ProductFinderServiceIT {
    
    private ISimpleHttpClient httpClient;
    private ProductFinderService productFinderService;

    private String jsonResponse;
    private String jsonResponseForNonExistingProduct;

    @BeforeEach
    public void setUp() {
        jsonResponse = "{"
            + "\"id\": 3,"
            + "\"title\": \"Mens Cotton Jacket\","
            + "\"price\": 55.99,"
            + "\"description\": \"great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.\","
            + "\"category\": \"men's clothing\","
            + "\"image\": \"https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg\","
            + "\"rating\": {\"rate\": 4.7, \"count\": 500}"
            + "}";

        jsonResponseForNonExistingProduct = "{}";

        httpClient = new TqsBasicHttpClient();
        productFinderService = new ProductFinderService(httpClient);
    }

    @Test
    public void testFindProductDetails() throws IOException {
        Optional<Product> product = productFinderService.findProductDetails(3);

        assertTrue(product.isPresent());
        assertEquals(product.get().getId(), 3);
        assertEquals(product.get().getTitle(), "Mens Cotton Jacket");
    }

    @Test
    public void testFindProductDetailsForNonExistingProduct() throws IOException {
        Optional<Product> product = productFinderService.findProductDetails(300);

        assertFalse(product.isPresent());
    }
}
