package foorun.unieat.api.model.domain;

import foorun.unieat.common.http.FooRunResponseCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UniEatCommonResponse {
    public final static String COMMON_MESSAGE_KEY = "unieat_response_message";
    public final static String COMMON_BODY_KEY = "unieat_response_data";

    public static ResponseEntity of(FooRunResponseCode code, HttpHeaders headers, Object data) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(COMMON_MESSAGE_KEY, code.getResponseMessage());
        if (data != null) body.put(COMMON_BODY_KEY, data);

        return ResponseEntity.status(code.getHttpStatus())
                .headers(headers)
                .body(body)
                ;
    }

    public static ResponseEntity success() {
        return success(null, null);
    }

    public static ResponseEntity success(HttpHeaders headers) {
        return success(headers, null);
    }

    public static ResponseEntity success(Object data) {
        return success(null, data);
    }

    public static ResponseEntity success(HttpHeaders headers, Object data) {
        return of(FooRunResponseCode.CODE_200, headers, data);
    }

    public static ResponseEntity fail(FooRunResponseCode code) {
        return fail(code, null, null);
    }

    public static ResponseEntity fail(FooRunResponseCode code, HttpHeaders headers) {
        return fail(code, headers, null);
    }

    public static ResponseEntity fail(FooRunResponseCode code, Object data) {
        return fail(code, null, data);
    }

    public static ResponseEntity fail(FooRunResponseCode code, HttpHeaders headers, Object data) {
        return of(code, headers, data);
    }
}