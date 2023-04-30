package foorun.unieat.api.model.database.restaurant.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import foorun.unieat.api.model.database.food.entity.FoodEntity;
import foorun.unieat.common.rules.StatusType;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "restaurant")
public class RestaurantEntity extends UniEatBaseTimeEntity {

    /**
     * 식당 ID
     */
    @Id
    @Column(name = "restaurant_id")
    private Long id;

    /**
     * 식당명
     */
    @Column(name = "restaurant_name")
    private String name;

    /**
     * 식당 한줄 소개
     */
    @Column(name = "restaurant_introduction")
    private String introduction;

    /**
     * 식당 이미지
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 식당 상세 설명
     */
    @Column(name = "restaurant_content")
    private String content;

    /**
     * 식당 카테고리
     */
    @Column(name = "category_code")
    private Integer category;

    /**
     * 식당 주소
     */
    private String address;

    /**
     * 식당 위치 (학생들이 부르는 지역 기준)
     */
    private String district;

    /**
     * 경도
     */
    private Double longitude;

    /**
     * 위도
     */
    private Double latitude;

    /**
     * 식당 전화번호
     */
    @Column(name = "phone_number")
    private String phoneNunmber;

    /**
     * 식당 운영시간
     */
    @Column(name = "operation_time")
    private String operationTime;

    /**
     * 식당 대표 음식가격
     */
    private int price;

    /**
     * 식당 상태값
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.ACTIVE;

    @ToString.Exclude
    @Builder.Default
    @OneToMany
    @JoinColumn(name="restaurant_id")
    private Set<FoodEntity> foods = new HashSet<>();

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "restaurant")
    private Set<RestaurantFileEntity> files = new HashSet<>();

    /* TODO: 식당의 행정적 위치 정보, 카테고리정보 포함 */
}
