package org.example.integrationstest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiTests {

    private HttpResponse<String> sendRequest(String url) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void getProductsReturns200() throws Exception {

        HttpResponse<String> response =
                sendRequest("https://fakestoreapi.com/products");

        assertEquals(403, response.statusCode()); //testing if Git get 403
    }


    @Test
    void productsWithCorrectAmount() throws Exception {

        HttpResponse<String> response =
                sendRequest("https://fakestoreapi.com/products");

        assertEquals(200, response.statusCode());

        int size =
                com.jayway.jsonpath.JsonPath.read(response.body(), "$.length()");

        assertEquals(20, size);
    }

    @Test
    void productWithCorrectFields() throws Exception {

        HttpResponse<String> response =
                sendRequest("https://fakestoreapi.com/products/1");

        assertEquals(200, response.statusCode());

        String title =
                com.jayway.jsonpath.JsonPath.read(response.body(), "$.title");

        Double price =
                com.jayway.jsonpath.JsonPath.read(response.body(), "$.price");

        String category =
                com.jayway.jsonpath.JsonPath.read(response.body(), "$.category");

        assertNotNull(title);
        assertNotNull(price);
        assertNotNull(category);
    }

    @Test
    void productIdReturnsCorrectProduct() throws Exception {

        HttpResponse<String> response =
                sendRequest("https://fakestoreapi.com/products/1");

        assertEquals(200, response.statusCode());

        int id =
                com.jayway.jsonpath.JsonPath.read(response.body(), "$.id");

        assertEquals(1, id);
    }
}