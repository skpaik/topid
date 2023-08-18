package io.goribco.apis.model.errrorres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestErrorMessage {
    public HttpStatus statusCode;
    public int statusCodeValue;

    public RestErrorBody body;
    public Object headers;
}
