package io.goribco.apis.model.profile;

import org.springframework.hateoas.RepresentationModel;

public class PersonModel extends RepresentationModel<PersonModel> {

    public String firstname, lastname;
}