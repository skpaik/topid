package io.goribco.core.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.goribco.core.response.BaseReq;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryReq extends BaseReq {
    private String name;
    private String ownerId;
    private String profileUrl;
    private String postOwnerProfileUrl;
    private int pageNum;
    private int pageSize;
    private int resSize;
}
