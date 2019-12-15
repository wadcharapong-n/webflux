package com.education.webflux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WebfluxApplication.class)
class HelloHandlerTest {

    @Autowired
    RouteFunctionalConfig routeFunctionalConfig;

    @Autowired
    HelloHandler helloHandler;

    WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient
                .bindToRouterFunction(routeFunctionalConfig.routerHello(helloHandler))
                .build();
    }

    @Test
    void showMessage() {

        client.get()
                .uri("/route/hello")
                .exchange()
                .expectStatus()
                .isOk();

//        verify(helloHandler).showMessage(any());
    }
}