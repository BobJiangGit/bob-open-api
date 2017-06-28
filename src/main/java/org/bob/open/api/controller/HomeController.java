package org.bob.open.api.controller;

import org.bob.open.api.dto.response.ResultResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @RequestMapping(value = "/")
    public ResultResponse<String> home() {
        return ResultResponse.success("welcome openAPI.");
    }

}
