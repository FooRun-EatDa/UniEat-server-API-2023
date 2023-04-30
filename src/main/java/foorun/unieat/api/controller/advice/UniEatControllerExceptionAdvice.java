package foorun.unieat.api.controller.advice;

import foorun.unieat.api.model.domain.UniEatCommonResponse;
import foorun.unieat.common.exception.ResponseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 예외처리를 담당하는 클래스
 * @RestControllerAdvice: 지정된 패키지의 모든 컨트롤러에 대한 예외처리를 한 곳에서 처리할 수 있음
 * basePackages를 설정하여 특정 클래스에만 제한적으로 적용이 가능함
 * @Order: 예외처리의 우선순위를 지정할 수 있음
 * Ordered.HIGHEST_PRECEDENCE -> 가장 높은 우선순위를 의미함
 */
/* 공통 오류 처리 */
@RestControllerAdvice(basePackages = "foorun.unieat.api.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class UniEatControllerExceptionAdvice {

    /**
     * @ExceptionHandler: 처리할 예외 타입을 지정하고, 예외가 발생했을 때 호출될 메소드를 정의함
     */
    @ExceptionHandler(ResponseRuntimeException.class)
    public ResponseEntity exception(ResponseRuntimeException e) {
        e.printStackTrace();
        return UniEatCommonResponse.fail(e.getResponseCode());
    }
}