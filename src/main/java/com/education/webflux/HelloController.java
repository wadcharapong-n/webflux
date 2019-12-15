package com.education.webflux;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
public class HelloController {

    @GetMapping(path = "/hello")
    public Mono<ResponseEntity> hello(@RequestParam(value = "message",required = false) Optional<String> message){
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(message.orElse("Noooooo")));
    }
}
