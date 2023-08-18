package io.goribco.core.response;

import io.goribco.core.request.QueryReq;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResHelper<S extends BaseRes, Q extends BaseReq> {

    @NotNull
    public RestRes<S, Q> makeRestRes(S baseRes, Q baseReq, BaseMsg baseMsg) {
        RestRes<S, Q> restRes = new RestRes<>();

        restRes.set_res(baseRes);
        restRes.set_req(baseReq);
        restRes.set_msg(baseMsg);

        return restRes;
    }


    @NotNull
    public ResponseEntity<?> makeRestRes2(S baseRes, Q baseReq, BaseMsg baseMsg) {
        RestRes<S, Q> restRes = new RestRes<>();

        restRes.set_res(baseRes);
        restRes.set_req(baseReq);
        restRes.set_msg(baseMsg);

        return ResponseEntity.ok(restRes);
    }

    @NotNull
    public EntityModel<?> makeRestRes4(S baseRes, Q baseReq, BaseMsg baseMsg) {
        RestRes<S, Q> restRes = new RestRes<>();

        restRes.set_res(baseRes);
        restRes.set_req(baseReq);

        return processAndResponse(baseMsg, restRes);
    }

    public ResponseEntity<?> makeRestRes3(List<S> baseResList, QueryReq queryReq, BaseMsg baseMsg) {
        RestRes<S, Q> restRes = new RestRes<>();

        restRes.set_res_list(baseResList);
        restRes.set_req_query(queryReq);
        restRes.set_msg(baseMsg);

        return ResponseEntity.ok(restRes);
    }

    public EntityModel<?> makeRestRes5(List<S> baseResList, QueryReq queryReq, BaseMsg baseMsg) {
        RestRes<S, Q> restRes = new RestRes<>();

        restRes.set_res_list(baseResList);
        restRes.set_req_query(queryReq);

        return processAndResponse(baseMsg, restRes);
    }

    @NotNull
    private EntityModel<ResponseEntity<RestRes<S, Q>>> processAndResponse(BaseMsg baseMsg, RestRes<S, Q> restRes) {
        restRes.set_msg(baseMsg);

        int httpStatus = 200;

        if (baseMsg != null) {
            httpStatus = baseMsg.getHttpStatus();
        }

        return EntityModel.of(ResponseEntity.status(httpStatus).body(restRes));
    }
}
