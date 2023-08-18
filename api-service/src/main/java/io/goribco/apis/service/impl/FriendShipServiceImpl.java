package io.goribco.apis.service.impl;

import io.goribco.apis.model.friendship.FriendShipData;
import io.goribco.apis.repository.FriendShipRepository;
import io.goribco.apis.service.FriendShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendShipServiceImpl implements FriendShipService {
    @Autowired
    private FriendShipRepository friendShipRepository;

    @Override
    public FriendShipData saveFriendShip(FriendShipData friendShipData) {
        return friendShipRepository.save(friendShipData);
    }

    @Override
    public Optional<FriendShipData> findByFromAndToProfile(String fromProfile, String toProfile) {
        return friendShipRepository.findByFromProfileAndToProfile(fromProfile, toProfile);
    }

    @Override
    public Optional<FriendShipData> findByFromProfileAndToProfileAndFollowing(String fromProfile, String toProfile, boolean isFollowing) {
        return friendShipRepository.findByFromProfileAndToProfileAndFollowing(fromProfile, toProfile, isFollowing);
    }

    @Override
    public Optional<FriendShipData> findByFromProfileAndToProfileAndFriend(String fromProfile, String toProfile, boolean isFriend) {
        return friendShipRepository.findByFromProfileAndToProfileAndFriend(fromProfile, toProfile, isFriend);
    }

    @Override
    public Optional<FriendShipData> findFollower(String fromProfile, String toProfile, boolean isFollower) {
        return friendShipRepository.findByFromProfileAndToProfileAndFriend(toProfile, fromProfile, isFollower);
    }

    @Override
    public Long countFollowers(String toProfile) {
        return friendShipRepository.countByToProfileAndFollowing(toProfile, true);
    }
}
