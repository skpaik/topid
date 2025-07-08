package io.goribco.apis.model.authmodels.users.providers.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.goribco.apis.model.authmodels.users.UserBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUserReq extends UserBase {
    private GitHubUser user;
    private GitHubAccount account;
    private GitHubProfile profile;
}


