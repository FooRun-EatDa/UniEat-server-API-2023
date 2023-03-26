package foorun.unieat.common.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FooRunResponseCode {
    CODE_200(HttpStatus.OK, "정상적으로 처리되었습니다."),                            // 정상 응답
    CODE_201(HttpStatus.CREATED, "정상적으로 처리되었습니다."),                       // 생성 처리
    CODE_400(HttpStatus.BAD_REQUEST, "요청 양식이 올바르지 않습니다."),               // 데이터가 미완성이거나 Type이 올바르지 않을 경우 발생
    CODE_401(HttpStatus.UNAUTHORIZED, "인증정보가 누락되었습니다."),                  // Http 요청 Header 정보에 인증 유효없이 진행하려는 경우, 서비스 제공을 막기위해 처리
    CODE_402(HttpStatus.PAYMENT_REQUIRED, "결제정보가 누락되었습니다."),              // Http 요청 Header 정보에 결제처리 정보가 없을 때 발생 (현재는 사용하지 않고, 미래를 위해 미리 정의한 코드값)
    CODE_403(HttpStatus.FORBIDDEN, "인가되지 않은 사용자입니다."),                    // Http 요청 Header 정보에 인증키가 만료되어 더이상 권한이 없을 때 발생
    CODE_404(HttpStatus.NOT_FOUND, "데이터 정보를 찾을 수 없습니다."),                // 요청 주소를 찾을 수 없을 때 발생. API 서비스에서 응답코드를 그대로 노출시킬 경우 보안 이슈
    CODE_405(HttpStatus.METHOD_NOT_ALLOWED, "요청한 처리 양식이 올바르지 않습니다."),  // 요청 주소가 존재하지만, 사용하지 못하거나 삭제된 상태일 때 발생. API 서비스에서 응답코드를 그대로 노출시킬 경우 보안 이슈
    CODE_415(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 포맷입니다."),         // API 서버에서 처리를 할 수 없는 데이터 포맷일 때 발생. (예: 요청 포맷: application/x-www-form-urlencoded | 응답 포맷 구현: application/json -> 오류 발생)
    CODE_418(HttpStatus.I_AM_A_TEAPOT, "커피를 끓이라고 있는게 아닙니다."),           // HTTP로 ubiquitous 장비 제어를 시도하는 예제를 설명 때, 처리가 불가능하게 응답하기 위한 개그성 코드(예: HTTP로 IoT 장비 제어 시도 시)
    CODE_500(HttpStatus.INTERNAL_SERVER_ERROR, "요청을 처리 중 오류가 발생했습니다."),// API 서버에 오류가 발생했으나, 응답 시 전체 내용을 알려줄 수 없는 상황일 때 발생
    ;

    private final HttpStatus httpStatus;
    private final String responseMessage;

    public static FooRunResponseCode findByHttpStatus(HttpStatus httpStatus) {
        return Arrays.stream(values())
                .filter(r -> r.getHttpStatus() == httpStatus)
                .findFirst()
                .orElse(FooRunResponseCode.CODE_500);
    }
}