package io.goribco.apis.component;


import io.goribco.apis.controller.ProfileController;
import io.goribco.apis.model.profile.ProfileRes;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProfileResAssembler implements RepresentationModelAssembler<ProfileRes, EntityModel<ProfileRes>> {

    @Override
    public @NotNull EntityModel<ProfileRes> toModel(@NotNull ProfileRes employee) {

        try {
            return EntityModel.of(employee, //
                    linkTo(methodOn(ProfileController.class).detailProfile(employee.getUrl())).withSelfRel(),
                    linkTo(methodOn(ProfileController.class).editProfile(employee.getOwnerId())).withRel("profile"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}