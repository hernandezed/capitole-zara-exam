package com.edh.capitole.infrastructure;

import com.edh.capitole.infrastructure.harness.ResponseReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class GetPriceAtDayTest extends CapitoleExamInfrastructureApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @ParameterizedTest
    @MethodSource("getAllParams")
    void doGet_withParams_doReturnHttpStatus200WithPrice(Long brandId, Long productId, LocalDateTime date, String jsonResponse) {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("brandId", brandId)
                        .queryParam("productId", productId)
                        .queryParam("date", date)
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody().json(jsonResponse);
    }

    @Test
    void doGet_withoutBrandId_returnHttpStatus400() throws IOException {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("productId", 1L)
                        .queryParam("date", LocalDateTime.now())
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody().json(ResponseReader.readJsonResponse("doGet_BAD_REQUEST_missingBrandId"));
    }

    @Test
    void doGet_withoutProductId_returnHttpStatus400() throws IOException {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("brandId", 1L)
                        .queryParam("date", LocalDateTime.now())
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody().json(ResponseReader.readJsonResponse("doGet_BAD_REQUEST_missingProductId"));
    }

    @Test
    void doGet_withoutDate_returnHttpStatus400() throws IOException {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("brandId", 1L)
                        .queryParam("productId", 1L)
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody().json(ResponseReader.readJsonResponse("doGet_BAD_REQUEST_missingDate"));
    }

    @Test
    void doGet_whenProductNotExists_returnHttpStatus404() throws IOException {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/prices")
                        .queryParam("brandId", 1L)
                        .queryParam("productId", 50L)
                        .queryParam("date", LocalDateTime.now())
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody().json(ResponseReader.readJsonResponse("doGet_NOT_FOUND"));
    }

    private static Stream<Arguments> getAllParams() throws IOException {
        return Stream.of(
                Arguments.arguments(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0), ResponseReader.readJsonResponse("doGet_brandId=1AndproductId=35455AndDate=2020-6-14T10:00")),
                Arguments.arguments(1L, 35455L, LocalDateTime.of(2020, 6, 14, 16, 0), ResponseReader.readJsonResponse("doGet_brandId=1AndproductId=35455AndDate=2020-6-14T16:00")),
                Arguments.arguments(1L, 35455L, LocalDateTime.of(2020, 6, 14, 21, 0), ResponseReader.readJsonResponse("doGet_brandId=1AndproductId=35455AndDate=2020-6-14T21:00")),
                Arguments.arguments(1L, 35455L, LocalDateTime.of(2020, 6, 15, 10, 0), ResponseReader.readJsonResponse("doGet_brandId=1AndproductId=35455AndDate=2020-6-15T10:00")),
                Arguments.arguments(1L, 35455L, LocalDateTime.of(2020, 6, 16, 21, 0), ResponseReader.readJsonResponse("doGet_brandId=1AndproductId=35455AndDate=2020-6-15T16:00"))
        );
    }
}
