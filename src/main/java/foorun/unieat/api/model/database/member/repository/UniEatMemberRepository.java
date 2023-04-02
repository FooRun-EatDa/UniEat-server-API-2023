package foorun.unieat.api.model.database.member.repository;

import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniEatMemberRepository extends JpaRepository<UniEatMemberEntity, Long> {
    boolean existsByEmail(String email);
    Optional<UniEatMemberEntity> findByEmail(String email);
}