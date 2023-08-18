package io.goribco.apis.controller;

import io.goribco.apis.configs.AppConstants;
import io.goribco.apis.configs.RoutesConfig;
import io.goribco.apis.configs.security.annotate.IsUser;
import io.goribco.apis.helper.ProfileDataHelper;
import io.goribco.apis.helper.WallPostHelper;
import io.goribco.apis.model.exceptions.ModelDataNotFoundException;
import io.goribco.apis.model.exceptions.UrPathNotValidException;
import io.goribco.apis.model.profile.ProfileCreateReq;
import io.goribco.apis.model.profile.ProfileData;
import io.goribco.apis.model.profile.ProfileRes;
import io.goribco.apis.model.profile.ProfileUpdateReq;
import io.goribco.apis.model.wallpost.WallPostData;
import io.goribco.apis.model.wallpost.WallPostRes;
import io.goribco.apis.service.FriendShipService;
import io.goribco.apis.service.ProfileService;
import io.goribco.apis.service.WallPostService;
import io.goribco.core.request.QueryReq;
import io.goribco.core.response.AppHttpStatus;
import io.goribco.core.response.BaseMsg;
import io.goribco.core.response.PaginationConst;
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
@RequestMapping(RoutesConfig.Profile.root)
public class ProfileController {
    @Autowired
    ProfileService profileService;
    @Autowired
    WallPostService wallPostService;
    @Autowired
    FriendShipService friendShipService;

    //    @QueryMapping
    //@CrossOrigin(origins = "http://localhost:3000")
//    @IsUser
//    public ProfileData orgById(@Argument String id) {
//        return orgRepository.findById(id).isPresent().get();
//    }
    //@PreAuthorize("hasAnyAuthority('VIEWER', 'ORG_ADMIN', 'USER')")
   /*
    @IsUser
    @PostMapping(value = RoutesConfig.Profile.create)
    //@QueryMapping
    public ResponseEntity<?> createProfile(@RequestBody @Valid ProfileCreateReq profileCreateReq) throws Exception {
        try {
            ProfileData profileData = orgService.saveProfile(profileCreateReq);
            ProfileRes profileRes = ProfileDataHelper.getProfileResponse(profileData);

            ResponseEntity<?> restRes = new ResHelper<ProfileRes, ProfileCreateReq>().makeRestRes2(profileRes, profileCreateReq, null);

            return restRes;

        } catch (AccessDeniedException | UrPathNotValidException exception) {
            throw exception;
        } catch (DuplicateKeyException exception) {
            throw new ModelDataExistsException(exception.getMessage());
        } catch (Exception exception) {
            throw new Exception("Something went wrong!");
        }
    }
    */

    @IsUser
    @PostMapping(value = RoutesConfig.Profile.create)
    public EntityModel<?> createProfile(@RequestBody @Valid ProfileCreateReq profileCreateReq)
            throws UrPathNotValidException,
            AccessDeniedException,
            DuplicateKeyException {

        ProfileData profileData = profileService.saveProfile(profileCreateReq);
        ProfileRes profileRes = ProfileDataHelper.getProfileResponse(profileData);

        return new ResHelper<ProfileRes, ProfileCreateReq>().makeRestRes4(
                profileRes,
                profileCreateReq,
                new BaseMsg("Profile created successfully", AppHttpStatus.CREATED));
    }

    @IsUser
    @GetMapping(value = RoutesConfig.Profile.edit)
    public EntityModel<?> editProfile(@PathVariable String url) throws
            ModelDataNotFoundException,
            AccessDeniedException {

        ProfileUpdateReq profileUpdateReq = new ProfileUpdateReq();
        profileUpdateReq.setUrl(url);

        ProfileData profileData = profileService.getProfileBy(url);
        ProfileRes profileRes = ProfileDataHelper.getProfileResponse(profileData);

        return new ResHelper<ProfileRes, ProfileUpdateReq>().makeRestRes4(
                profileRes,
                profileUpdateReq,
                new BaseMsg("Profile edit request successfully", AppHttpStatus.OK));
    }

    @IsUser
    @PostMapping(value = RoutesConfig.Profile.update)
    public EntityModel<?> updateProfile(@PathVariable String url, @RequestBody @Valid ProfileUpdateReq profileUpdateReq) throws
            ModelDataNotFoundException,
            AccessDeniedException,
            UrPathNotValidException,
            DuplicateKeyException {

        ProfileData profileData = profileService.updateProfile(url, profileUpdateReq);
        ProfileRes profileRes = ProfileDataHelper.getProfileResponse(profileData);

        return new ResHelper<ProfileRes, ProfileUpdateReq>().makeRestRes4(
                profileRes,
                profileUpdateReq,
                new BaseMsg("Profile update successfully", AppHttpStatus.Accepted));
    }

    /*
    @GetMapping(value = RoutesConfig.Profile.list)
    public EntityModel<?> profileList(
            @RequestParam(name = "name", defaultValue = "", required = false) String name,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "24", required = false) int limit) {

        BaseQueryParams queryReq = BaseQueryParams.builder()
                .name(name)
                .pageNum(page)
                .pageSize(limit)
                .build();

        List<ProfileData> profileDataList = orgService.myProfileList(queryReq);
        List<ProfileRes> profileResList = ProfileDataHelper.getProfileListResponse(profileDataList);

        queryReq.setResSize(profileDataList.size());

        return new ResHelper<ProfileRes, ProfileCreateReq>().makeRestRes5(
                profileResList,
                queryReq,
                new BaseMsg("Profile list successfully", AppHttpStatus.OK));
    }
    */

    @GetMapping(value = {RoutesConfig.Profile.list, RoutesConfig.Profile.listByOwner})
    public EntityModel<?> profileList(
            @PathVariable(required = false) String ownerId,
            @RequestParam(name = "name", defaultValue = "", required = false) String name,
            @RequestParam(name = "page", defaultValue = PaginationConst.pageNum + "", required = false) int page,
            @RequestParam(name = "limit", defaultValue = PaginationConst.pageSize + "", required = false) int limit) {

        QueryReq queryReq = QueryReq.builder()
                .ownerId(ownerId)
                .name(name)
                .pageNum(page)
                .pageSize(limit)
                .build();

        List<ProfileData> profileDataList = profileService.profileList(queryReq);
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

        QueryReq queryReq = QueryReq.builder()
                .name(name)
                .pageNum(page)
                .pageSize(limit)
                .build();

        List<ProfileData> profileDataList = profileService.myProfileList(queryReq);
        List<ProfileRes> profileResList = ProfileDataHelper.getProfileListResponse(profileDataList);

        queryReq.setResSize(profileDataList.size());

        return new ResHelper<ProfileRes, ProfileCreateReq>().makeRestRes5(
                profileResList,
                queryReq,
                new BaseMsg("My Profile list successfully", AppHttpStatus.OK));
    }

    @GetMapping(value = RoutesConfig.Profile.detail)
    public EntityModel<?> detailProfile(@PathVariable String url) throws ModelDataNotFoundException {

        ProfileCreateReq profileCreateReq = new ProfileCreateReq();
        profileCreateReq.setUrl(url);

        ProfileData profileData = profileService.getProfileBy(url);
        ProfileRes profileRes = ProfileDataHelper.getProfileResponse(profileData);

        profileRes.set_res_list_wall_post(get_wall_post_data_list(url));

        long totalFollowers = friendShipService.countFollowers(url);
        profileRes.setTotalFollowers(totalFollowers);

        return new ResHelper<ProfileRes, ProfileCreateReq>().makeRestRes4(
                profileRes,
                profileCreateReq,
                new BaseMsg("Profile details", AppHttpStatus.OK));
    }

    private List<WallPostRes> get_wall_post_data_list(String url) {
        QueryReq queryReq = QueryReq.builder()
                .profileUrl(url)
                .pageNum(PaginationConst.pageNum)
                .pageSize(PaginationConst.pageSize)
                .build();

        List<WallPostData> wallPostDataList = wallPostService.wallPostListByProfile(queryReq);

        return WallPostHelper.getWallPostListResponse(wallPostDataList);
    }

    /*
    @GetMapping(value = RoutesConfig.Profile.listByUser)
    //@QueryMapping
    public CollectionModel<?> profileListByUser(@PathVariable String ownerId) throws AccessDeniedException {
        BaseQueryParams queryReq = BaseQueryParams.builder()
                .ownerId(ownerId)
                .build();

        List<ProfileData> profileDataList = orgService.profileListByUser(ownerId);
        List<ProfileRes> profileResList = ProfileDataHelper.getProfileListResponse(profileDataList);

        queryReq.setResSize(profileDataList.size());

        List<EntityModel<ProfileRes>> profileList = profileResList.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        // ResponseEntity<?> restRes = new ResHelper<ProfileRes, ProfileCreateReq>().makeRestRes3(profileResList, queryReq, null);

        return CollectionModel.of(profileList,
                linkTo(methodOn(ProfileController.class).profileListByUser(ownerId)).withSelfRel());
    }
    */
}