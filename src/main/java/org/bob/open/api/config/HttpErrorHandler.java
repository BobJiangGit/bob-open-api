package org.bob.open.api.config;

import org.bob.open.api.dto.response.ResultResponse;
import org.bob.open.api.log.LogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Bob Jiang on 2017/4/6.
 */
@RestController
public class HttpErrorHandler extends AbstractErrorController {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static final String ERROR_PATH = "/error";

    public HttpErrorHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return this.ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH)
    public ResultResponse error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, false);
        HttpStatus status = getStatus(request);
        String message = body.get("message").toString();

        if (status.equals(HttpStatus.BAD_REQUEST)) {
            List<ObjectError> errors = (List<ObjectError>) body.get("errors");
            if (errors != null && errors.size() > 0) {
                message = errors.get(0).getDefaultMessage();
            }
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("LOG [Exception][").append(body.get("path")).append("] 出现异常: [");
        buffer.append(status.value()).append(", ").append(status.getReasonPhrase());
        buffer.append("] ").append(" 异常信息：").append(message);
        logger.error(buffer.toString());
        return ResultResponse.define(status.value(), message);
    }

}
