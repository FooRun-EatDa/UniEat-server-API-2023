package foorun.unieat.api.exception;

import foorun.unieat.common.exception.ResponseRuntimeException;
import foorun.unieat.common.http.FooRunResponseCode;

public class UniEatUnAuthorizationException extends ResponseRuntimeException {
    public UniEatUnAuthorizationException() {
        super(FooRunResponseCode.CODE_401);
    }
}