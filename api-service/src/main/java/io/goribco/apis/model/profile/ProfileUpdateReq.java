package io.goribco.apis.model.profile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProfileUpdateReq extends ProfileReq {
    @NotNull
    @Min(value = 1, message = "Minimum value is 1")
    @Max(value = 16, message = "Maximum value is 16")
    private byte profileVisibility;//Default: PostAuthority.Anyone

    @NotNull
    @Min(value = 1, message = "Minimum value is 1")
    @Max(value = 16, message = "Maximum value is 16")
    private byte wallPostAuthority;//Default: PostAuthority.Anyone

    @NotNull
    @Min(value = 1, message = "Minimum value is 1")
    @Max(value = 16, message = "Maximum value is 16")
    private byte wallPostVisibility;//Default: PostAuthority.Anyone

    @NotNull
    @Min(value = 1, message = "Minimum value is 1")
    @Max(value = 16, message = "Maximum value is 16")
    private byte allowAnonymousWallPostFrom;//PostAuthority: Default: Anyone

    @NotNull
    @Min(value = 1, message = "Minimum value is 1")
    @Max(value = 16, message = "Maximum value is 16")
    private byte showInList;//PostAuthority: Default: Anyone

    @NotNull
    @Min(value = 1, message = "Minimum value is 1")
    @Max(value = 16, message = "Maximum value is 16")
    private byte showInUserAccount;//PostAuthority: Default: NoOne
}