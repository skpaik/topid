package io.goribco.apis.webclient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface EmailServiceClient {

    @GetExchange("/employee/department/{departmentId}")
    public List<String> findByDepartment(@PathVariable("departmentId") Long departmentId);

    @GetExchange("/email/send/{email}/{event}")
    List<String> emailSend(@PathVariable("email") String email, @PathVariable("event") int event);
}
