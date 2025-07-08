package io.goribco.apis.configs;

public interface AuthConfig {
    interface ROLES {
        String USER = "USER";
        String VIEWER = "VIEWER";
        String ORG_ADMIN = "ORG_ADMIN";
        String ORG_AUTHOR = "ORG_AUTHOR";
        String ORG_EDITOR = "ORG_EDITOR";

        String POST_REVIEWER = "POST_REVIEWER";
        String SQA = "SQA";
        String DEVELOPER = "DEVELOPER";
        String ADMIN = "ADMIN";
        String SUPER_ADMIN = "SUPER_ADMIN";
        String DEFAULT = USER;
    }
}
