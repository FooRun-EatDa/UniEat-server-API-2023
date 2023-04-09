package foorun.unieat.common.rules;

import foorun.unieat.api.exception.UniEatServerErrorException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SocialLoginType {
    APPLE,
    GOOGLE,
    KAKAO,
    NAVER,
    ;

    public static SocialLoginType valueOfIgnoreCase(String str) {
        return Arrays.stream(values()).filter(t -> t.name().toLowerCase().equals(str.toLowerCase())).findFirst().orElseThrow(UniEatServerErrorException::new);
    }
}