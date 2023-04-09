package foorun.unieat.api.service.member;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.model.domain.member.request.MemberSignIn;
import foorun.unieat.api.model.domain.UniEatCommonResponse;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.service.UniEatCommonService;
import foorun.unieat.common.rules.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSignInService implements UniEatCommonService<MemberSignIn>, UserDetailsService {
    private final JwtProvider jwtProvider;
    private final UniEatMemberRepository memberRepository;

    @Override
    public ResponseEntity service(MemberSignIn form) {
        UniEatMemberEntity member = loadUserByUsername(form.getPrimaryId());
        if (false /* todo 비밀번호 확인 조건 */) {
            throw new UniEatForbiddenException();
        }

        if (!member.isEnabled()) {
            throw new UniEatForbiddenException();
        }

        member.updateSignInNow();

        /* todo token 인증 및 생성 구현 */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, jwtProvider.createToken(member.getPrimaryId()));
        HttpHeaders httpHeaders = HttpHeaders.readOnlyHttpHeaders(headers);

        return UniEatCommonResponse.success(httpHeaders);
    }

    @Override
    @Transactional(readOnly = true)
    public UniEatMemberEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findById(username).orElseThrow(UniEatForbiddenException::new);
    }
}