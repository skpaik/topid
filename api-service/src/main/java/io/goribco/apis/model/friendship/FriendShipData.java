package io.goribco.apis.model.friendship;

import io.goribco.core.model.BaseModel;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "friend_ship_data")
public class FriendShipData extends BaseModel {

    private String fromProfile;
    private String toProfile;
    private boolean friend;//FriendShipType
    private boolean following;//FriendShipType
    private boolean ban;//FriendShipType
    private boolean block;//FriendShipType
}
