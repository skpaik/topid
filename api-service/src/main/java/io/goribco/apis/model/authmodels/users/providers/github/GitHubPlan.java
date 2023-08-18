package io.goribco.apis.model.authmodels.users.providers.github;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_provider_github_plan")
public class GitHubPlan {
    private String name;
    private Integer space;
    private Integer collaborators;
    private Integer privateRepos;
}



