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

    @BeforeEach
    public void setUp() {
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
