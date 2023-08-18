package io.goribco.apis.model.authmodels.login;

import io.goribco.core.response.BaseRes;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRes implements BaseRes {
    private String token;
}
