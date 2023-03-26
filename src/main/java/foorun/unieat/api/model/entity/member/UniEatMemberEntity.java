package foorun.unieat.api.model.entity.member;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import foorun.unieat.common.rules.ManagedStatusType;
import foorun.unieat.common.rules.MemberRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

/**
 * UNIEAT 회원정보
 */
@Entity
@Table(name = "member_base")
@Validated
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class UniEatMemberEntity extends UniEatBaseTimeEntity implements UserDetails {

    /**
     * 회원 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", updatable = false)
    private Long pId;

    /**
     * 회원 인증 Email
     */
    @Column(name = "MEMBER_EMAIL", unique = true)
    @Email(message = "E-Mail 양식이 아닙니다.")
    private String email;

    /**
     * 회원 PASSWORD
     */
    @Column(name = "MEMBER_PASSWORD")
    private String password;

    /**
     * 최종 로그인 일시
     */
    @Column(name = "LAST_SIGN_IN_AT")
    private LocalDateTime lastSignInAt;

    /**
     * 계정 상태 관리
     */
    @Column(name = "MANAGE_STATUS", length = 10)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ManagedStatusType status = ManagedStatusType.ACTIVE;

    /**
     * 계정 권한 정보
     */
    @Column(name = "MEMBER_ROLE", length = 10)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberRole role = MemberRole.GUEST;

    /**
     * 계정 잠금 기간
     */
    @Column(name = "LOCKED_DATE")
    private LocalDateTime lockedDate;

    /**
     * 계정 만료 기간
     */
    @Column(name = "EXPIRED_DATE")
    @Builder.Default
    private LocalDateTime expiredDate = LocalDateTime.MAX;

    /**
     * 비밀번호 변경
     *
     * @param password 변경할 비밀번호
     */
    public void changePassword(String password) {
        this.password = password;
    }

    /**
     * 최종 로그인 일시 갱신
     */
    public void updateSignInNow() {
        this.lastSignInAt = LocalDateTime.now();
    }

    /**
     * 계정 잠금 갱신
     */
    public void updateLocked(LocalDateTime date) {
        lockedDate = date;
    }

    public void updateLocked(int year, int month, int day, int hour, int minute, int second) {
        lockedDate = LocalDateTime.of(year, month, day, hour, minute, second);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return LocalDateTime.now().isBefore(expiredDate);
    }

    @Override
    public boolean isAccountNonLocked() {
        return lockedDate == null || LocalDateTime.now().isAfter(lockedDate);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == ManagedStatusType.ACTIVE && isAccountNonExpired() && isAccountNonLocked() && isCredentialsNonExpired();
    }
}