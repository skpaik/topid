package io.goribco.apis.model.profile;

import io.goribco.core.models.optionitems.UserTypeInt;

public interface FriendShipType {
    byte FRIEND = UserTypeInt.FRIEND;// who are follow each other
    byte FOLLOWING = UserTypeInt.FOLLOWING;
    byte FOLLOWERS = UserTypeInt.FOLLOWERS;// DELETE IT
    byte BAN = UserTypeInt.BAN;// Not Block but cannot Perform any action
    byte BLOCK = UserTypeInt.BLOCK;// Cannot perform any action
}
