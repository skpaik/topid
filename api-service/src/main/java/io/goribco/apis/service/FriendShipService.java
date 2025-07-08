package io.goribco.apis.service;

import io.goribco.apis.model.friendship.FriendShipData;

import java.util.Optional;

public interface FriendShipService {
    FriendShipData saveFriendShip(FriendShipData friendShipData);

    Optional<FriendShipData> findByFromAndToProfile(String fromProfile, String toProfile);

    Optional<FriendShipData> findByFromProfileAndToProfileAndFollowing(String fromProfile, String toProfile, boolean isFollowing);

    Optional<FriendShipData> findByFromProfileAndToProfileAndFriend(String fromProfile, String toProfile, boolean isFriend);

    Optional<FriendShipData> findFollower(String fromProfile, String toProfile, boolean isFollower);

    Long countFollowers(String toProfile);
}
