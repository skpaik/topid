package io.goribco.apis.model.wallpost;

import io.goribco.core.model.BaseModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class WallPostModel extends BaseModel {
    @NotNull
    private String name;

    @NotNull
    @Indexed(unique = true)
    @Pattern(regexp = "^[a-z\\d]+(?:-[a-z\\d]+)*$", message = "invalid url pattern")
    private String url;

    private String website;

    @NotNull
    private String location;

    @NotNull
    private Date founded;

    @NotNull
    private String type;
}
