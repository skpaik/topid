package io.goribco.apis.controller;

import io.goribco.apis.configs.RoutesConfig;
import io.goribco.apis.helper.AuthHelper;
import io.goribco.apis.model.authmodels.users.UserData;
import io.goribco.apis.model.authmodels.users.UserReq;
import io.goribco.apis.model.authmodels.users.UserRes;
import io.goribco.apis.model.exceptions.UserNotFoundException;
import io.goribco.apis.service.AuthService;
import io.goribco.core.response.AppHttpStatus;
import io.goribco.core.response.BaseMsg;
import io.goribco.core.response.ResHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RoutesConfig.Person.root)
public class PersonController {
    @Autowired
    private AuthService authService;

    @GetMapping("/{id}")
    public EntityModel<?> getUser(@PathVariable String id) throws UserNotFoundException {
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
}
