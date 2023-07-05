package foorun.unieat.common.rules;

import foorun.unieat.api.exception.UniEatServerErrorException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SocialLoginType {
    FOORUN,
    APPLE,
    GOOGLE,
    KAKAO,
    NAVER,
    ;
}