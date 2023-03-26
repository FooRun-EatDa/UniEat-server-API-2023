package foorun.unieat.api.model.domain.response;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import foorun.unieat.common.http.FooRunResponseCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UniEatCommonResponse {
    public static <T extends UniEatBaseDTO> ResponseEntity<T> of(FooRunResponseCode code, HttpHeaders headers, T data) {
        return ResponseEntity.status(code.getHttpStatus())
                .headers(headers)
                .body(data)
                ;
    }

    public static ResponseEntity success() {
        return success(null, null);
    }

    public static <T extends UniEatBaseDTO> ResponseEntity<T> success(HttpHeaders headers) {
        return success(headers, null);
    }

    public static <T extends UniEatBaseDTO> ResponseEntity<T> success(T data) {
        return success(null, data);
    }

    public static <T extends UniEatBaseDTO> ResponseEntity<T> success(HttpHeaders headers, T data) {
        return of(FooRunResponseCode.CODE_200, headers, data);
    }

    public static ResponseEntity fail(FooRunResponseCode code) {
        return fail(code, null, null);
    }

    public static <T extends UniEatBaseDTO> ResponseEntity<T> fail(FooRunResponseCode code, HttpHeaders headers) {
        return fail(code, headers, null);
    }

    public static <T extends UniEatBaseDTO> ResponseEntity<T> fail(FooRunResponseCode code, T data) {
        return fail(code, null, data);
    }

    public static <T extends UniEatBaseDTO> ResponseEntity<T> fail(FooRunResponseCode code, HttpHeaders headers, T data) {
        return of(code, headers, data);
    }
}