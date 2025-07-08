package io.goribco.apis.model.wallpost;

import io.goribco.core.models.optionitems.OptionItem;
import io.goribco.core.models.optionitems.OptionItemList;
import io.goribco.core.models.optionitems.UserTypeInt;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public interface PostAuthority {
    byte NO_ONE = UserTypeInt.NO_ONE;
    byte ANYONE = UserTypeInt.ANYONE;
    byte LOGGED_IN = UserTypeInt.LOGGED_IN;// if user logged in only

    byte FRIEND = UserTypeInt.FRIEND;// who are follow each other
    byte FOLLOWING = UserTypeInt.FOLLOWING;
    byte FOLLOWERS = UserTypeInt.FOLLOWERS;// DELETE IT
    // byte BAN = AppConstants.UserTypeInt.BAN;// Not Block but cannot Perform any action
    //  byte BLOCK = AppConstants.UserTypeInt.BLOCK;// Cannot perform any action
    byte POST_OWNER = UserTypeInt.POST_OWNER;// Cannot perform any action


    List<OptionItem> postAuthorityItems = Arrays.asList(
            OptionItemList.NO_ONE,
            OptionItemList.ANYONE,
            OptionItemList.LOGGED_IN,
            OptionItemList.FRIEND,
            OptionItemList.FOLLOWING
    );

}
