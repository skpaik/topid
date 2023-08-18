package io.goribco.apis.model.profile;

import io.goribco.apis.model.wallpost.WallPostData;
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
@Document(collection = "profile_data")
public class ProfileData extends BaseModel {

    //@NotNull
    private String name;

    //@NotNull
    @Indexed(unique = true)
    private String url;

    private String website;

    //@NotNull
    private String location;

    //@NotNull
    private Date founded;
    //@NotNull
    private String type;
    //@NotNull
    private byte profileVisibility;//Default: PostAuthority.Anyone
    private byte wallPostAuthority;//Default: PostAuthority.Anyone
    private byte wallPostVisibility;//Default: PostAuthority.Anyone

    private boolean allowAnonymousWallPost;//boolean. Default: true
    private byte allowAnonymousWallPostFrom;//PostAuthority: Default: Anyone

    private byte showInList;//PostAuthority: Default: Anyone
    private byte showInUserAccount;//PostAuthority: Default: NoOne

    @Transient
    private List<WallPostData> wallPostDataList;
}
