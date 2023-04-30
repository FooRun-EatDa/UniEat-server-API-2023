package foorun.unieat.api.model.database.email.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 이메일 인증 코드 정보
 */
@Getter
@Entity
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "email_verification_code")
public class EmailVerificationCodeEntity extends UniEatBaseTimeEntity {

    /**
     * 회원 이메일
     */
    @Id
    @Column(name = "member_email", unique = true)
    private String email;

    /**
     * 인증 코드
     */
    @Column(name = "verification_code")
    private String code;

    /**
     * 인증 코드 발급 일시
     */
    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * 인증 코드가 일치하는지 검사
     */
    public boolean isEqualsCode(String code) {
        return this.code.equals(code);
    }
}
