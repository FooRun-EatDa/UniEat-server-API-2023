package foorun.unieat.api.model.database.member.repository;

import foorun.unieat.api.model.database.member.entity.UniEatMemberAuthEntity;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.entity.clazz.UniEatMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniEatMemberAuthRepository extends JpaRepository<UniEatMemberAuthEntity, UniEatMemberId> {
}