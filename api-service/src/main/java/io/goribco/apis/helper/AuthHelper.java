package io.goribco.apis.helper;

import io.goribco.apis.configs.security.permission.RoleData;
import io.goribco.apis.model.authmodels.login.AuthenticateReq;
import io.goribco.apis.model.authmodels.login.LoginReq;
import io.goribco.apis.model.authmodels.users.UserData;
import io.goribco.apis.model.authmodels.users.UserReq;
import io.goribco.apis.model.authmodels.users.UserRes;
import io.goribco.apis.model.authmodels.users.providers.github.GitHubUserReq;
import io.goribco.apis.utils.UniqueIdUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class AuthHelper {

    public static UserData createUserData(UserReq userReq) {
        UserData user = new UserData();

        user.setUrl(userReq.getUrl());
        user.setUsername(userReq.getUsername());
        user.setPassword(userReq.getPassword());
        user.setEmail(userReq.getEmail());
        user.setPhone(userReq.getPhone());

        user.setBusinessTitle(userReq.getBusinessTitle());

        user.setFirstName(userReq.getFirstName());
        user.setMiddleName(userReq.getMiddleName());
        user.setLastName(userReq.getLastName());

        return user;
    }

    public static UserData createUserData(UserReq userReq, PasswordEncoder passwordEncoder) {
        UserData userData = createUserData(userReq);

        userData.setUId(UniqueIdUtils.on().getNanoId());
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));

        //userData.setRole(RoleData.VIEWER);
        userData.setRole(RoleData.USER);

        if (userData.getEmail().split("@")[1].contains("admin")) {
            userData.setRole(RoleData.ADMIN);
        } else if (userData.getEmail().split("@")[1].contains("manager")) {
            userData.setRole(RoleData.MANAGER);
        }

        //userData.setRole(RoleData.ADMIN);

        return userData;
    }

    public UserRes getUserResponse(UserData userData) {
        UserRes userResponse = new UserRes();

        userResponse.setId(userData.getId());
        userResponse.setUsername(userData.getUsername());
        userResponse.setUId(userData.getUId());
        userResponse.setEmail(userData.getEmail());
        userResponse.setPhone(userData.getPhone());
        userResponse.setUrl(userData.getUrl());
        userResponse.setFullName(userData.getFullName());
        userResponse.setBusinessTitle(userData.getBusinessTitle());

        return userResponse;
    }

    public UserData getGitHubUserData(GitHubUserReq userReq) {
        UserData user = new UserData();

        user.setUsername(userReq.getUsername());
        //user.setPassword(userReq.getPassword());
        user.setEmail(userReq.getEmail());
        user.setPhone(userReq.getPhone());

        //user.setBusinessTitle(userReq.getBusinessTitle());

        user.setFirstName(userReq.getFirstName());
        user.setMiddleName(userReq.getMiddleName());
        user.setLastName(userReq.getLastName());

        return user;
    }

    public UserData getGitHubUserData(GitHubUserReq gitHubUserReq, PasswordEncoder passwordEncoder) {
        UserData userData = getGitHubUserData(gitHubUserReq);

        userData.setUId(UniqueIdUtils.on().getNanoId());
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));

        userData.setRole(RoleData.USER);

        return userData;
    }

    public List<UserRes> getUserListResponse(List<UserData> userDataList) {
        List<UserRes> userResList = new ArrayList<>();

        for (UserData userData : userDataList) {
            userResList.add(getUserResponse(userData));
        }

        return userResList;
    }

    public UserReq optimizeRegisterUserReq(UserReq userReq) {
        userReq.setPassword(null);
        return userReq;
    }

    public LoginReq optimizeLoginReq(LoginReq loginReq) {
        loginReq.setPassword(null);
        return loginReq;
    }

    public AuthenticateReq optimizeAuthenticateReq(AuthenticateReq authenticateReq) {
        authenticateReq.setPassword(null);
        return authenticateReq;
    }
}
