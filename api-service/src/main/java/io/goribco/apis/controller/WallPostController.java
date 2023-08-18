package io.goribco.apis.controller;

import io.goribco.apis.configs.AppConstants;
import io.goribco.apis.configs.RoutesConfig;
import io.goribco.apis.configs.security.annotate.IsUser;
import io.goribco.apis.helper.WallPostHelper;
import io.goribco.apis.model.exceptions.ModelDataNotFoundException;
import io.goribco.apis.model.exceptions.ReqBodyNotValidException;
import io.goribco.apis.model.exceptions.UserUnauthorizedException;
import io.goribco.apis.model.exceptions.WallPostNotAllowedException;
import io.goribco.apis.model.wallpost.*;
import io.goribco.apis.service.FriendShipService;
import io.goribco.apis.service.WallPostService;
import io.goribco.core.request.QueryReq;
import io.goribco.core.response.AppHttpStatus;
import io.goribco.core.response.BaseMsg;
import io.goribco.core.response.ResHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = AppConstants.crossOriginUrl, allowedHeaders = "*")
@RequestMapping(RoutesConfig.WallPost.root)
public class WallPostController {
    @Autowired
    WallPostService wallPostService;
    @Autowired
    FriendShipService friendShipService;

    //@IsUser
    @PostMapping(value = RoutesConfig.WallPost.create)
    public EntityModel<?> createWallPost(@RequestBody @Valid WallPostReq wallPostReq)
            throws AccessDeniedException,
            DuplicateKeyException,
            ModelDataNotFoundException,
            WallPostNotAllowedException,
            UserUnauthorizedException,
            ReqBodyNotValidException {

        WallPostData wallPostData = wallPostService.saveWallPost(wallPostReq, friendShipService);
        WallPostRes wallPostRes = WallPostHelper.getWallPostResponse(wallPostData);

        return new ResHelper<WallPostRes, WallPostReq>().makeRestRes4(
                wallPostRes,
                wallPostReq,
                new BaseMsg("WallPost created successfully", AppHttpStatus.CREATED));
    }


    @IsUser
    @GetMapping(value = RoutesConfig.WallPost.edit)
    public EntityModel<?> editWallPost(@PathVariable String url) throws
            ModelDataNotFoundException,
            AccessDeniedException {

        WallPostReq wallPostReq = new WallPostReq();
        wallPostReq.setUrl(url);

        WallPostData wallPostData = wallPostService.getWallPostBy(url);
        WallPostRes wallPostRes = WallPostHelper.getWallPostResponse(wallPostData);

        wallPostRes.setForm_visibility_list(PostAuthority.postAuthorityItems);

        return new ResHelper<WallPostRes, WallPostReq>().makeRestRes4(
                wallPostRes,
                wallPostReq,
                new BaseMsg("WallPost edit request successfully", AppHttpStatus.OK));
    }

    @IsUser
    @PostMapping(value = RoutesConfig.WallPost.update)
    public EntityModel<?> updateWallPost(@PathVariable String url, @RequestBody @Valid WallPostEditReq wallPostEditReq) throws
            ModelDataNotFoundException,
            AccessDeniedException,
            DuplicateKeyException,
            ReqBodyNotValidException {

        WallPostData wallPostData = wallPostService.updateWallPost(url, wallPostEditReq);
        WallPostRes wallPostRes = WallPostHelper.getWallPostResponse(wallPostData);

        return new ResHelper<WallPostRes, WallPostEditReq>().makeRestRes4(
                wallPostRes,
                wallPostEditReq,
                new BaseMsg("WallPost update successfully", AppHttpStatus.Accepted));
    }

    @IsUser
    @PostMapping(value = RoutesConfig.WallPost.updateVisibility)
    public EntityModel<?> updateVisibilityWallPost(@PathVariable String url, @RequestBody @Valid WallPostVisibilityUpdateReq wallPostVisibilityUpdateReq) throws
            ModelDataNotFoundException,
            AccessDeniedException,
            DuplicateKeyException,
            ReqBodyNotValidException {

        WallPostData wallPostData = wallPostService.updateVisibilityWallPost(url, wallPostVisibilityUpdateReq);
        WallPostRes wallPostRes = WallPostHelper.getWallPostResponse(wallPostData);

        return new ResHelper<WallPostRes, WallPostVisibilityUpdateReq>().makeRestRes4(
                wallPostRes,
                wallPostVisibilityUpdateReq,
                new BaseMsg("WallPost visibility update successfully", AppHttpStatus.Accepted));
    }

    @GetMapping(value = {RoutesConfig.WallPost.listByProfileOwner})
    public EntityModel<?> wallPostListByProfile(
            @PathVariable(required = false) String profileUrl,
            @RequestParam(name = "owner", required = false) String owner,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "8", required = false) int limit) {

        QueryReq queryReq = QueryReq.builder()
                .profileUrl(profileUrl)
                .postOwnerProfileUrl(owner)
                .pageNum(page)
                .pageSize(limit)
                .build();

        List<WallPostData> wallPostDataList = wallPostService.wallPostListByProfile(queryReq);
        List<WallPostRes> wallPostResList = WallPostHelper.getWallPostListResponse(wallPostDataList);

        queryReq.setResSize(wallPostDataList.size());

        return new ResHelper<WallPostRes, WallPostReq>().makeRestRes5(
                wallPostResList,
                queryReq,
                new BaseMsg("WallPost list by Profile successfully", AppHttpStatus.OK));
    }

    @GetMapping(value = RoutesConfig.WallPost.detail)
    public EntityModel<?> detailWallPost(@PathVariable String url) throws ModelDataNotFoundException {

        WallPostReq wallPostReq = new WallPostReq();
        wallPostReq.setUrl(url);

        WallPostData wallPostData = wallPostService.getWallPostBy(url);
        WallPostRes wallPostRes = WallPostHelper.getWallPostResponse(wallPostData);

        return new ResHelper<WallPostRes, WallPostReq>().makeRestRes4(
                wallPostRes,
                wallPostReq,
                new BaseMsg("WallPost details", AppHttpStatus.CONFLICT));
    }
}