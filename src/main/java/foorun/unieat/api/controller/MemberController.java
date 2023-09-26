package foorun.unieat.api.controller;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.exception.UniEatServerErrorException;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;
import foorun.unieat.api.model.database.restaurant.repository.RestaurantRepository;
import foorun.unieat.api.model.domain.UniEatCommonResponse;
import foorun.unieat.api.model.domain.member.request.MemberSignIn;
import foorun.unieat.api.model.domain.member.request.MemberSignOut;
import foorun.unieat.api.model.domain.member.request.OAuth2SignIn;
import foorun.unieat.api.model.domain.member.response.OAuth2Token;
import foorun.unieat.api.service.member.MemberSignInService;
import foorun.unieat.api.service.member.MemberSignOutService;
import foorun.unieat.common.exception.ResponseRuntimeException;
import foorun.unieat.common.http.FooRunToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    private final MemberSignInService memberSignInService;
    private final MemberSignOutService memberSignOutService;

    @RequestMapping(value = "/sign-in/{providerStr}", method = RequestMethod.POST)
    public ResponseEntity signInOAuth(@PathVariable String providerStr, @Validated @RequestBody OAuth2SignIn form) {
        MemberSignIn dto = MemberSignIn.of(providerStr, form.getAccessToken());
        OAuth2Token result = memberSignInService.service(dto);
        FooRunToken fooRunToken = result.getToken();

        Map<String, String> body = new LinkedHashMap<>();
        body.put(HttpHeaders.AUTHORIZATION, fooRunToken.getAccessToken());
        body.put(JwtProvider.REFRESH_TOKEN_HEADER_NAME, fooRunToken.getRefreshToken());

        return UniEatCommonResponse.success(body);
    }

    @RequestMapping(value = "/sign-out", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity signOut(@RequestHeader Map<String, String> headers) {
        try {
            MemberSignOut dto = MemberSignOut.of(headers.get(HttpHeaders.AUTHORIZATION.toLowerCase()), headers.get(JwtProvider.REFRESH_TOKEN_HEADER_NAME.toLowerCase()));
            if (dto.getAccessToken() == null || dto.getAccessToken().trim().isEmpty()) {
                throw new NullPointerException("Access Token is empty.");
            }
            if (dto.getRefreshToken() == null || dto.getRefreshToken().trim().isEmpty()) {
                throw new NullPointerException("Refresh Token is empty.");
            }

            memberSignOutService.service(dto);
        } catch (ResponseRuntimeException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("비정상적인 요청이 감지되었습니다. ({})", e.getMessage());
        }

        return UniEatCommonResponse.success();
    }
}