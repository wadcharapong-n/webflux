package com.education.webflux.repository;

import com.education.webflux.model.UserProfile;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserProfileRepository extends ReactiveCrudRepository<UserProfile, ObjectId> {
    Mono<UserProfile> findByUsername(String username);
}
