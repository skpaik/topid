package io.goribco.apis.model.wallpost;

import io.goribco.apis.model.wallpostreply.WallPostReplyData;
import io.goribco.core.model.BaseModel;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "wall_post_data")
public class WallPostData extends BaseModel {

    //private String name;
    //private String website;
    @Indexed(unique = true)
    private String url;
    private String location;
    private String msgBody;
    private Date founded;
    //private String type;
    private byte visibility;//PostAuthority// Current state of Visibility. Default: POST_OWNER

    //private String wallOwner;// Actual owner User of the  profile
    private String wallPostOwner;// Actual owner of the  profile from the post coming

    private String postToProfile;// To which Profile post will go
    private String postFromProfile;// Send request from which user
    private boolean postAnonymously;// Wall Poster will stay Anonymous. Decide by Poser. Default: true

    private byte replyVisibility;//Default: PostAuthority.POST_OWNER
    private byte replyAuthority;//Default: PostAuthority.POST_OWNER

    @Transient
    private List<WallPostReplyData> wallPostReplyDataList;
}
