package io.goribco.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseMsg {
    private String message;
    private int httpStatus;
}
