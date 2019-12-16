package com.education.webflux.handler;

import com.education.webflux.config.JWTUtil;
import com.education.webflux.model.UserProfile;
import com.education.webflux.service.UserService;
import org.reactivestreams.Publisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RegisterHandler {

    final
    JWTUtil jwtUtil;

    final
    UserService userService;

    public RegisterHandler(JWTUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public Mono<ServerResponse> getInfo(ServerRequest r){
        return r.principal()
                .flatMap(o -> userService.findByUsername(o.getName()))
                .flatMap(userProfile -> ServerResponse.ok().bodyValue(userProfile))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> getAllUser(ServerRequest r){
        return defaultReadResponse(userService.getAllUserProfile());
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<UserProfile> profiles) {
        return ServerResponse
                .ok()
                .body(profiles, UserProfile.class);
    }
}
