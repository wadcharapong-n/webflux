package com.education.webflux;

import com.education.webflux.bean.AuthRegister;
import com.education.webflux.bean.AuthRequest;
import com.education.webflux.bean.AuthResponse;
import com.education.webflux.bean.Role;
import com.education.webflux.config.JWTUtil;
import com.education.webflux.config.PBKDF2Encoder;
import com.education.webflux.handler.RegisterHandler;
import com.education.webflux.model.UserProfile;
import com.education.webflux.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.Arrays;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteFunctionalConfig {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PBKDF2Encoder passwordEncoder;

    @Bean
    RouterFunction<ServerResponse> router(RegisterHandler registerHandler){
        return route(GET("/login"), request ->
             request.bodyToMono(AuthRequest.class)
                    .flatMap(ar -> userProfileRepository.findByUsername(ar.getUsername())
                            .flatMap((userDetails) -> {
                        if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
                            return ServerResponse.ok().bodyValue(new AuthResponse(jwtUtil.generateToken(userDetails)));
                        } else {
                            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
                        }
                    }).defaultIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build().block()))
        )
                .andRoute(POST("/register-admin"),request -> {
                    return request.bodyToMono(AuthRegister.class)
                            .flatMap(authRegister -> {
                                UserProfile register = new UserProfile();
                                register.setUsername(authRegister.getUsername());
                                register.setPassword(passwordEncoder.encode(authRegister.getPassword()));
                                register.setEnabled(true);
                                register.setRoles(Arrays.asList(Role.ROLE_ADMIN));
                                return userProfileRepository.save(register);
                            })
                            .flatMap(userProfile -> ServerResponse.ok().bodyValue(userProfile))
                            .defaultIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).build().block());
                }
        )
                .andRoute(POST("/register-user"),request -> {
                    return request.bodyToMono(AuthRegister.class)
                            .flatMap(authRegister -> {
                                UserProfile register = new UserProfile();
                                register.setUsername(authRegister.getUsername());
                                register.setPassword(passwordEncoder.encode(authRegister.getPassword()));
                                register.setEnabled(true);
                                register.setRoles(Arrays.asList(Role.ROLE_USER));
                                return userProfileRepository.save(register);
                            })
                            .flatMap(userProfile -> ServerResponse.ok().bodyValue(userProfile))
                            .defaultIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).build().block());
                }
        )
                .andRoute(GET("/me"),registerHandler::getInfo)
                .andRoute(GET("/user"),registerHandler::getAllUser);
    }

//    @Bean
//    CorsWebFilter corsWebFilter() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedOrigins(Arrays.asList("*"));
//        corsConfig.setMaxAge(3600L);
//        corsConfig.addAllowedMethod("GET");
//        corsConfig.addAllowedMethod("OPTIONS");
//        corsConfig.addAllowedMethod("PATCH");
//        corsConfig.addAllowedMethod("POST");
//        corsConfig.addAllowedMethod("PUT");
//        corsConfig.addAllowedHeader("*");
//
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//
//        return new CorsWebFilter(source);
//    }
}
