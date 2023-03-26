package foorun.unieat.common.exception;

import foorun.unieat.common.http.FooRunResponseCode;
import lombok.Getter;

@Getter
public abstract class ResponseRuntimeException extends RuntimeException {
    protected final FooRunResponseCode responseCode;

    public ResponseRuntimeException(FooRunResponseCode responseCode) {
        super(responseCode.getResponseMessage());

        this.responseCode = responseCode;
    }
}