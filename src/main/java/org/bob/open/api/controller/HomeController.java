package org.bob.open.api.controller;

import org.bob.open.api.dto.response.ResultResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class HomeController {

    @RequestMapping(value = "/")
    public ModelAndView root() {
        return new ModelAndView("redirect:/open-api");
    }

    @RequestMapping(value = "/open-api")
    public ResultResponse<String> home() {
        return ResultResponse.success("welcome openAPI.");
    }

}
