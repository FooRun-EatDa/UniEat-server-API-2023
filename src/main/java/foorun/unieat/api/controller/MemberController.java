package foorun.unieat.api.controller;

import foorun.unieat.api.model.domain.request.member.MemberSignIn;
import foorun.unieat.api.model.domain.request.member.MemberSignUp;
import foorun.unieat.api.service.member.MemberSignInService;
import foorun.unieat.api.service.member.MemberSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberSignUpService memberSignUpService;
    private final MemberSignInService memberSignInService;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity signIn(@RequestBody MemberSignIn form) {
        ResponseEntity response = memberSignInService.service(form);

        return response;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody MemberSignUp form) {
        ResponseEntity response = memberSignUpService.service(form);

        return response;
    }
}