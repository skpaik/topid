package io.goribco.apis.service;

import io.goribco.apis.model.exceptions.ModelDataNotFoundException;
import io.goribco.apis.model.exceptions.UrPathNotValidException;
import io.goribco.apis.model.profile.ProfileCreateReq;
import io.goribco.apis.model.profile.ProfileData;
import io.goribco.apis.model.profile.ProfileUpdateReq;
import io.goribco.core.request.QueryReq;

import java.util.List;

public interface ProfileService {
    ProfileData saveProfile(ProfileCreateReq profileCreateReq) throws UrPathNotValidException;

    ProfileData getProfileBy(String url) throws ModelDataNotFoundException;

    ProfileData updateProfile(String url, ProfileUpdateReq profileUpdateReq) throws ModelDataNotFoundException, UrPathNotValidException;

    List<ProfileData> myProfileList(QueryReq queryReq);

    List<ProfileData> profileList(QueryReq queryReq);

}
