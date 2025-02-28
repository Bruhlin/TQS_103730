package com.TQS.lab2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindProductDetailsTests {

    @Mock
    private ISimpleHttpClient httpClient;

    @InjectMocks
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
    }

    @Test
    public void testFindProductDetails() throws IOException {
        when(httpClient.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn(jsonResponse);

        Optional<Product> product = productFinderService.findProductDetails(3);

        assertThat(product.isPresent(), equalTo(true));
        assertThat(product.get().getId(), equalTo(3));
        assertThat(product.get().getTitle(), equalTo("Mens Cotton Jacket"));
    }

    @Test
    public void testFindProductDetailsForNonExistingProduct() throws IOException {
        when(httpClient.doHttpGet("https://fakestoreapi.com/products/300")).thenReturn(jsonResponseForNonExistingProduct);

        Optional<Product> product = productFinderService.findProductDetails(300);

        assertThat(product.isPresent(), equalTo(false));
    }
}
