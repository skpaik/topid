package io.goribco.core.models.optionitems;

public interface OptionItemList {
    OptionItem NO_ONE = new OptionItem(UserTypeInt.NO_ONE, UserTypeStr.NO_ONE);
    OptionItem ANYONE = new OptionItem(UserTypeInt.ANYONE, UserTypeStr.ANYONE);

    OptionItem LOGGED_IN = new OptionItem(UserTypeInt.LOGGED_IN, UserTypeStr.LOGGED_IN);

    OptionItem FRIEND = new OptionItem(UserTypeInt.FRIEND, UserTypeStr.FRIEND);

    OptionItem FOLLOWING = new OptionItem(UserTypeInt.FOLLOWING, UserTypeStr.FOLLOWING);

    OptionItem FOLLOWERS = new OptionItem(UserTypeInt.FOLLOWERS, UserTypeStr.FOLLOWERS);

    OptionItem BAN = new OptionItem(UserTypeInt.BAN, UserTypeStr.BAN);

    OptionItem BLOCK = new OptionItem(UserTypeInt.BLOCK, UserTypeStr.BLOCK);

    OptionItem POST_OWNER = new OptionItem(UserTypeInt.POST_OWNER, UserTypeStr.POST_OWNER);
}
