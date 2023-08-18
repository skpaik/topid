package io.goribco.apis.service.impl;

import io.goribco.apis.helper.WallPostHelper;
import io.goribco.apis.model.exceptions.ModelDataNotFoundException;
import io.goribco.apis.model.exceptions.ReqBodyNotValidException;
import io.goribco.apis.model.exceptions.UserUnauthorizedException;
import io.goribco.apis.model.exceptions.WallPostNotAllowedException;
import io.goribco.apis.model.friendship.FriendShipData;
import io.goribco.apis.model.profile.ProfileData;
import io.goribco.apis.model.wallpost.*;
import io.goribco.apis.repository.ProfileDataRepository;
import io.goribco.apis.repository.WallPostRepository;
import io.goribco.apis.service.FriendShipService;
import io.goribco.apis.service.WallPostService;
import io.goribco.apis.utils.AuthUtil;
import io.goribco.core.request.QueryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WallPostServiceImpl implements WallPostService {
    @Autowired
    private WallPostRepository wallPostRepository;
    @Autowired
    private ProfileDataRepository profileDataRepository;

    @Override
    public WallPostData saveWallPost(WallPostReq wallPostReq, FriendShipService friendShipService) throws
            AccessDeniedException,
            ModelDataNotFoundException,
            WallPostNotAllowedException,
            UserUnauthorizedException,
            ReqBodyNotValidException {

        ProfileData postFromProfileData = profileDataRepository.findByUrl(wallPostReq.getPostFromProfile())
                .orElseThrow(() -> new ModelDataNotFoundException("Your profile (" + wallPostReq.getPostFromProfile() + ") is not valid"));

        ProfileData postToProfileData = profileDataRepository.findByUrl(wallPostReq.getPostToProfile())
                .orElseThrow(() -> new ModelDataNotFoundException("Your target profile (" + wallPostReq.getPostToProfile() + ") is not found"));

        validateWallPostToProfile(wallPostReq, postFromProfileData, postToProfileData, friendShipService);

        WallPostData profileData = WallPostHelper.createWallPostData(wallPostReq, postToProfileData);

        return wallPostRepository.save(profileData);
    }

    @Override
    public WallPostData getWallPostBy(String url) throws ModelDataNotFoundException {
        return wallPostRepository.findByUrl(url)
                .orElseThrow(() -> new ModelDataNotFoundException("WallPost not found for url=" + url));
    }

    @Override
    public WallPostData updateWallPost(String url, WallPostEditReq wallPostReq) throws ModelDataNotFoundException, ReqBodyNotValidException {

        WallPostData wallPostData = getWallPostBy(url);

        validateWallPostEdit(wallPostReq.getVisibility(), wallPostData);

        WallPostHelper.updateWallPostData(wallPostData, wallPostReq);

        wallPostData = wallPostRepository.save(wallPostData);

        return wallPostData;
    }

    @Override
    public WallPostData updateVisibilityWallPost(String url, WallPostVisibilityUpdateReq wallPostVisibilityUpdateReq) throws ModelDataNotFoundException, ReqBodyNotValidException {
        WallPostData wallPostData = getWallPostBy(url);

        validateWallPostEdit(wallPostVisibilityUpdateReq.getVisibility(), wallPostData);

        WallPostHelper.updateVisibilityWallPostData(wallPostData, wallPostVisibilityUpdateReq);

        wallPostData = wallPostRepository.save(wallPostData);

        return wallPostData;
    }

    @Override
    public List<WallPostData> wallPostListByProfile(QueryReq queryReq) {
        Page<WallPostData> wallPostDataPage = null;

        Pageable pageable = PageRequest.of(queryReq.getPageNum(), queryReq.getPageSize());

        if (queryReq.getPostOwnerProfileUrl() != null) {
            wallPostDataPage = wallPostRepository.findByPostToProfileAndPostFromProfile(
                    queryReq.getProfileUrl(),
                    queryReq.getPostOwnerProfileUrl(),
                    pageable);
        } else {
            wallPostDataPage = wallPostRepository.findByPostToProfile(queryReq.getProfileUrl(), pageable);
        }

        return wallPostDataPage.getContent();
    }


    private void validateWallPostEdit(byte visibility, WallPostData wallPostBy) throws AccessDeniedException, ReqBodyNotValidException {
        isVisibilityValid(visibility);

        if (!wallPostBy.getOwnerId().equals(AuthUtil.getCurrentUserName())) {
            throw new AccessDeniedException("You are not authorized to edit this wallPost");
        }
    }

    private void isVisibilityValid(byte visibility) throws ReqBodyNotValidException {
        if (!WallPostHelper.isVisibilityValid(visibility)) {
            throw new ReqBodyNotValidException("Please select a valid visibility mode");
        }
    }

    private void validateWallPostToProfile(WallPostReq wallPostReq,
                                           ProfileData postFromProfileData,
                                           ProfileData postToProfileData,
                                           FriendShipService friendShipService)
            throws WallPostNotAllowedException,
            UserUnauthorizedException,
            ModelDataNotFoundException,
            ReqBodyNotValidException {
        isVisibilityValid(wallPostReq.getVisibility());

        int profileVisibility = postToProfileData.getProfileVisibility();
        int wallPostAuthority = postToProfileData.getWallPostAuthority();
        int wallPostVisibility = postToProfileData.getWallPostVisibility();

        if (profileVisibility == PostAuthority.NO_ONE
                || wallPostAuthority == PostAuthority.NO_ONE
                || wallPostVisibility == PostAuthority.NO_ONE) {
            throw new WallPostNotAllowedException("You cannot post to this profile");
        }

        boolean anonymousLoggedIn = false;
        boolean anonymousFriend = false;
        boolean anonymousFollowing = false;
        boolean anonymousFollowers = false;

        if (wallPostReq.isPostAnonymously()) {
            if (postToProfileData.isAllowAnonymousWallPost()) {
                int allowAnonymousWallPostFrom = postToProfileData.getAllowAnonymousWallPostFrom();

                if (allowAnonymousWallPostFrom == PostAuthority.LOGGED_IN) {
                    anonymousLoggedIn = true;
                }
                if (allowAnonymousWallPostFrom == PostAuthority.FRIEND) {
                    anonymousFriend = true;
                }
                if (allowAnonymousWallPostFrom == PostAuthority.FOLLOWING) {
                    anonymousFollowing = true;
                }
                if (allowAnonymousWallPostFrom == PostAuthority.FOLLOWERS) {
                    anonymousFollowers = true;
                }
            } else {
                throw new WallPostNotAllowedException("You cannot post anonymously to this profile");
            }
        }


        if (profileVisibility == PostAuthority.ANYONE
                && wallPostAuthority == PostAuthority.ANYONE
                && wallPostVisibility == PostAuthority.ANYONE) {

            return;
        }


        String currentUserName = AuthUtil.getCurrentUserName();//anonymousUser

        if ((profileVisibility == PostAuthority.LOGGED_IN
                || wallPostAuthority == PostAuthority.LOGGED_IN
                || wallPostVisibility == PostAuthority.LOGGED_IN
                || anonymousLoggedIn
        ) || (wallPostAuthority == PostAuthority.FRIEND
                || wallPostAuthority == PostAuthority.FOLLOWING
                || wallPostAuthority == PostAuthority.FOLLOWERS)) {

            if (currentUserName.equals("anonymousUser")) {
                throw new UserUnauthorizedException("anonymousUser cannot post to this profile");
            }
        }

        if (wallPostAuthority == PostAuthority.LOGGED_IN
                || wallPostAuthority == PostAuthority.FRIEND
                || wallPostAuthority == PostAuthority.FOLLOWING
                || wallPostAuthority == PostAuthority.FOLLOWERS) {

            if (!postFromProfileData.getOwnerId().equals(currentUserName)) {
                throw new WallPostNotAllowedException("You cannot post from this profile (" + wallPostReq.getPostFromProfile() + ")");
            }
        }

        FriendShipData friendShipData = friendShipService.findByFromAndToProfile(wallPostReq.getPostFromProfile(), postToProfileData.getUrl()).orElse(null);

        if (wallPostAuthority == PostAuthority.FRIEND || anonymousFriend) {

            if (friendShipData == null || !friendShipData.isFriend()) {
                throw new WallPostNotAllowedException("Only friends can post to this profile (" + wallPostReq.getPostToProfile() + ")");
            }
        }

        if (wallPostAuthority == PostAuthority.FOLLOWING || anonymousFollowing) {

            if (friendShipData == null || !friendShipData.isFollowing()) {
                throw new WallPostNotAllowedException("Only following profile can post to this profile (" + wallPostReq.getPostToProfile() + ")");
            }
        }

        if (wallPostAuthority == PostAuthority.FOLLOWERS || anonymousFollowers) {
            friendShipService.findFollower(wallPostReq.getPostFromProfile(), postToProfileData.getUrl(), true)
                    .orElseThrow(() -> new WallPostNotAllowedException("Only followers can post to this profile (" + wallPostReq.getPostToProfile() + ")"));
        }
    }
}
