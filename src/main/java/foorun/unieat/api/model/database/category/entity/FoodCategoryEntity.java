package foorun.unieat.api.model.database.category.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 음식 분류항목
 */
@Entity
@Table(name = "category_food")
@Getter
public class FoodCategoryEntity extends UniEatBaseEntity {
    /**
     * 분류 ID
     */
    @Id
    @Column(name = "category_id", length = 3, updatable = false)
    private String primaryId;

    /**
     * 항목 이름
     */
    @Column(name = "category_name", length = 50)
    private String name;
}