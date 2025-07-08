package io.goribco.apis.helper;

import io.goribco.apis.model.profile.*;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

public class ProfileDataHelper extends BaseDataHelper {
    public static ProfileData createProfileData(ProfileCreateReq profileCreateReq) {

        ProfileData profileData = ProfileData.builder()
                .name(profileCreateReq.getName())
                .url(profileCreateReq.getUrl())
                .website(profileCreateReq.getWebsite())
                .location(profileCreateReq.getLocation())
                .founded(profileCreateReq.getFounded())
                .profileVisibility(profileCreateReq.getProfileVisibility())
                .wallPostAuthority(profileCreateReq.getWallPostAuthority())
                .wallPostVisibility(profileCreateReq.getWallPostVisibility())
                .allowAnonymousWallPost(profileCreateReq.isAllowAnonymousWallPost())
                .allowAnonymousWallPostFrom(profileCreateReq.getAllowAnonymousWallPostFrom())
                .type(ProfileType.MAIN)
                .showInList(profileCreateReq.getShowInList())
                .showInUserAccount(profileCreateReq.getShowInUserAccount())
                .build();

        if (profileCreateReq.getType() != null && profileCreateReq.getType().equals(ProfileType.SUB)) {
            profileData.setType(ProfileType.SUB);
        }

        createCommonFields(profileData);

        return profileData;
    }

    public static ProfileRes getProfileResponse(ProfileData profileData) {
        ProfileRes profileRes = ProfileRes.builder()
                .name(profileData.getName())
                .url(profileData.getUrl())
                .website(profileData.getWebsite())
                .location(profileData.getLocation())
                .founded(profileData.getFounded())
                .profileVisibility(profileData.getProfileVisibility())
                .wallPostAuthority(profileData.getWallPostAuthority())
                .wallPostVisibility(profileData.getWallPostVisibility())
                .allowAnonymousWallPost(profileData.isAllowAnonymousWallPost())
                .allowAnonymousWallPostFrom(profileData.getAllowAnonymousWallPostFrom())
                .type(ProfileType.MAIN)
                .showInList(profileData.getShowInList())
                .showInUserAccount(profileData.getShowInUserAccount())
                .build();

        updateCommonResponse(profileRes, profileData);

        final List<Link> links = new ArrayList<>();
        links.add(Link.of("link 420"));
        links.add(Link.of("link 421"));
        profileRes.setLinks(links);

        return profileRes;
    }


    public static void updateProfileData(ProfileData profileData, ProfileUpdateReq profileUpdateReq) {

        profileData.setName(profileUpdateReq.getName());
        profileData.setUrl(profileUpdateReq.getUrl());
        profileData.setWebsite(profileUpdateReq.getWebsite());
        profileData.setLocation(profileUpdateReq.getLocation());
        profileData.setFounded(profileUpdateReq.getFounded());

        updateCommonFields(profileData);
    }

    public static List<ProfileRes> getProfileListResponse(List<ProfileData> profileDataList) {

        List<ProfileRes> profileResList = new ArrayList<>();

        for (ProfileData profileData : profileDataList) {
            profileResList.add(getProfileResponse(profileData));
        }

        return profileResList;
    }


    public ProfileUpdateReq optimizeProfileUpdateReq(ProfileUpdateReq loginReq) {
        // loginReq.setProfileVisibility(null);
        return loginReq;
    }
}
