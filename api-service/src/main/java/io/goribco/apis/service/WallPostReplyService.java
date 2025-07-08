package io.goribco.apis.service;

import io.goribco.apis.model.exceptions.ModelDataNotFoundException;
import io.goribco.apis.model.exceptions.UserUnauthorizedException;
import io.goribco.apis.model.exceptions.WallPostReplyNotAllowedException;
import io.goribco.apis.model.wallpostreply.WallPostReplyData;
import io.goribco.apis.model.wallpostreply.WallPostReplyEditReq;
import io.goribco.apis.model.wallpostreply.WallPostReplyReq;
import org.springframework.security.access.AccessDeniedException;

public interface WallPostReplyService {
    WallPostReplyData saveWallPostReply(WallPostReplyReq wallPostReq, FriendShipService friendShipService) throws AccessDeniedException, ModelDataNotFoundException, WallPostReplyNotAllowedException, UserUnauthorizedException;

    WallPostReplyData getWallPostReplyBy(String url) throws ModelDataNotFoundException;

    WallPostReplyData updateWallPostReply(String url, WallPostReplyEditReq wallPostReplyReq) throws ModelDataNotFoundException;

    //    ProfileData getProfileBy(String url) throws ModelDataNotFoundException;
//
//    ProfileData updateProfile(String url, ProfileCreateReq profileCreateReq) throws ModelDataNotFoundException, UrPathNotValidException;
//
//    List<ProfileData> myProfileList(BaseQueryParams queryReq);
//
//    List<ProfileData> profileList(BaseQueryParams queryReq);

}