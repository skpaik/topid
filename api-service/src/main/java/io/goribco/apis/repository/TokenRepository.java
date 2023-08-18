package io.goribco.apis.repository;

import io.goribco.apis.model.token.TokenData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<TokenData, Integer> {

    List<TokenData> findAllByUserUrl(String url);

    Optional<TokenData> findByToken(String token);
}
