package com.education.webflux;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class HelloHandler {

    public Mono<ServerResponse> showMessage(ServerRequest r){
        Optional<String> message = r.queryParam("message");
        return ServerResponse.ok().bodyValue(message.orElse("No!!!"));
    }
}
