package io.goribco.apis.model.wallpost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.goribco.core.response.BaseReq;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallPostVisibilityUpdateReq extends BaseReq {
    @NotNull
    private byte visibility;//PostAuthority// Current state of Visibility. Default: POST_OWNER
}