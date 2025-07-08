package io.goribco.apis.service;

import io.goribco.apis.model.exceptions.ModelDataNotFoundException;
import io.goribco.apis.model.exceptions.ReqBodyNotValidException;
import io.goribco.apis.model.exceptions.UserUnauthorizedException;
import io.goribco.apis.model.exceptions.WallPostNotAllowedException;
import io.goribco.apis.model.wallpost.WallPostData;
import io.goribco.apis.model.wallpost.WallPostEditReq;
import io.goribco.apis.model.wallpost.WallPostReq;
import io.goribco.apis.model.wallpost.WallPostVisibilityUpdateReq;
import io.goribco.core.request.QueryReq;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

public interface WallPostService {
    WallPostData saveWallPost(WallPostReq wallPostReq, FriendShipService friendShipService) throws
            AccessDeniedException,
            ModelDataNotFoundException,
            WallPostNotAllowedException,
            UserUnauthorizedException,
            ReqBodyNotValidException;

    WallPostData getWallPostBy(String url) throws ModelDataNotFoundException;

    WallPostData updateWallPost(String url, WallPostEditReq wallPostReq) throws
            ModelDataNotFoundException,
            ReqBodyNotValidException;

    WallPostData updateVisibilityWallPost(String url, WallPostVisibilityUpdateReq wallPostVisibilityUpdateReq) throws
            ModelDataNotFoundException,
            ReqBodyNotValidException;

    List<WallPostData> wallPostListByProfile(QueryReq queryReq);

    //    ProfileData getProfileBy(String url) throws ModelDataNotFoundException;
//
//    ProfileData updateProfile(String url, ProfileCreateReq profileCreateReq) throws ModelDataNotFoundException, UrPathNotValidException;
//
//    List<ProfileData> myProfileList(BaseQueryParams queryReq);
//
//    List<ProfileData> profileList(BaseQueryParams queryReq);

}