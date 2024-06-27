package com.ingenia.travel.flujos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.ingenia.travel.infrastructure.adapters.input.rest.PathController;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.request.PathRequest;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.request.StationRequest;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.response.ShortPathResponse;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PathIntegrationTest {

   @Autowired
   private WebTestClient webTestClient;

   private static final Random RANDOM = new Random();

   @Autowired
   PathController pathController;

   @Test
   void canFindShortPath() {
      //create Stations
      int n = 4;
      IntStream.range(0, n).forEach(i -> addStation());

      //create paths
      addPaths(50.0, 1L, 2L);
      addPaths(100.0, 1L, 3L);
      addPaths(60.0, 1L, 4L);
      addPaths(20.0, 4L, 3L);

      //find short path
      int sourceId = 3;
      int destinationId = 2;
      Double costoEsperado = 130.0;
      List<Long> pathIdsEsperado = new ArrayList<>();
      pathIdsEsperado.add(3L);
      pathIdsEsperado.add(4L);
      pathIdsEsperado.add(1L);
      pathIdsEsperado.add(2L);

      ShortPathResponse response = webTestClient
            .get()
            .uri("/paths/{sourceId}/{destinationId}", sourceId, destinationId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(ShortPathResponse.class)
            .getResponseBody()
            .blockFirst();

      //check if the path is the "short path"
      assertThat(response.getCost()).isEqualTo(costoEsperado);
      assertThat(response.getPathIds()).isEqualTo(pathIdsEsperado);
   }

   public void addStation() {
      Faker faker = new Faker();
      Name fakerName = faker.name();
      StationRequest stationRequest = StationRequest.builder().name(fakerName.fullName()).build();

      webTestClient
            .post()
            .uri("/stations")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(stationRequest), StationRequest.class)
            .exchange()
            .expectStatus()
            .isCreated();
   }

   public void addPaths(Double cost, Long sourceId, Long destinationId) {
      PathRequest pathRequest = PathRequest.builder().cost(cost).sourceId(sourceId).destinationId(destinationId).build();

      webTestClient
            .post()
            .uri("/paths")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(pathRequest), PathRequest.class)
            .exchange()
            .expectStatus()
            .isCreated();
   }
}
