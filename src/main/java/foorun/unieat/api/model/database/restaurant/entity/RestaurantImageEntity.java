package foorun.unieat.api.model.database.restaurant.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 식당 이미지 관리
 */
@Entity
@Table(name = "restaurant_image")
@Getter
public class RestaurantImageEntity extends UniEatBaseTimeEntity {
    /**
     * 식당 ID
     */
    @Id
    @Column(name = "restaurant_id", updatable = false)
    private Long primaryId;

    /**
     * 이미지 순서
     */
    @Column(name = "seq")
    private Integer sequence;

    /**
     * 파일 경로
     */
    @Column(name = "file_url", length = 500)
    private String fileUrl;
}