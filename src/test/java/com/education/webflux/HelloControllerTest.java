package com.education.webflux;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WebfluxApplication.class)
public class HelloControllerTest {

    @Autowired
    RouteFunctionalConfig routeFunctionalConfig;

    @Autowired
    HelloHandler helloHandler;

    @Autowired
    private WebTestClient client;

    @Test
    void hello() {
        client.get()
                .uri("/hello")
                .exchange()
                .expectStatus()
                .isOk();
    }
}