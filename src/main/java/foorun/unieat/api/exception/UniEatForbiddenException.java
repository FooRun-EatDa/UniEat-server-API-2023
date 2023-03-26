package foorun.unieat.api.exception;

import foorun.unieat.common.exception.ResponseRuntimeException;
import foorun.unieat.common.http.FooRunResponseCode;

public class UniEatForbiddenException extends ResponseRuntimeException {
    public UniEatForbiddenException() {
        super(FooRunResponseCode.CODE_403);
    }
}
