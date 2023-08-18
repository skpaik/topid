package io.goribco.apis.model.authmodels.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserReq extends UserBase {
    @NotNull
    @Pattern(regexp = "^[a-z\\d]+(?:-[a-z\\d]+)*$", message = "invalid url pattern")
    private String url;
    private String password;
    private String id;
}