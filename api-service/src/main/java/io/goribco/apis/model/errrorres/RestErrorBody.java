package io.goribco.apis.model.errrorres;

import io.goribco.core.response.BaseMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestErrorBody {
    private BaseMsg _msg;
    private Map<String, String> _errors;
    private String _error;
}
