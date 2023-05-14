package foorun.unieat.api.model.database.member.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import foorun.unieat.api.model.database.member.entity.primary_key.UniEatMemberId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


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
@DynamicUpdate
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
    @Column(name = "member_nickname", unique = true, length = 50)
    private String nickname;

    /**
     * 회원 프로필사진
     */
    @Column(name = "member_profile_image", length = 500)
    private String profileImagePath;

    /**
     * 자기소개 문구
     */
    @Column(name = "member_introduction", length = 50)
    private String introduce;

    public UniEatMemberMyPageEntity changeNickname(@NotBlank String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UniEatMemberMyPageEntity changeProfileImage(MultipartFile file) {
        /* todo: image 파일 보관 후, 해당 경로 URL 가져오기 및 데이터 바꿔치기 구현 */
        this.profileImagePath = file.getOriginalFilename();
        return this;
    }

    public UniEatMemberMyPageEntity changeIntroduction(String introduce) {
        this.introduce = introduce;
        return this;
    }
}