package io.goribco.apis.model.profile;

import io.goribco.apis.model.wallpost.WallPostRes;
import io.goribco.core.model.BaseModel;
import io.goribco.core.response.BaseRes;
import lombok.*;
import org.springframework.hateoas.Link;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProfileRes extends BaseModel implements BaseRes {
    private String name;
    private String url;
    private String website;
    private String location;
    private Date founded;
    private String type;

    private byte profileVisibility;//Default: PostAuthority.Anyone
    private byte wallPostAuthority;//Default: PostAuthority.Anyone
    private byte wallPostVisibility;//Default: PostAuthority.Anyone

    private boolean allowAnonymousWallPost;//boolean. Default: true
    private byte allowAnonymousWallPostFrom;//PostAuthority: Default: Anyone

    private byte showInList;//PostAuthority: Default: Anyone
    private byte showInUserAccount;//PostAuthority: Default: NoOne

    private List<Link> links;
    private long totalFollowers;
    private List<WallPostRes> _res_list_wall_post;
}
