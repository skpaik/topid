package io.goribco.apis.repository;

import io.goribco.apis.model.friendship.FriendShipData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FriendShipRepository extends MongoRepository<FriendShipData, String> {
    Optional<FriendShipData> findByFromProfileAndToProfile(String fromProfile, String toProfile);

    Optional<FriendShipData> findByFromProfileAndToProfileAndFollowing(String fromProfile, String toProfile, boolean isFollowing);

    Optional<FriendShipData> findByFromProfileAndToProfileAndFriend(String fromProfile, String toProfile, boolean isFriend);

    Long countByToProfileAndFollowing(String toProfile, boolean following);
}
