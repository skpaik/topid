package io.goribco.apis.controller;

import io.goribco.apis.configs.RoutesConfig;
import io.goribco.apis.helper.AuthHelper;
import io.goribco.apis.helper.emails.EmailEvent;
import io.goribco.apis.helper.emails.EmailHelper;
import io.goribco.apis.model.authmodels.login.AuthRes;
import io.goribco.apis.model.authmodels.login.AuthenticateReq;
import io.goribco.apis.model.authmodels.login.LoginReq;
import io.goribco.apis.model.authmodels.login.LoginRes;
import io.goribco.apis.model.authmodels.users.UserData;
import io.goribco.apis.model.authmodels.users.UserReq;
import io.goribco.apis.model.authmodels.users.UserRes;
import io.goribco.apis.model.authmodels.users.providers.github.GitHubUserReq;
import io.goribco.apis.model.exceptions.ModelDataExistsException;
import io.goribco.apis.model.exceptions.UserNotFoundException;
import io.goribco.apis.service.AuthService;
import io.goribco.apis.utils.AuthUtil;
//import io.goribco.apis.webclient.BeServiceExchangeClient;
//import io.goribco.apis.webclient.EmployeeClient;
import io.goribco.core.request.QueryReq;
import io.goribco.core.response.AppHttpStatus;
import io.goribco.core.response.BaseMsg;
import io.goribco.core.response.ResHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RoutesConfig.Auth.root)
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;
//    @Autowired
//    private BeServiceExchangeClient beServiceExchangeClient;
//    @Autowired
//    private EmployeeClient employeeClient;
    @PostMapping(value = RoutesConfig.Auth.register)
    public EntityModel<?> registerUser(@RequestBody @Valid UserReq userReq) throws Exception {
        LOGGER.info("registerUser Request with {}", userReq);
        try {
            UserData userDataSaved = authService.saveUser(userReq);

            AuthRes authRes = authService.generateJwtToken(userDataSaved);

            AuthHelper authHelper = new AuthHelper();

            UserRes userRes = authHelper.getUserResponse(userDataSaved);

            userRes.setAuthRes(authRes);

            userReq = authHelper.optimizeRegisterUserReq(userReq);

            new EmailHelper().sendEmailAsync(userReq.getEmail(), EmailEvent.SEND);

            return new ResHelper<UserRes, UserReq>().makeRestRes4(
                    userRes,
                    userReq,
                    new BaseMsg("User register successfully", AppHttpStatus.OK));
        } catch (DuplicateKeyException exception) {
            throw new ModelDataExistsException("Username " + userReq.getUsername() + " already exists!");
        } catch (InterruptedException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new Exception("Something went wrong!");
        }
    }

    @PostMapping(value = RoutesConfig.Auth.registerGithubUser)
    public EntityModel<?> registerGithubUser(@RequestBody @Valid GitHubUserReq gitHubUserReq) throws Exception {
        LOGGER.info("registerGithubUser Request with {}", gitHubUserReq);
        try {
            UserRes userRes = authService.saveGitHubUser(gitHubUserReq);

            return new ResHelper<UserRes, GitHubUserReq>().makeRestRes4(
                    userRes,
                    gitHubUserReq,
                    new BaseMsg("User (Github) register successfully", AppHttpStatus.OK));
        } catch (DuplicateKeyException exception) {
            throw new ModelDataExistsException("Username " + gitHubUserReq.getUsername() + " already exists!");
        } catch (Exception exception) {
            throw new Exception("Something went wrong!");
        }
    }

    @PostMapping(value = RoutesConfig.Auth.login)
    public EntityModel<?> loginUser(@RequestBody @Valid LoginReq loginReq) throws Exception {
        LOGGER.info("loginUser Request with {}", loginReq);
        try {
            LoginRes loginRes = authService.loginUser(loginReq);
            loginReq = new AuthHelper().optimizeLoginReq(loginReq);

            return new ResHelper<LoginRes, LoginReq>().makeRestRes4(
                    loginRes,
                    loginReq,
                    new BaseMsg("User login successfully", AppHttpStatus.OK));
        } catch (UsernameNotFoundException exception) {
            throw new UsernameNotFoundException("Username " + loginReq.getUsername() + " not found!", exception);
        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Username " + loginReq.getUsername() + " or password is not correct!", exception);
        } catch (DisabledException exception) {
            throw new DisabledException("User is not activated", exception);
        } catch (Exception exception) {
            throw new Exception("Something went wrong!");
        }
    }

    @PostMapping(value = RoutesConfig.Auth.authenticate)
    public EntityModel<?> authenticateUser(@RequestBody @Valid AuthenticateReq authenticateReq) throws Exception {
        LOGGER.info("authenticateUser Request with {}", authenticateReq);
        try {
            AuthRes loginRes = authService.authenticateUser(authenticateReq);
            authenticateReq = new AuthHelper().optimizeAuthenticateReq(authenticateReq);

            return new ResHelper<AuthRes, AuthenticateReq>().makeRestRes4(
                    loginRes,
                    authenticateReq,
                    new BaseMsg("User authenticate successfully", AppHttpStatus.OK));
        } catch (UsernameNotFoundException exception) {
            throw new UsernameNotFoundException("Username " + authenticateReq.getUsername() + " not found!", exception);
        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Username " + authenticateReq.getUsername() + " or password is not correct!", exception);
        } catch (DisabledException exception) {
            throw new DisabledException("User is not activated", exception);
        } catch (Exception exception) {
            throw new Exception("Something went wrong!");
        }
    }

    @PostMapping(value = RoutesConfig.Auth.refreshToken)
    public EntityModel<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("refreshToken Request with {}", request.getHeaderNames());

        String currentUserName = AuthUtil.getCurrentUserName();
        try {
            AuthRes loginRes = authService.refreshToken(request, response);

            return new ResHelper<AuthRes, AuthenticateReq>().makeRestRes4(
                    loginRes,
                    null,
                    new BaseMsg("User refresh token get successfully", AppHttpStatus.OK));
        } catch (UsernameNotFoundException exception) {
            throw new UsernameNotFoundException("Username " + currentUserName + " not found!", exception);
        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Username " + currentUserName + " or password is not correct!", exception);
        } catch (DisabledException exception) {
            throw new DisabledException("User is not activated", exception);
        } catch (Exception exception) {
            throw new Exception("Something went wrong!");
        }
    }

    @GetMapping(value = RoutesConfig.Auth.detail)
    public EntityModel<?> getUser(@PathVariable String id) throws UserNotFoundException {
        LOGGER.info("getUser Request with {}", id);
        UserReq userReq = UserReq.builder()
                .id(id)
                .build();

        UserData userData = authService.getUser(userReq);

        UserRes userRes = new AuthHelper().getUserResponse(userData);

        return new ResHelper<UserRes, UserReq>().makeRestRes4(
                userRes,
                userReq,
                new BaseMsg("User detail successfully", AppHttpStatus.OK));
    }

    @GetMapping(value = RoutesConfig.Person.all)
    public EntityModel<?> getAllUser(
            @RequestParam(name = "name", defaultValue = "", required = false) String name,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "24", required = false) int limit) {

        QueryReq queryReq = QueryReq.builder()
                .name(name)
                .pageNum(page)
                .pageSize(limit)
                .build();
        LOGGER.info("getAllUser Request with {}", queryReq);

        List<UserData> userDataList = authService.getAllUser(queryReq);

        List<UserRes> userResList = new AuthHelper().getUserListResponse(userDataList);

        queryReq.setResSize(userDataList.size());

        return new ResHelper<UserRes, QueryReq>().makeRestRes5(
                userResList,
                queryReq,
                new BaseMsg("User list successfully", AppHttpStatus.OK));
    }
}
