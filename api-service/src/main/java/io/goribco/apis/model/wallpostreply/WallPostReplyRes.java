package io.goribco.apis.model.wallpostreply;

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
public class WallPostReplyRes extends BaseModel implements BaseRes {
    private String name;
    private String url;
    private String website;
    private String location;
    private Date founded;
    private String msgBody;
    private String type;
    private List<Link> links;
    private boolean replyAnonymously;// Wall Poster will stay Anonymous. Decide by Poser. Default: true
    private WallPostRes _res_data_wall_post;
}
