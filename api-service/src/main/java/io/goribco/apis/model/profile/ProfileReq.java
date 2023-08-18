package io.goribco.apis.model.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.goribco.apis.model.wallpost.PostAuthority;
import io.goribco.core.response.BaseReq;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileReq extends BaseReq {
    private String id;
    @NotNull
    private String name;
    @NotNull
    @Pattern(regexp = "^[a-z\\d]+(?:-[a-z\\d]+)*$", message = "invalid url pattern")
    private String url;
    private String website;
    @NotNull
    private String location;
    @NotNull
    private Date founded;
    private String type;

    private byte profileVisibility;//Default: PostAuthority.Anyone
    private byte wallPostAuthority;//Default: PostAuthority.Anyone
    private byte wallPostVisibility;//Default: PostAuthority.Anyone

    private Boolean allowAnonymousWallPost;//boolean. Default: true
    private byte allowAnonymousWallPostFrom;//PostAuthority: Default: Anyone

    private byte showInList;//PostAuthority: Default: Anyone
    private byte showInUserAccount;//PostAuthority: Default: NoOne

    public byte getProfileVisibility() {
        return Objects.requireNonNullElse(profileVisibility, PostAuthority.ANYONE);
    }

    public byte getWallPostAuthority() {
        return Objects.requireNonNullElse(wallPostAuthority, PostAuthority.ANYONE);
    }

    public byte getWallPostVisibility() {
        return Objects.requireNonNullElse(wallPostVisibility, PostAuthority.ANYONE);
    }

    public boolean isAllowAnonymousWallPost() {
        return Objects.requireNonNullElse(allowAnonymousWallPost, true);
    }

    public byte getAllowAnonymousWallPostFrom() {
        return Objects.requireNonNullElse(allowAnonymousWallPostFrom, PostAuthority.ANYONE);
    }

    public byte getShowInList() {
        return Objects.requireNonNullElse(showInList, PostAuthority.ANYONE);
    }

    public byte getShowInUserAccount() {
        return Objects.requireNonNullElse(showInUserAccount, PostAuthority.NO_ONE);
    }
}