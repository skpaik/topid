package io.goribco.apis.model.wallpost;

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
public class WallPostReq extends BaseReq {
    private String id;
    private String url;
    @NotNull
    private String location;
    @NotNull
    private String msgBody;

    //private String wallOwner;// Actual owner User of the  profile
    @NotNull
    private String postToProfile;// To which Profile post will go
    //private String postOwner;// Actual Logged-In user posting to wall
    @NotNull
    private String postFromProfile;// Send request from which user

    @NotNull
    private boolean postAnonymously;// Wall Poster will stay Anonymous. Decide by Poser. Default: true

    private byte visibility;//PostAuthority// Current state of Visibility. Default: POST_OWNER
}