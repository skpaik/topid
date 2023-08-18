package io.goribco.apis.model.wallpost;

import io.goribco.core.model.BaseModel;
import io.goribco.core.models.optionitems.OptionItem;
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
public class WallPostRes extends BaseModel implements BaseRes {
    private String name;
    private String url;
    private String website;
    private String location;
    private Date founded;
    private String msgBody;
    private String type;
    private byte visibility;


    private String postToProfile;// To which Profile post will go
    private String postFromProfile;// Send request from which user


    private List<Link> links;
    private List<OptionItem> form_visibility_list;
}
