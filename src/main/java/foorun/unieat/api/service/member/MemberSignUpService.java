package foorun.unieat.api.service.member;

import foorun.unieat.api.exception.UniEatBadRequestException;
import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.model.base.dto.UniEatResponseDTO;
import foorun.unieat.api.model.domain.member.request.MemberSignUp;
import foorun.unieat.api.model.domain.UniEatCommonResponse;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.api.service.UniEatCommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Cacheable;

@Slf4j
@Service
@RequiredArgsConstructor
@Cacheable
public class MemberSignUpService implements UniEatCommonService<MemberSignUp> {
    private final UniEatMemberRepository memberRepository;

    @Override
    public UniEatResponseDTO service(MemberSignUp form) {
        if (!form.getPassword().equals(form.getPasswordRe())) {
            throw new UniEatBadRequestException();
        }

        UniEatMemberEntity newMember = UniEatMemberEntity.builder()
                .password(form.getPassword())
                .build();

        memberRepository.save(newMember);

        return null;
    }
}