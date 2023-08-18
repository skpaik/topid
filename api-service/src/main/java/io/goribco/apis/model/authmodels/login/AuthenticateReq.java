package io.goribco.apis.model.authmodels.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.goribco.core.response.BaseReq;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateReq extends BaseReq {
    @NotNull
    private String username;
    @NotNull
    private String password;
}