package foorun.unieat.api.controller.advice;

import foorun.unieat.api.model.domain.UniEatCommonResponse;
import foorun.unieat.common.exception.ResponseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "foorun.unieat.api.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class UniEatControllerExceptionAdvice {
    @ExceptionHandler(ResponseRuntimeException.class)
    public ResponseEntity exception(ResponseRuntimeException e) {
        e.printStackTrace();
        return UniEatCommonResponse.fail(e.getResponseCode());
    }
}