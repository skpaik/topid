package io.goribco.apis.repository;

import io.goribco.apis.model.authmodels.users.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserDataRepository extends MongoRepository<UserData, String> {
    //UserData findByUsername(String username);
    Optional<UserData> findByUsername(String username);
}