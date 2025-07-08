package io.goribco.apis.service.impl;

import io.goribco.apis.helper.ProfileDataHelper;
import io.goribco.apis.model.exceptions.ModelDataNotFoundException;
import io.goribco.apis.model.exceptions.UrPathNotValidException;
import io.goribco.apis.model.profile.ProfileCreateReq;
import io.goribco.apis.model.profile.ProfileData;
import io.goribco.apis.model.profile.ProfileUpdateReq;
import io.goribco.apis.repository.ProfileDataRepository;
import io.goribco.apis.service.ProfileService;
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
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDataRepository profileDataRepository;

    @Override
    public ProfileData saveProfile(ProfileCreateReq profileCreateReq) throws UrPathNotValidException {

        //RegexUtil.validateUrlName(profileCreateReq.getUrl(), "Profile");

        ProfileData profileData = ProfileDataHelper.createProfileData(profileCreateReq);

        //   ProfileData orgDataSaved = orgRepository.save(profileData);

        return profileDataRepository.save(profileData);
    }

    @Override
    public ProfileData getProfileBy(String url) throws ModelDataNotFoundException {
        ProfileData profileData = profileDataRepository.findByUrl(url) //
                .orElseThrow(() -> new ModelDataNotFoundException("Profile not found for url=" + url));

        return profileData;
    }

    @Override
    public ProfileData updateProfile(String url, ProfileUpdateReq profileUpdateReq) throws AccessDeniedException, ModelDataNotFoundException, UrPathNotValidException {
        ProfileData profileData = getProfileBy(url);

        String currentUserName = AuthUtil.getCurrentUserName();

        if (!profileData.getOwnerId().equals(currentUserName)) {
            throw new AccessDeniedException("You are not authorized to edit this organization");
        }

        // RegexUtil.validateUrlName(profileCreateReq.getUrl(), "Profile");

        ProfileDataHelper.updateProfileData(profileData, profileUpdateReq);

        profileData = profileDataRepository.save(profileData);

        return profileData;
    }

    @Override
    public List<ProfileData> myProfileList(QueryReq queryReq) {
        /*
        Pageable pageable = PageRequest.of(queryReq.getPageNum(), queryReq.getPageSize(),
                Sort.by("publishedAtMoment").ascending()
                        .and(Sort.by("updatedAtMoment")).ascending()
                        .and(Sort.by("createdAtMoment")).ascending());
        */

        Pageable pageable = PageRequest.of(queryReq.getPageNum(), queryReq.getPageSize());

        return profileDataRepository.findByOwnerIdOrderByUpdatedAtMomentAsc(AuthUtil.getCurrentUserName(), pageable).getContent();
    }

    @Override
    public List<ProfileData> profileList(QueryReq queryReq) {
        Page<ProfileData> profileDataList = null;

        Pageable pageable = PageRequest.of(queryReq.getPageNum(), queryReq.getPageSize());

        if (queryReq.getOwnerId() != null) {
            profileDataList = profileDataRepository.findByOwnerIdOrderByNameAscEnabled(queryReq.getOwnerId(), pageable);
        } else {
            profileDataList = profileDataRepository.findAll(pageable);
        }

        return profileDataList.getContent();
    }
}
