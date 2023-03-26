package foorun.unieat.api.exception;

import foorun.unieat.common.exception.ResponseRuntimeException;
import foorun.unieat.common.http.FooRunResponseCode;

public class UniEatBadRequestException extends ResponseRuntimeException {
    public UniEatBadRequestException() {
        super(FooRunResponseCode.CODE_400);
    }
}
