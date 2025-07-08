package io.goribco.apis.model.authmodels.users;

import io.goribco.apis.model.authmodels.login.AuthRes;
import io.goribco.core.response.BaseRes;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRes extends UserBase implements BaseRes {
    private String id;
    private String uId;
    private String url;
    private String fullName;
    private AuthRes authRes;
}
