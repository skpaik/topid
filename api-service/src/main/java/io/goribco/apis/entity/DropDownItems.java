package io.goribco.apis.entity;

import io.goribco.core.models.optionitems.OptionItem;
import io.goribco.core.models.optionitems.OptionItemList;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class DropDownItems {
    @Getter
    private static final List<OptionItem> friendshipItems = Arrays.asList(
            OptionItemList.FRIEND,
            OptionItemList.FOLLOWING,
            OptionItemList.FOLLOWERS,
            OptionItemList.BAN,
            OptionItemList.BLOCK
    );

}
