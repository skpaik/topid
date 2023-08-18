package io.goribco.apis.helper;

import io.goribco.apis.model.profile.ProfileData;
import io.goribco.apis.model.wallpost.WallPostData;
import io.goribco.apis.model.wallpostreply.WallPostReplyData;
import io.goribco.apis.model.wallpostreply.WallPostReplyEditReq;
import io.goribco.apis.model.wallpostreply.WallPostReplyReq;
import io.goribco.apis.model.wallpostreply.WallPostReplyRes;
import io.goribco.apis.utils.AuthUtil;
import io.goribco.apis.utils.UniqueIdUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

public class WallPostReplyHelper extends BaseDataHelper {
    public static WallPostReplyRes getWallPostReplyResponse(WallPostReplyData wallPostReplyData) {
        WallPostReplyRes profileRes = WallPostReplyRes.builder()
                .url(wallPostReplyData.getUrl())
                .location(wallPostReplyData.getLocation())
                .msgBody(wallPostReplyData.getMsgBody())
                .replyAnonymously(wallPostReplyData.isReplyAnonymously())
                //   .wallPostRes(wallPostReplyData.getWallPostRes())
                .build();


        updateCommonResponse(profileRes, wallPostReplyData);

        final List<Link> links = new ArrayList<>();
        links.add(Link.of("link 422"));
        links.add(Link.of("link 423"));
        profileRes.setLinks(links);

        return profileRes;
    }

    public static WallPostReplyData createWallPostReplyData(WallPostReplyReq wallPostReplyReq, WallPostData wallPostData, ProfileData wallPostReplyFromProfile) {
        WallPostReplyData wallPostReplyData = WallPostReplyData.builder()
                .url(getUniqueUrl(wallPostReplyReq))
                .location(wallPostReplyReq.getLocation())
                .msgBody(wallPostReplyReq.getMsgBody())
                .visibility(wallPostData.getReplyVisibility())
                .replyToWallPost(wallPostReplyReq.getReplyToWallPost())
                .replyFromProfile(wallPostReplyReq.getReplyFromProfile())
                .replyAnonymously(wallPostReplyReq.isReplyAnonymously())
                .wallPostOwner(AuthUtil.getCurrentUserName())//TODO: This one should be> WallPost.owner>Profile.url
                .wallPostReplyOwner(AuthUtil.getCurrentUserName())
                .build();

        createCommonFields(wallPostData);

        return wallPostReplyData;
    }

    @NotNull
    private static String getUniqueUrl(WallPostReplyReq wallPostReq) {
        return UniqueIdUtils.on().getNanoId(wallPostReq.getReplyToWallPost(), 3);
    }

    public static void updateWallPostReplyData(WallPostReplyData wallPostReplyData, WallPostReplyEditReq wallPostReplyReq) {
        wallPostReplyData.setMsgBody(wallPostReplyReq.getMsgBody());
        //wallPostReplyData.setUrl(wallPostReq.getUrl());
        //wallPostReplyData.setWebsite(wallPostReq.getWebsite());
        //wallPostReplyData.setLocation(wallPostReplyReq.getLocation());
        wallPostReplyData.setReplyAnonymously(wallPostReplyReq.isReplyAnonymously());

        updateCommonFields(wallPostReplyData);
    }
}
