package io.goribco.apis.model.wallpostreply;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.goribco.core.response.BaseReq;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallPostReplyReq extends BaseReq {

    private String id;
    private String url;
    @NotNull
    private String location;
    @NotNull
    private String msgBody;

    private String replyToWallPost;// To which Profile post will go// WallPost.url
    private String replyFromProfile;// Send request from which user
    private boolean replyAnonymously;// Wall Poster will stay Anonymous. Decide by Poser. Default: true
}