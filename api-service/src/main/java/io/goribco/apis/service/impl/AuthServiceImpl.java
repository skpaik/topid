package io.goribco.apis.service.impl;

import io.goribco.apis.configs.security.jwt.JwtHelper;
import io.goribco.apis.helper.AuthHelper;
import io.goribco.apis.model.authmodels.login.AuthRes;
import io.goribco.apis.model.authmodels.login.AuthenticateReq;
import io.goribco.apis.model.authmodels.login.LoginReq;
import io.goribco.apis.model.authmodels.login.LoginRes;
import io.goribco.apis.model.authmodels.users.UserData;
import io.goribco.apis.model.authmodels.users.UserReq;
import io.goribco.apis.model.authmodels.users.UserRes;
import io.goribco.apis.model.authmodels.users.providers.github.GitHubUserReq;
import io.goribco.apis.model.exceptions.UserNotFoundException;
import io.goribco.apis.model.token.TokenData;
import io.goribco.apis.model.token.TokenType;
import io.goribco.apis.repository.TokenRepository;
import io.goribco.apis.repository.UserDataRepository;
import io.goribco.apis.service.AuthService;
import io.goribco.apis.service.UserDataService;
import io.goribco.core.request.QueryReq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service(value = "authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public UserData saveUser(UserReq userReq) {
        UserData userData = AuthHelper.createUserData(userReq, passwordEncoder);

        UserData userDataSaved =new UserData();// userDataRepository.save(userData);

        return userDataSaved;
    }

    @Override
    public LoginRes loginUser(LoginReq loginReq)
            throws UsernameNotFoundException,
            DisabledException,
            BadCredentialsException,
            IOException {
        final UserData userDetails = userDataService.findByUsername(loginReq.getUsername());

        final String jwt = jwtHelper.generateToken(userDetails);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword(), userDetails.getAuthorities()));

        return new LoginRes(jwt);
    }

    @Override
    public AuthRes authenticateUser(AuthenticateReq authenticateReq)
            throws UsernameNotFoundException,
            DisabledException,
            BadCredentialsException,
            IOException {
        UserData userData = userDataService.findByUsername(authenticateReq.getUsername());

        String jwtToken = jwtHelper.generateToken(userData);
        String refreshToken = jwtHelper.generateRefreshToken(userData);
        revokeAllUserTokens(userData);

        saveUserToken(userData, jwtToken);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateReq.getUsername(), authenticateReq.getPassword(), userData.getAuthorities()));

        AuthRes authRes = AuthRes.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

        return authRes;
    }

    @Override
    public UserData getUser(UserReq userReq) throws UserNotFoundException {
        UserData userData = userDataRepository.findById(userReq.getId())
                .orElseThrow(() -> new UserNotFoundException("user not found with id : " + userReq.getId()));

        return userData;
    }

    @Override
    public List<UserData> getAllUser(QueryReq queryReq) {
        List<UserData> userDataList = userDataRepository.findAll();

        return userDataList;
    }

    @Override
    public UserRes saveGitHubUser(GitHubUserReq gitHubUserReq) {
        AuthHelper authHelper = new AuthHelper();

        UserData userData = authHelper.getGitHubUserData(gitHubUserReq, passwordEncoder);

        UserData userDataSaved = userDataRepository.save(userData);

        UserRes userResponse = authHelper.getUserResponse(userDataSaved);

        return userResponse;
    }
//    private  PasswordEncoder passwordEncoder;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Override
    public AuthRes generateJwtToken(UserData userData) {

        String jwtToken = jwtHelper.generateToken(userData);
        String refreshToken = jwtHelper.generateRefreshToken(userData);

        saveUserToken(userData, jwtToken);

        return AuthRes.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    private void saveUserToken(UserData user, String jwtToken) {

        TokenData token = TokenData.builder()
                //  .user(user)
                .userUrl(user.getUrl())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }


    @Override
    public AuthRes refreshToken(HttpServletRequest request, HttpServletResponse response) {
        AuthRes authRes = AuthRes.builder().build();

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return authRes;
        }

        refreshToken = authHeader.substring(7);
        username = jwtHelper.extractUsername(refreshToken);

        if (username != null) {
            UserData user = userDataRepository.findByUsername(username).orElseThrow();

            if (jwtHelper.isTokenValid(refreshToken, user)) {
                String accessToken = jwtHelper.generateToken(user);

                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

//                var authResponse = AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();

                authRes.setAccessToken(accessToken);
                authRes.setRefreshToken(refreshToken);
                //   new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

        return authRes;
    }

    private void revokeAllUserTokens(UserData user) {
        List<TokenData> validUserTokens = tokenRepository.findAllByUserUrl(user.getUrl());

        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }
}
