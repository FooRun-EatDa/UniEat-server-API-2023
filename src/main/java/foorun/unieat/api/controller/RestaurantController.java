package foorun.unieat.api.controller;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.model.domain.UniEatCommonResponse;
import foorun.unieat.api.model.domain.member.request.MemberSignIn;
import foorun.unieat.api.model.domain.member.request.OAuth2SignIn;
import foorun.unieat.api.model.domain.member.response.OAuth2Token;
import foorun.unieat.common.http.FooRunToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

}
