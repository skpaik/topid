package io.goribco.apis.repository;

import io.goribco.apis.model.profile.ProfileData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileDataRepository extends MongoRepository<ProfileData, String> {
    Optional<ProfileData> findByUrl(String url);

    Page<ProfileData> findByOwnerIdOrderByUpdatedAtMomentAsc(String ownerId, Pageable pageable);

    Page<ProfileData> findByOwnerIdOrderByNameAscEnabled(String ownerId, Pageable pageable);
}
