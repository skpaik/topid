package io.goribco.apis.service;

import io.goribco.apis.model.authmodels.login.AuthRes;
import io.goribco.apis.model.authmodels.login.AuthenticateReq;
import io.goribco.apis.model.authmodels.login.LoginReq;
import io.goribco.apis.model.authmodels.login.LoginRes;
import io.goribco.apis.model.authmodels.users.UserData;
import io.goribco.apis.model.authmodels.users.UserReq;
import io.goribco.apis.model.authmodels.users.UserRes;
import io.goribco.apis.model.authmodels.users.providers.github.GitHubUserReq;
import io.goribco.apis.model.exceptions.UserNotFoundException;
import io.goribco.core.request.QueryReq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.List;

public interface AuthService {
    UserData saveUser(UserReq userReq);

    //LoginRes loginUserOld(LoginReq loginReq) throws UsernameNotFoundException;

    LoginRes loginUser(LoginReq loginReq) throws UsernameNotFoundException, DisabledException, BadCredentialsException, IOException;

    AuthRes authenticateUser(AuthenticateReq authenticateReq) throws UsernameNotFoundException, DisabledException, BadCredentialsException, IOException;

    UserData getUser(UserReq userReq) throws UserNotFoundException;

    List<UserData> getAllUser(QueryReq queryReq);

    UserRes saveGitHubUser(GitHubUserReq gitHubUserReq);

    AuthRes generateJwtToken(UserData savedUser);

    AuthRes refreshToken(HttpServletRequest request, HttpServletResponse response);
}
