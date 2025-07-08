package io.goribco.apis.repository;

import io.goribco.apis.model.wallpost.WallPostData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WallPostRepository extends MongoRepository<WallPostData, String> {
    Optional<WallPostData> findByUrl(String url);

    Page<WallPostData> findByPostToProfile(String postToProfile, Pageable pageable);

    Page<WallPostData> findByPostToProfileAndPostFromProfile(String postToProfile, String postFromProfile, Pageable pageable);
}
