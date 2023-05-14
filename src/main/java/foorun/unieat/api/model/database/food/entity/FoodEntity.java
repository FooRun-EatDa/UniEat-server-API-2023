package foorun.unieat.api.model.database.food.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;
import foorun.unieat.common.rules.StatusType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 메뉴 정보
 */
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@DynamicUpdate
@Table(name="food")
@Deprecated
public class FoodEntity extends UniEatBaseTimeEntity {

    /**
     * 메뉴 ID
     */
    @Id
    @Column(name="food_id")
    private Long id;

    /**
     * 메뉴명
     */
    @Column(name="food_name")
    private String name;

    /**
     * 메뉴 음식 이미지
     */
    @Column(name="img_url")
    private String imgUrl;

    /**
     * 메뉴 가격
     */
    @Column(name="food_price")
    private int price;

    /**
     * 메뉴 상세설명
     */
    @Column(name="food_details")
    private String details;

    /**
     * 표출 순서
     */
    private int sequence;

    /**
     * 메뉴 상태값
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name="food_status")
    private StatusType status = StatusType.ACTIVE;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restaurant;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "food")
    private List<FoodFileEntity> files = new ArrayList<>();
}
