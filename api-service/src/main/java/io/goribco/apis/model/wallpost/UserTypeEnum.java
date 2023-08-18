package io.goribco.apis.model.wallpost;

import io.goribco.core.models.optionitems.UserTypeInt;
import lombok.Getter;

@Getter
public enum UserTypeEnum {
    NO_ONE(UserTypeInt.NO_ONE),
    ANYONE(UserTypeInt.ANYONE),
    LOGGED_IN(UserTypeInt.LOGGED_IN),
    FRIEND(UserTypeInt.FRIEND),
    FOLLOWING(UserTypeInt.FOLLOWING),
    FOLLOWERS(UserTypeInt.FOLLOWERS),
    BAN(UserTypeInt.BAN),
    BLOCK(UserTypeInt.BLOCK),
    POST_OWNER(UserTypeInt.POST_OWNER);

    private final byte id;

    UserTypeEnum(byte _id) {
        id = _id;
    }

}
