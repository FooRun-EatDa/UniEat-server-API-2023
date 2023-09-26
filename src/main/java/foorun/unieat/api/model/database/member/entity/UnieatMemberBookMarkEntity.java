package foorun.unieat.api.model.database.member.entity;

import foorun.unieat.api.model.database.member.entity.primary_key.UniEatBookMarker;
import foorun.unieat.api.model.database.member.entity.primary_key.UniEatMemberId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * UNIEAT 북마크
 */
@Entity
@Table(name = "member_bookmark")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@IdClass(UniEatBookMarker.class)
public class UnieatMemberBookMarkEntity {
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
     * 식당 ID
     */
    @Id
    @Column(name = "restaurant_id", updatable = false)
    private Long restaurantId;
}