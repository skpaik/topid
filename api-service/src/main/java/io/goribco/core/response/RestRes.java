package io.goribco.core.response;

import io.goribco.core.request.QueryReq;
import lombok.Data;

import java.util.List;

@Data
public class RestRes<S extends BaseRes, Q extends BaseReq> {
    private BaseMsg _msg;
    private S _res;
    private Q _req;

    private List<S> _res_list;
    private QueryReq _req_query;
}
