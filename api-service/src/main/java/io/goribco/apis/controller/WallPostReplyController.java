package io.goribco.apis.controller;

import io.goribco.apis.configs.AppConstants;
import io.goribco.apis.configs.RoutesConfig;
import io.goribco.apis.configs.security.annotate.IsUser;
import io.goribco.apis.helper.WallPostHelper;
import io.goribco.apis.helper.WallPostReplyHelper;
import io.goribco.apis.model.exceptions.ModelDataNotFoundException;
import io.goribco.apis.model.exceptions.UserUnauthorizedException;
import io.goribco.apis.model.exceptions.WallPostReplyNotAllowedException;
import io.goribco.apis.model.wallpost.WallPostData;
import io.goribco.apis.model.wallpostreply.WallPostReplyData;
import io.goribco.apis.model.wallpostreply.WallPostReplyEditReq;
import io.goribco.apis.model.wallpostreply.WallPostReplyReq;
import io.goribco.apis.model.wallpostreply.WallPostReplyRes;
import io.goribco.apis.service.FriendShipService;
import io.goribco.apis.service.WallPostReplyService;
import io.goribco.apis.service.WallPostService;
import io.goribco.core.response.AppHttpStatus;
import io.goribco.core.response.BaseMsg;
import io.goribco.core.response.ResHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = AppConstants.crossOriginUrl, allowedHeaders = "*")
@RequestMapping(RoutesConfig.WallPostReply.root)
public class WallPostReplyController {
    @Autowired
    WallPostReplyService wallPostReplyService;
    @Autowired
    WallPostService wallPostService;
    @Autowired
    FriendShipService friendShipService;

    @IsUser
    @PostMapping(value = RoutesConfig.WallPostReply.create)
    public EntityModel<?> createWallPostReply(@RequestBody @Valid WallPostReplyReq wallPostReq)
            throws AccessDeniedException,
            DuplicateKeyException,
            ModelDataNotFoundException,
            WallPostReplyNotAllowedException,
            UserUnauthorizedException {

        WallPostReplyData wallPostReplyData = wallPostReplyService.saveWallPostReply(wallPostReq, friendShipService);
        WallPostReplyRes wallPostReplyRes = WallPostReplyHelper.getWallPostReplyResponse(wallPostReplyData);

        return new ResHelper<WallPostReplyRes, WallPostReplyReq>().makeRestRes4(
                wallPostReplyRes,
                wallPostReq,
                new BaseMsg("WallPostReply created successfully", AppHttpStatus.CREATED));
    }

    /*
    @IsUser
    @GetMapping(value = RoutesConfig.WallPostReply.edit)
    public EntityModel<?> editProfile(@PathVariable String url) throws
            ModelDataNotFoundException,
            AccessDeniedException {

        ProfileCreateReq profileCreateReq = new ProfileCreateReq();
        profileCreateReq.setUrl(url);

        ProfileData profileData = wallPostService.getProfileBy(url);
        ProfileRes profileRes = ProfileDataHelper.getProfileResponse(profileData);

        return new ResHelper<ProfileRes, ProfileCreateReq>().makeRestRes4(
                profileRes,
                profileCreateReq,
                new BaseMsg("Profile edit request successfully", AppHttpStatus.OK));
    }
*/
    @IsUser
    @PostMapping(value = RoutesConfig.WallPostReply.update)
    public EntityModel<?> updateWallPostReply(@PathVariable String url, @RequestBody @Valid WallPostReplyEditReq wallPostReplyReq) throws
            ModelDataNotFoundException,
            AccessDeniedException,
            DuplicateKeyException {
        WallPostReplyData wallPostReplyData = wallPostReplyService.updateWallPostReply(url, wallPostReplyReq);
        WallPostReplyRes wallPostReplyRes = WallPostReplyHelper.getWallPostReplyResponse(wallPostReplyData);

        return new ResHelper<WallPostReplyRes, WallPostReplyEditReq>().makeRestRes4(
                wallPostReplyRes,
                wallPostReplyReq,
                new BaseMsg("WallPostReply edit successfully", AppHttpStatus.Accepted));
    }

    /*
    @GetMapping(value = {RoutesConfig.Profile.list, RoutesConfig.Profile.listByOwner})
    public EntityModel<?> profileList(
            @PathVariable(required = false) String ownerId,
            @RequestParam(name = "name", defaultValue = "", required = false) String name,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "12", required = false) int limit) {

        BaseQueryParams queryReq = BaseQueryParams.builder()
                .ownerId(ownerId)
                .name(name)
                .pageNum(page)
                .pageSize(limit)
                .build();

        List<ProfileData> profileDataList = wallPostService.profileList(queryReq);
        List<ProfileRes> profileResList = ProfileDataHelper.getProfileListResponse(profileDataList);

        queryReq.setResSize(profileDataList.size());

        return new ResHelper<ProfileRes, ProfileCreateReq>().makeRestRes5(
                profileResList,
                queryReq,
                new BaseMsg("Profile list successfully", AppHttpStatus.OK));
    }

    @IsUser
    @GetMapping(value = RoutesConfig.Profile.myList)
    public EntityModel<?> myProfileList(
            @RequestParam(name = "name", defaultValue = "", required = false) String name,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "24", required = false) int limit) throws AccessDeniedException {

        BaseQueryParams queryReq = BaseQueryParams.builder()
                .name(name)
                .pageNum(page)
                .pageSize(limit)
                .build();

        List<ProfileData> profileDataList = wallPostService.myProfileList(queryReq);
        List<ProfileRes> profileResList = ProfileDataHelper.getProfileListResponse(profileDataList);

        queryReq.setResSize(profileDataList.size());

        return new ResHelper<ProfileRes, ProfileCreateReq>().makeRestRes5(
                profileResList,
                queryReq,
                new BaseMsg("My Profile list successfully", AppHttpStatus.OK));
    }

    */
    @IsUser
    @GetMapping(value = RoutesConfig.WallPostReply.detail)
    public EntityModel<?> detailWallPostReply(@PathVariable String url) throws ModelDataNotFoundException {

        WallPostReplyReq wallPostReplyReq = new WallPostReplyReq();
        wallPostReplyReq.setUrl(url);

        WallPostReplyData wallPostReplyData = wallPostReplyService.getWallPostReplyBy(wallPostReplyReq.getUrl());
        WallPostReplyRes wallPostReplyRes = WallPostReplyHelper.getWallPostReplyResponse(wallPostReplyData);

        WallPostData wallPostData = wallPostService.getWallPostBy(wallPostReplyData.getReplyToWallPost());
        if (wallPostData != null) {
            wallPostReplyRes.set_res_data_wall_post(WallPostHelper.getWallPostResponse(wallPostData));
        }

        return new ResHelper<WallPostReplyRes, WallPostReplyReq>().makeRestRes4(
                wallPostReplyRes,
                wallPostReplyReq,
                new BaseMsg("WallPostReply details", AppHttpStatus.CONFLICT));
    }
}