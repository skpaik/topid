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
public class WallPostReplyEditReq extends BaseReq {
    @NotNull
    private String msgBody;
    @NotNull
    private boolean replyAnonymously;// Wall Poster will stay Anonymous. Decide by Poser. Default: true
    //private boolean canBePublic;// Wall Poster will decide Wall owner can make it public or not
    //private String visibility;//PostAuthority// Current state of Visibility. Default: POST_OWNER
}