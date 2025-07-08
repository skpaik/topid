package io.goribco.apis.repository;

import io.goribco.apis.model.wallpostreply.WallPostReplyData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WallPostReplyRepository extends MongoRepository<WallPostReplyData, String> {
    Optional<WallPostReplyData> findByUrl(String url);
}
