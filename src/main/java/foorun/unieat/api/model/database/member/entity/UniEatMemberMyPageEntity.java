package foorun.unieat.api.model.database.member.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import foorun.unieat.api.model.database.member.entity.clazz.UniEatMemberId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


/**
 * 회원 마이페이지 정보
 */
@Entity
@Table(name = "member_mypage")
@Validated
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@IdClass(UniEatMemberId.class)
public class UniEatMemberMyPageEntity extends UniEatBaseTimeEntity {
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
     * 회원 닉네임
     */
    @Column(name="member_nickname", unique = true)
    private String nickname;

    /**
     * 회원 프로필사진
     */
    @Column(name="member_profile_image")
    private String profileImagePath;
}