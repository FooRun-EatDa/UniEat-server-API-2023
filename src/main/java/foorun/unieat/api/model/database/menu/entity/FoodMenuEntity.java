package foorun.unieat.api.model.database.menu.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import foorun.unieat.api.model.database.category.entity.FoodCategoryEntity;
import foorun.unieat.api.model.database.menu.entity.primary_key.FoodMenuId;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 음식 메뉴 기본정보
 */
@Entity
@Table(name = "menu_base")
@Getter
@IdClass(FoodMenuId.class)
public class FoodMenuEntity extends UniEatBaseTimeEntity {
    /**
     * 식당 ID
     */
    @Id
    @Column(name = "restaurant_id")
    private Long restaurantId;

    /**
     * 메뉴 ID
     */
    @Id
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 노출 순서
     */
    @Column(name = "seq")
    private Integer sequence;

    /**
     * 메뉴 이름
     */
    @Column(name = "menu_name", length = 50)
    private String menuName;

    /**
     * 메뉴 이미지
     */
    @Column(name = "menu_image_url", length = 500)
    private String menuImageUrl;

    /**
     * 메뉴 가격(단위: 원)
     */
    @Column(name = "menu_price")
    private Long price;

    /**
     * 메뉴 항목 ID
     */
    @Column(name = "menu_category_id", length = 3, insertable = false, updatable = false)
    private String categoryId;

    /**
     * 메뉴 추천 여부
     */
    @Column(name = "menu_recommend")
    private boolean recommended;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id", referencedColumnName = "category_id")
    private FoodCategoryEntity foodCategory;
}