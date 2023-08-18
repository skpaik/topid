package io.goribco.apis.helper;

import io.goribco.apis.utils.AuthUtil;
import io.goribco.apis.utils.DateTimeUtil;
import io.goribco.core.model.BaseModel;

public class BaseDataHelper {

    protected static void createCommonFields(BaseModel baseModel) {
        long currentDateTime = DateTimeUtil.getMilliSec();

        baseModel.setCreatedAtMoment(currentDateTime);
        //  baseModel.setUpdatedAtMoment(currentDateTime);
        //    baseModel.setPublishedAtMoment(currentDateTime);

        String currentUserName = AuthUtil.getCurrentUserName();

        baseModel.setOwnerId(currentUserName);
        baseModel.setCreatedById(currentUserName);
        //   baseModel.setUpdatedById(currentUserName);
        //  baseModel.setPublishedById(currentUserName);

        baseModel.setEnabled(true);
    }

    protected static void updateCommonFields(BaseModel baseModel) {
        baseModel.setUpdatedAtMoment(DateTimeUtil.getMilliSec());

        baseModel.setUpdatedById(AuthUtil.getCurrentUserName());
    }

    protected static void updateCommonResponse(BaseModel profileRes, BaseModel profileData) {
        profileRes.setCreatedAtMoment(profileData.getCreatedAtMoment());
        profileRes.setUpdatedAtMoment(profileData.getUpdatedAtMoment());
        profileRes.setPublishedAtMoment(profileData.getPublishedAtMoment());


        //profileRes.setOwnerId(profileData.getOwnerId());

        profileRes.setCreatedById(profileData.getCreatedById());
        profileRes.setUpdatedById(profileData.getUpdatedById());
        profileRes.setPublishedById(profileData.getPublishedById());


        profileRes.setCreatedAt(profileData.getCreatedAt());
        profileRes.setCreatedAtStr(profileData.getCreatedAtStr());
        profileRes.setUpdatedAt(profileData.getUpdatedAt());
        profileRes.setUpdatedAtStr(profileData.getUpdatedAtStr());
        profileRes.setPublishedAt(profileData.getPublishedAt());
        profileRes.setPublishedAtStr(profileData.getPublishedAtStr());

        // profileRes.setEnabled(true);
    }
}
