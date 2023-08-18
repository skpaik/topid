package io.goribco.apis.model.authmodels.users.providers.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_provider_github_account")
public class GitHubAccount {
    private String provider;
    private String type;
    private String providerAccountId;
    private String accessToken;
    private String tokenType;
    private String scope;
}
