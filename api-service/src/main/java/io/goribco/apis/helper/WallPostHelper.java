package io.goribco.apis.helper;

import io.goribco.apis.model.profile.ProfileData;
import io.goribco.apis.model.wallpost.*;
import io.goribco.apis.utils.UniqueIdUtils;
import io.goribco.core.models.optionitems.OptionItem;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

public class WallPostHelper extends BaseDataHelper {
    public static WallPostRes getWallPostResponse(WallPostData wallPostData) {
        WallPostRes profileRes = WallPostRes.builder()
                .postFromProfile(wallPostData.getPostFromProfile())
                .postToProfile(wallPostData.getPostToProfile())
                .location(wallPostData.getLocation())
                .msgBody(wallPostData.getMsgBody())
                .founded(wallPostData.getFounded())
                .url(wallPostData.getUrl())
                .visibility(wallPostData.getVisibility())
                .build();

        updateCommonResponse(profileRes, wallPostData);

        final List<Link> links = new ArrayList<>();
        links.add(Link.of("link 422"));
        links.add(Link.of("link 423"));
        profileRes.setLinks(links);

        return profileRes;
    }

    public static WallPostData createWallPostData(WallPostReq wallPostReq, ProfileData postToProfileData) {
        WallPostData wallPostData = WallPostData.builder()
                .url(UniqueIdUtils.on().getNanoId())
                .location(wallPostReq.getLocation())
                .msgBody(wallPostReq.getMsgBody())
                .postToProfile(wallPostReq.getPostToProfile())
                .postFromProfile(wallPostReq.getPostFromProfile())
                .postAnonymously(wallPostReq.isPostAnonymously())
                .visibility(PostAuthority.POST_OWNER)
                .replyAuthority(PostAuthority.POST_OWNER)
                //.wallOwner(postToProfileData.getOwnerId())
                .wallPostOwner(postToProfileData.getOwnerId())//TODO: This should come from postFromProfile
                .build();

        createCommonFields(wallPostData);

        return wallPostData;
    }

    public static void updateWallPostData(WallPostData wallPostData, WallPostEditReq wallPostReq) {

        wallPostData.setMsgBody(wallPostReq.getMsgBody());
        wallPostData.setLocation(wallPostReq.getLocation());
        wallPostData.setVisibility(wallPostData.getVisibility());

        updateCommonFields(wallPostData);
    }

    public static List<WallPostRes> getWallPostListResponse(List<WallPostData> wallPostDataList) {
        List<WallPostRes> wallPostResArrayList = new ArrayList<>();

        for (WallPostData wallPostData : wallPostDataList) {
            wallPostResArrayList.add(getWallPostResponse(wallPostData));
        }

        return wallPostResArrayList;
    }

    public static void updateVisibilityWallPostData(WallPostData wallPostData, WallPostVisibilityUpdateReq wallPostVisibilityUpdateReq) {

        wallPostData.setVisibility(wallPostVisibilityUpdateReq.getVisibility());

        updateCommonFields(wallPostData);
    }

    public static boolean isVisibilityValid(byte visibility) {
        boolean isValid = false;

        for (OptionItem optionItem : PostAuthority.postAuthorityItems) {
            if (optionItem.getDetail().getValue() == visibility) {
                isValid = true;
                break;
            }
        }

        return isValid;
    }
}
