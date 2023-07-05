package foorun.unieat.api.service.member;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.exception.UniEatUnAuthorizationException;
import foorun.unieat.api.model.base.dto.UniEatResponseDTO;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.api.model.domain.member.request.MemberSignOut;
import foorun.unieat.api.service.UniEatCommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSignOutService implements UniEatCommonService<MemberSignOut> {
    private final JwtProvider jwtProvider;

    private final UniEatMemberRepository memberRepository;

    @Override
    public UniEatResponseDTO service(MemberSignOut form) {
        if (!jwtProvider.verifyToken(form.getAccessToken())) {
            throw new UniEatUnAuthorizationException();
        }
        if (!jwtProvider.verifyToken(form.getRefreshToken())) {
            throw new UniEatUnAuthorizationException();
        }

        UniEatMemberEntity member = jwtProvider.resolveToken(form.getRefreshToken(), memberRepository);
        if (member == null || !member.isEnabled()) {
            throw new UniEatForbiddenException();
        }

        member.setRefreshToken(null);
        memberRepository.save(member);

        return null;
    }
}