package foorun.unieat.api.model.database.member.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import foorun.unieat.api.model.database.member.entity.clazz.UniEatMemberId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "member_auth")
@Validated
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@IdClass(UniEatMemberId.class)
@DynamicUpdate
public class UniEatMemberAuthEntity extends UniEatBaseTimeEntity {
    /**
     * 회원 접속경로
     */
    @Id
    @Column(name = "member_provider", updatable = false)
    private String provider;

    /**
     * 회원 ID
     */
    @Id
    @Column(name = "member_id", updatable = false)
    private String primaryId;

    /**
     * 회원 REFRESH TOKEN 발급
     */
    @Setter
    @Column(name = "refresh_token")
    private String refreshToken;

    /**
     * UNIEAT 회원정보
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "member_provider", referencedColumnName = "member_provider"),
            @JoinColumn(name = "member_id", referencedColumnName = "member_id")
        }
    )
    private UniEatMemberEntity memberBase;
}