package foorun.unieat.api.model.database.email.repository;

import foorun.unieat.api.model.database.email.entity.EmailVerificationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCodeEntity, String> {
}
