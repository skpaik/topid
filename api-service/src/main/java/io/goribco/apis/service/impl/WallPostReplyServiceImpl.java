package io.goribco.apis.service.impl;

import io.goribco.apis.helper.WallPostReplyHelper;
import io.goribco.apis.model.exceptions.ModelDataNotFoundException;
import io.goribco.apis.model.profile.ProfileData;
import io.goribco.apis.model.wallpost.WallPostData;
import io.goribco.apis.model.wallpostreply.WallPostReplyData;
import io.goribco.apis.model.wallpostreply.WallPostReplyEditReq;
import io.goribco.apis.model.wallpostreply.WallPostReplyReq;
import io.goribco.apis.repository.ProfileDataRepository;
import io.goribco.apis.repository.WallPostReplyRepository;
import io.goribco.apis.repository.WallPostRepository;
import io.goribco.apis.service.FriendShipService;
import io.goribco.apis.service.WallPostReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class WallPostReplyServiceImpl implements WallPostReplyService {
    @Autowired
    private WallPostReplyRepository wallPostReplyRepository;
    @Autowired
    private WallPostRepository wallPostRepository;
    @Autowired
    private ProfileDataRepository profileDataRepository;

    @Override
    public WallPostReplyData saveWallPostReply(WallPostReplyReq wallPostReplyReq, FriendShipService friendShipService) throws
            AccessDeniedException,
            ModelDataNotFoundException {

        ProfileData wallPostReplyFromProfile = profileDataRepository.findByUrl(wallPostReplyReq.getReplyFromProfile())
                .orElseThrow(() -> new ModelDataNotFoundException("Your profile (" + wallPostReplyReq.getReplyFromProfile() + ") not found"));

        WallPostData toWallPostData = wallPostRepository.findByUrl(wallPostReplyReq.getReplyToWallPost())
                .orElseThrow(() -> new ModelDataNotFoundException("This wall post (" + wallPostReplyReq.getReplyFromProfile() + ") is not available"));


        //validateReplyToWallPost(wallPostReplyReq, wallPostReplyData, friendShipService);

        WallPostReplyData wallPostReplyData = WallPostReplyHelper.createWallPostReplyData(wallPostReplyReq, toWallPostData, wallPostReplyFromProfile);

        return wallPostReplyRepository.save(wallPostReplyData);
        //return profileData;
    }

    @Override
    public WallPostReplyData getWallPostReplyBy(String url) throws ModelDataNotFoundException {
        return wallPostReplyRepository.findByUrl(url)
                .orElseThrow(() -> new ModelDataNotFoundException("WallPostReply not found for url=" + url));
    }

    @Override
    public WallPostReplyData updateWallPostReply(String url, WallPostReplyEditReq wallPostReplyReq) throws ModelDataNotFoundException {
        WallPostReplyData wallPostReplyData = getWallPostReplyBy(url);

        validateWallPostReplyEdit(wallPostReplyReq, wallPostReplyData);

        WallPostReplyHelper.updateWallPostReplyData(wallPostReplyData, wallPostReplyReq);

        wallPostReplyData = wallPostReplyRepository.save(wallPostReplyData);

        return wallPostReplyData;
    }

    private void validateWallPostReplyEdit(WallPostReplyEditReq wallPostReplyReq, WallPostReplyData wallPostData) {

    }
}
