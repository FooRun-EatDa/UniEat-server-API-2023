package foorun.unieat.api.model.database.member.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

/**
 * 회원 마이페이지 정보
 */
@Entity
@Table(name="member_mypage")
@Validated
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class UniEatMemberMyPageEntity extends UniEatBaseTimeEntity {

    /**
     * 마이페이지 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mypage_id")
    private Long id;

    /**
     * 회원 닉네임
     */
    @Column(name="member_nickname", unique = true)
    private String nickname;

    /**
     * 회원 프로필사진
     */
    @Column(name="member_profileImage")
    private String profileImage;

    @OneToOne(mappedBy = "MemberMyPageEntity")
    private UniEatMemberEntity member;

    /* 작성한 아티클 FK*/

}
