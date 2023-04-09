package foorun.unieat.api.controller;

import foorun.unieat.api.model.domain.member.request.MemberSignIn;
import foorun.unieat.api.model.domain.member.request.MemberSignInByKakao;
import foorun.unieat.api.model.domain.member.request.MemberSignUp;
import foorun.unieat.api.service.member.MemberSignInService;
import foorun.unieat.api.service.member.MemberSignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberSignUpService memberSignUpService;
    private final MemberSignInService memberSignInService;

    @Deprecated
    //@RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity signIn(@Validated @RequestBody MemberSignIn form) {
        log.debug("try sign in: {}", form);
        ResponseEntity response = memberSignInService.service(form);

        return response;
    }

    @RequestMapping(value = "/sign-in/kakao", method = RequestMethod.POST)
    public ResponseEntity signIn(@Validated @RequestBody MemberSignInByKakao form) {
        log.debug("try sign in: {}", form);
        ResponseEntity response = memberSignInService.service(form);

        return response;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity signUp(@Validated @RequestBody MemberSignUp form) {
        log.debug("try sign up: {}", form);
        ResponseEntity response = memberSignUpService.service(form);

        return response;
    }
}