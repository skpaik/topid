package io.goribco.apis.model.authmodels.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.goribco.core.response.BaseRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthRes implements BaseRes {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
