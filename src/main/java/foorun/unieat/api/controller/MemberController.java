package foorun.unieat.api.controller;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.exception.UniEatServerErrorException;
import foorun.unieat.api.exception.UniEatUnAuthorizationException;
import foorun.unieat.api.model.domain.UniEatCommonResponse;
import foorun.unieat.api.model.domain.member.request.MemberSignUp;
import foorun.unieat.api.model.domain.member.request.OAuth2SignIn;
import foorun.unieat.api.model.domain.member.response.OAuth2Token;
import foorun.unieat.api.service.member.MemberSignInService;
import foorun.unieat.api.service.member.MemberSignUpService;
import foorun.unieat.common.http.FooRunToken;
import foorun.unieat.common.rules.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberSignUpService memberSignUpService;
    private final MemberSignInService memberSignInService;

    /*@RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity oAuthSignIn(@Validated @RequestBody OAuth2User form) {
        log.debug("try sign in: {}", form);
        ResponseEntity response = memberSignInService.service(form);

        return response;
    }*/

    @RequestMapping(value = "/sign-in/{providerStr}", method = RequestMethod.POST)
    public ResponseEntity signInKakao(@PathVariable String providerStr, @Validated @RequestBody OAuth2SignIn form) {
        if (providerStr == null || providerStr.trim().isEmpty()) {
            throw new UniEatUnAuthorizationException();
        }
        SocialLoginType loginType;
        try {
            loginType = SocialLoginType.valueOf(providerStr.toUpperCase());
        } catch (Exception e) {
            log.error("#### 지원하지 않는 소셜 로그인 시도: {}", providerStr);
            throw new UniEatUnAuthorizationException();
        }

        if (loginType == SocialLoginType.KAKAO) {
            final String BEARER = OAuth2AccessToken.TokenType.BEARER.getValue();
            if (!BEARER.contains(form.getAccessToken())) {
                form.setAccessToken(BEARER + " " + form.getAccessToken());
            }
        }
        OAuth2Token result = memberSignInService.service(loginType, form);
        FooRunToken fooRunToken = result.getToken();

        Map<String, String> body = new LinkedHashMap<>();
        body.put(HttpHeaders.AUTHORIZATION, fooRunToken.getAccessToken());
        body.put(JwtProvider.REFRESH_TOKEN_HEADER_NAME, fooRunToken.getRefreshToken());

        return UniEatCommonResponse.success(body);
    }

    /* OAUTH 구현하면서 다르게 처리 */
    //@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    @Deprecated
    public ResponseEntity signUp(@Validated @RequestBody MemberSignUp form) {
        log.debug("try sign up: {}", form);
        try {
            memberSignUpService.service(form);
        } catch (Exception e) {
            throw new UniEatServerErrorException();
        }

        return UniEatCommonResponse.success();
    }

    @GetMapping("/test")
    public ResponseEntity testCase() {
        log.debug("CALL");
        return UniEatCommonResponse.success();
    }
}