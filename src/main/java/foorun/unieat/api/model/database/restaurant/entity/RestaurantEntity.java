package foorun.unieat.api.model.database.restaurant.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import foorun.unieat.api.model.database.menu.entity.FoodMenuEntity;
import foorun.unieat.common.rules.ManagedStatusType;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * 식당 기본정보
 */
@Entity
@Table(name = "restaurant_base")
@Getter
public class RestaurantEntity extends UniEatBaseTimeEntity {
    /**
     * 식당 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id", updatable = false)
    private Long primaryId;

    /**
     * 사업자번호
     */
    @Column(name = "business_number", length = 10)
    private String businessNumber;

    /**
     * 식당명
     */
    @Column(name = "restaurant_name", length = 200)
    private String name;

    /**
     * 식당 대표이미지 sequence
     */
    @ToString.Exclude
    @Column(name = "restaurant_title_image_seq")
    private Integer titleImageSeq;

    /**
     * 식당 한줄 소개
     */
    @Column(name = "restaurant_introduction", length = 100)
    private String introduction;

    /**
     * 식당 상세 설명
     */
    @Column(name = "restaurant_content", length = 500)
    private String content;

    /**
     * 식당 주소 기본
     */
    @Column(name = "restaurant_address_base", length = 100)
    private String addressBase;

    /**
     * 식당 주소 상세
     */
    @Column(name = "restaurant_address_detail", length = 100)
    private String addressDetail;

    /**
     * 식당위치 위도
     */
    @Column(name = "restaurant_latitude", precision = 13, scale = 10)
    private BigDecimal latitude;

    /**
     * 식당위치 경도
     */
    @Column(name = "restaurant_longitude", precision = 13, scale = 10)
    private BigDecimal longitude;

    /**
     * 식당 전화번호
     */
    @Column(name = "restaurant_call_number", length = 11)
    private String callNumber;

    /**
     * 식당 운영시간 설명
     */
    @Column(name = "restaurant_operation_time", length = 300)
    private String operationTime;

    /**
     * 식당 상태 관리
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "manage_status", length = 10)
    private ManagedStatusType status = ManagedStatusType.ACTIVE;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    private Collection<RestaurantFoodCategoryEntity> foodCategories = new HashSet<>();

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    private Collection<RestaurantImageEntity> restaurantImages = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    private Collection<FoodMenuEntity> foodMenu = new HashSet<>();
}