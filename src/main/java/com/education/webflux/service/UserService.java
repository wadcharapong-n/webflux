package com.education.webflux.service;

import com.education.webflux.bean.Role;
import com.education.webflux.model.UserProfile;
import com.education.webflux.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    UserProfileRepository userProfileRepository;

//    //username:passwowrd -> user:user
//    private final String userUsername = "user";// password: user
//    private final UserProfile user = new UserProfile(userUsername, "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Arrays.asList(Role.ROLE_USER));
//
//    //username:passwowrd -> admin:admin
//    private final String adminUsername = "admin";// password: admin
//    private final UserProfile admin = new UserProfile(adminUsername, "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true, Arrays.asList(Role.ROLE_ADMIN));
//
//    public Mono<UserProfile> findByUsername(String username) {
//        if (username.equals(userUsername)) {
//            return Mono.just(user);
//        } else if (username.equals(adminUsername)) {
//            return Mono.just(admin);
//        } else {
//            return Mono.empty();
//        }
//    }

    public Mono<UserProfile> findByUsername(String username) {
        return userProfileRepository.findByUsername(username);
    }

    public Flux<UserProfile> getAllUserProfile(){
        return userProfileRepository.findAll();
    }

}
