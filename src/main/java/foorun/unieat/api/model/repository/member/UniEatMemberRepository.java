package foorun.unieat.api.model.repository.member;

import foorun.unieat.api.model.entity.member.UniEatMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniEatMemberRepository extends JpaRepository<UniEatMemberEntity, Long> {
    Optional<UniEatMemberEntity> findByMemberId(Long memberId);
    Optional<UniEatMemberEntity> findByEmail(String email);
}