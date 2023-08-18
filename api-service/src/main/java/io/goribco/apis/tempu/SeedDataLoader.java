package io.goribco.apis.tempu;

import io.goribco.apis.model.authmodels.users.UserReq;
import io.goribco.apis.model.exceptions.ModelDataNotFoundException;
import io.goribco.apis.model.exceptions.UrPathNotValidException;
import io.goribco.apis.model.friendship.FriendShipData;
import io.goribco.apis.model.profile.FriendShipType;
import io.goribco.apis.model.profile.ProfileCreateReq;
import io.goribco.apis.model.profile.ProfileData;
import io.goribco.apis.model.profile.ProfileUpdateReq;
import io.goribco.apis.repository.FriendShipRepository;
import io.goribco.apis.repository.ProfileDataRepository;
import io.goribco.apis.repository.UserDataRepository;
import io.goribco.apis.service.AuthService;
import io.goribco.apis.service.FriendShipService;
import io.goribco.apis.service.ProfileService;
import io.goribco.apis.utils.UniqueIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeedDataLoader implements CommandLineRunner {

    @Autowired
    UserDataRepository userDataRepository;
    @Autowired
    ProfileService profileService;
    @Autowired
    private AuthService authService;
    @Autowired
    private ProfileDataRepository profileDataRepository;
    @Autowired
    private FriendShipRepository friendShipRepository;
    @Autowired
    private FriendShipService friendShipService;

    @Override
    public void run(String... args) {
        try {
            loadUserData();
            loadProfileData();
            loadFriendShipData();
            updateProfileData();
            isProjectRunning();
        } catch (UrPathNotValidException e) {
            throw new RuntimeException(e);
        } catch (ModelDataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void isProjectRunning() {
        printLog("\n\n\n\nYOUR PROJECT RUNNING SUCCESSFULLY:  " + UniqueIdUtils.on().getNanoId(24) + "\n\n");
    }

    private void updateProfileData() throws ModelDataNotFoundException, UrPathNotValidException {
        if (profileDataRepository.count() == 0) {
            updateProfile(
                    "sudipta1", "aysharja1",
                    "sudipta2", "aysharja2",
                    "sudipta3", "aysharja3",
                    "sudipta4", "aysharja4",
                    "sudipta5", "aysharja5",
                    "sudipta6", "aysharja6"
            );
            printLog("Total Profile Update: " + profileDataRepository.count());
        }
    }

    private void updateProfile(String... profiles) throws ModelDataNotFoundException, UrPathNotValidException {

        for (String profileUrl : profiles) {
            ProfileUpdateReq profileCreateReq = new ProfileUpdateReq();

            profileCreateReq.setUrl(profileUrl);

            ProfileData profileData = null;

            profileData = profileService.getProfileBy(profileUrl);

            if (profileData != null) {
                profileData.setOwnerId("user11");
                profileService.updateProfile("user11", profileCreateReq);
            }
        }
    }

    private void loadProfileData() throws UrPathNotValidException {
        if (profileDataRepository.count() == 0) {
            createProfile(
                    "sudipta1", "aysharja1",
                    "sudipta2", "aysharja2",
                    "sudipta3", "aysharja3",
                    "sudipta4", "aysharja4",
                    "sudipta5", "aysharja5",
                    "sudipta6", "aysharja6"
            );
            printLog("Total Profile: " + profileDataRepository.count());
        }
    }

    private void createProfile(String... profiles) throws UrPathNotValidException {

        for (String profileUrl : profiles) {
            ProfileCreateReq profileCreateReq = new ProfileCreateReq();

            profileCreateReq.setUrl(profileUrl);

            profileService.saveProfile(profileCreateReq);
        }
    }

    private void loadFriendShipData() {
        if (friendShipRepository.count() == 0) {
            createFriendShip("sudipta1", "aysharja1", FriendShipType.FOLLOWING);
            createFriendShip("aysharja1", "sudipta1", FriendShipType.FOLLOWING);

            createFriendShip("sudipta2", "aysharja2", FriendShipType.BAN);
            createFriendShip("sudipta3", "aysharja3", FriendShipType.BLOCK);

            printLog("Total Friendship: " + friendShipRepository.count());
        }
    }

    private void createFriendShip(String profile1, String profile2, int friendShipType) {

        FriendShipData friendShipDataNew = new FriendShipData();
        FriendShipData friendShipDataIsFollowing = null;

        Optional<FriendShipData> isFollowingOptional = friendShipService.findByFromProfileAndToProfileAndFollowing(profile2, profile1, true);

        if (isFollowingOptional.isPresent()) {
            friendShipDataIsFollowing = isFollowingOptional.get();

            //TODO: IF BAN and BLOCK then  cannot be friend and cannot follow

            friendShipDataNew.setFriend(true);
        }

        friendShipDataNew.setFromProfile(profile1);
        friendShipDataNew.setToProfile(profile2);

        if (friendShipType == FriendShipType.FOLLOWING) {
            friendShipDataNew.setFollowing(true);
        } else if (friendShipType == FriendShipType.BAN) {
            friendShipDataNew.setBan(true);
        } else if (friendShipType == FriendShipType.BLOCK) {
            friendShipDataNew.setBlock(true);
            friendShipDataNew.setFollowing(false);
            friendShipDataNew.setFriend(false);
        }

        friendShipService.saveFriendShip(friendShipDataNew);

        if (friendShipDataIsFollowing != null) {
            friendShipDataIsFollowing.setFriend(true);

            if (friendShipType == FriendShipType.BLOCK) {

                friendShipDataIsFollowing.setFollowing(false);
                friendShipDataIsFollowing.setFriend(false);
            }

            friendShipService.saveFriendShip(friendShipDataIsFollowing);
        }
/*
        if (friendShipType == FriendShipType.FOLLOWING) {
            friendShipData = new FriendShipData();
            friendShipData.setFromProfile(profile2);
            friendShipData.setToProfile(profile1);
            friendShipData.setStatus(FriendShipType.FOLLOWERS);

            friendShipRepository.save(friendShipData);
        }


        if (friendShipType == FriendShipType.FOLLOWING) {

            // IF profile2 already follow me, then following code for create Friends

            friendShipData = new FriendShipData();
            friendShipData.setFromProfile(profile2);
            friendShipData.setToProfile(profile1);
            friendShipData.setStatus(FriendShipType.FRIENDS);

            friendShipRepository.save(friendShipData);

        }
        */
    }

    private void printLog(String s) {
        System.out.println(s);
    }

    private void loadUserData() {
        if (userDataRepository.count() == 0) {
            createUser(1);
            createUser(2);
            printLog("Total User: " + userDataRepository.count());
        }
    }

    private void createUser(int seq) {
        UserReq userReq = UserReq.builder().build();
        //userReq.setUId(UniqueIdUtils.on().getNanoId());
//        Role role = roleService.findByName(AuthConfig.ROLES.VIEWER);
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(role);

        // userReq.setRoles(roleSet);
        userReq.setUrl(UniqueIdUtils.on().getNanoId());
        userReq.setUsername("user" + seq);
        userReq.setPassword("user" + seq);
        userReq.setEmail("email" + seq + "@gmail.com");
        userReq.setPhone("+880171910007" + seq);
        userReq.setFirstName("First Name" + seq);
        userReq.setMiddleName("Middle Name" + seq);
        userReq.setLastName("Last Name" + seq);
        userReq.setBusinessTitle("businessTitle" + seq);

        authService.saveUser(userReq);
    }
}