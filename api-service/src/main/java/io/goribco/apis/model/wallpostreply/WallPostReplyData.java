package io.goribco.apis.model.wallpostreply;

import io.goribco.apis.model.wallpost.WallPostData;
import io.goribco.core.model.BaseModel;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "wall_post_reply_data")
public class WallPostReplyData extends BaseModel {

    @Transient
    WallPostData wallPostData;
    //private String name;
    //private String website;
    @Indexed(unique = true)
    private String url;
    private String location;
    private String msgBody;
    //private String type;
    private byte visibility;//PostAuthority// Current state of Visibility. Default: POST_OWNER
    private String wallPostOwner;// Actual owner User of the  profile
    private String wallPostReplyOwner;// Actual owner of the  profile from the post coming
    private String replyToWallPost;// To which WallPos post will go
    private String replyFromProfile;// Send request from which Profile
    private boolean replyAnonymously;// Wall Poster will stay Anonymous. Decide by Poser. Default: true
}
