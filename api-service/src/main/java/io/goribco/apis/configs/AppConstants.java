package io.goribco.apis.configs;

import io.goribco.apis.utils.AuthUtil;

public interface AppConstants {


    String[] swaggerUrls = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };


    String actuatorBasePath = "/actuator";


    String[] actuatorUrls = {
            actuatorBasePath,
            //"/actuator/**",
            actuatorBasePath + "/beans",
            actuatorBasePath + "/info",
            actuatorBasePath + "/sessions",
            actuatorBasePath + "/env",
            actuatorBasePath + "/env/*",
            actuatorBasePath + "/health",
            actuatorBasePath + "/health/**"
    };
    String[] authUrls = {
            RoutesConfig.Auth.root + RoutesConfig.Auth.register,
            RoutesConfig.Auth.root + RoutesConfig.Auth.login,
            RoutesConfig.Auth.root + RoutesConfig.Auth.authenticate,
            RoutesConfig.Person.root + RoutesConfig.Person.all
    };
    String[] publicUrls = {
            "/",
            "/api",
            "/api/",
            "/api/tutorials",
            "/users/authenticate",
            "/users/register",
            RoutesConfig.GraphQl.root,
            RoutesConfig.GraphQl.graphiql,
            RoutesConfig.Profile.root + RoutesConfig.Profile.detail,
            RoutesConfig.WallPost.root + RoutesConfig.WallPost.create,
            RoutesConfig.WallPost.root + RoutesConfig.WallPost.detail
    };
    String[] unAuthorizedUrls = AuthUtil.concatStringArray(swaggerUrls, actuatorUrls, authUrls, publicUrls);
    String crossOriginUrl = "http://localhost:3000";//"*"
}