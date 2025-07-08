package io.goribco.apis.configs.security.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.goribco.apis.configs.security.permission.PermissionApi.*;

@RequiredArgsConstructor
public enum RoleData {
    ANONYMOUS(Collections.emptySet()),
    USER(Set.of(
            USER_READ,
            USER_UPDATE,
            USER_DELETE,
            USER_CREATE
    )),
    VIEWER(Set.of(
            VIEWER_READ,
            VIEWER_UPDATE,
            VIEWER_DELETE,
            VIEWER_CREATE
    )),
    ORG_ADMIN(Set.of(
            ORG_ADMIN_READ,
            ORG_ADMIN_UPDATE,
            ORG_ADMIN_DELETE,
            ORG_ADMIN_CREATE
    )),
    ORG_AUTHOR(Set.of(
            ORG_AUTHOR_READ,
            ORG_AUTHOR_UPDATE,
            ORG_AUTHOR_DELETE,
            ORG_AUTHOR_CREATE
    )),
    ORG_EDITOR(Set.of(
            ORG_EDITOR_READ,
            ORG_EDITOR_UPDATE,
            ORG_EDITOR_DELETE,
            ORG_EDITOR_CREATE
    )),
    POST_REVIEWER(Set.of(
            POST_REVIEWER_READ,
            POST_REVIEWER_UPDATE,
            POST_REVIEWER_DELETE,
            POST_REVIEWER_CREATE
    )),
    SQA(Set.of(
            SQA_READ,
            SQA_UPDATE,
            SQA_DELETE,
            SQA_CREATE
    )),
    DEVELOPER(Set.of(
            DEVELOPER_READ,
            DEVELOPER_UPDATE,
            DEVELOPER_DELETE,
            DEVELOPER_CREATE
    )),
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE,
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_DELETE,
            MANAGER_CREATE
    )),
    SUPER_ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE,
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_DELETE,
            MANAGER_CREATE,
            SUPER_ADMIN_READ,
            SUPER_ADMIN_UPDATE,
            SUPER_ADMIN_DELETE,
            SUPER_ADMIN_CREATE
    )),
    MANAGER(Set.of(
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_DELETE,
            MANAGER_CREATE
    ));

    @Getter
    private final Set<PermissionApi> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
