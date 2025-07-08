package io.goribco.apis.configs;

public interface RoutesConfig {
    String basePrefix = "/api/v1/";

    interface Auth {
        String root = basePrefix + "auth";
        String register = "/register";
        String registerGithubUser = "/register-github-user";
        String login = "/login";
        String authenticate = "/authenticate";
        String refreshToken = "/refresh-token";
        String detail = "/u/{id}";
        String logout = "logout";
    }

    interface Person {
        String root = basePrefix + "p";
        String all = "/all";
        String user = "/u";
    }

    interface Profile {
        String root = basePrefix + "profile";
        String create = "/create";
        String edit = "/edit/{url}";
        String update = "/update/{url}";
        String list = "list";// All public list from all user
        String myList = "mylist";// can see all enabled, draft profiles
        String listByOwner = "list/{ownerId}";// all public list by a user
        String detail = "/{url}";
    }

    interface WallPost {
        String root = basePrefix + "wallpost";
        String create = "/create";
        String edit = "/edit/{url}";
        String update = "/update/{url}";
        String updateVisibility = "/update-visibility/{url}";
        // String list = "list";// All public list from all user
        // String myList = "mylist";// can see all enabled, draft profiles
        String listByProfileOwner = "list/{profileUrl}";// all public list by a user
        String detail = "/{url}";
    }

    interface WallPostReply {
        String root = basePrefix + "wallpostreply";
        String create = "/create";
        String edit = "/edit/{url}";
        String update = "/update/{url}";
        String list = "list";// All public list from all user
        String myList = "mylist";// can see all enabled, draft profiles
        String listByOwner = "list/{ownerId}";// all public list by a user
        String detail = "/{url}";
    }

    interface GraphQl {
        String root = "/graphql";
        String graphiql = "/graphiql";
    }
}
