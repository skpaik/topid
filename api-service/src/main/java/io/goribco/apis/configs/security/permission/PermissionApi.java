package io.goribco.apis.configs.security.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PermissionApi {
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),

    VIEWER_READ("viewer:read"),
    VIEWER_UPDATE("viewer:update"),
    VIEWER_CREATE("viewer:create"),
    VIEWER_DELETE("viewer:delete"),

    ORG_ADMIN_READ("org_admin:read"),
    ORG_ADMIN_UPDATE("org_admin:update"),
    ORG_ADMIN_CREATE("org_admin:create"),
    ORG_ADMIN_DELETE("org_admin:delete"),

    ORG_AUTHOR_READ("org_author:read"),
    ORG_AUTHOR_UPDATE("org_author:update"),
    ORG_AUTHOR_CREATE("org_author:create"),
    ORG_AUTHOR_DELETE("org_author:delete"),

    ORG_EDITOR_READ("org_editor:read"),
    ORG_EDITOR_UPDATE("org_editor:update"),
    ORG_EDITOR_CREATE("org_editor:create"),
    ORG_EDITOR_DELETE("org_editor:delete"),

    POST_REVIEWER_READ("post_reviewer:read"),
    POST_REVIEWER_UPDATE("post_reviewer:update"),
    POST_REVIEWER_CREATE("post_reviewer:create"),
    POST_REVIEWER_DELETE("post_reviewer:delete"),

    SQA_READ("sqa:read"),
    SQA_UPDATE("sqa:update"),
    SQA_CREATE("sqa:create"),
    SQA_DELETE("sqa:delete"),

    DEVELOPER_READ("developer:read"),
    DEVELOPER_UPDATE("developer:update"),
    DEVELOPER_CREATE("developer:create"),
    DEVELOPER_DELETE("developer:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),

    SUPER_ADMIN_READ("super_admin:read"),
    SUPER_ADMIN_UPDATE("super_admin:update"),
    SUPER_ADMIN_CREATE("super_admin:create"),
    SUPER_ADMIN_DELETE("super_admin:delete");

    @Getter
    private final String permission;
}
