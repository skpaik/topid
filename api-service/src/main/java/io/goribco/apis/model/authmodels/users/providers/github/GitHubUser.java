package io.goribco.apis.model.authmodels.users.providers.github;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_provider_github_user")
public class GitHubUser {
    private String id;
    private String name;
    private String email;
    private String image;
}
