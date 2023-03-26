package foorun.unieat.api.service.member;

import foorun.unieat.api.exception.UniEatBadRequestException;
import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.model.domain.request.member.MemberSignUp;
import foorun.unieat.api.model.domain.response.UniEatCommonResponse;
import foorun.unieat.api.model.entity.member.UniEatMemberEntity;
import foorun.unieat.api.model.repository.member.UniEatMemberRepository;
import foorun.unieat.api.service.UniEatCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberSignUpService extends UniEatCommonService<MemberSignUp> {
    private final UniEatMemberRepository memberRepository;

    @Override
    public ResponseEntity service(MemberSignUp form) {
        if (!form.getPassword().equals(form.getPasswordRe())) {
            throw new UniEatBadRequestException();
        }

        if (existsEmail(form.getEmail()) || existsNickname(form.getNickname())) {
            throw new UniEatForbiddenException();
        }

        /* TODO: 학교 인증 관련 처리 ? */

        UniEatMemberEntity newMember = UniEatMemberEntity.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .build();

        memberRepository.save(newMember);

        return UniEatCommonResponse.success();
    }

    @Transactional(readOnly = true)
    public boolean existsEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean existsNickname(String nickname) {
        /* TODO: 회원 데이터 모델에 별칭 컬럼과 변수 추가할 것 */
        return false;
    }
}