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
import java.math.BigInteger;
import java.util.*;

/**
 * 식당 기본정보
 */
@Entity
@Table(name = "restaurant")
@Getter
public class RestaurantEntity extends UniEatBaseTimeEntity {
//    /**
//     * 식당 ID
//     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "restaurant_id", updatable = false)
//    private Long primaryId;
//
//    /**
//     * 식당명
//     */
//    @Column(name = "name", length = 60)
//    private String name;
//
//    /**
//     * 식당 대표이미지 sequence
//     */
//    @Column(name = "explanation")
//    private String explanation;
//
//    /**
//     * 식당 한줄 소개
//     */
//    @Column(name = "img_url", length = 100)
//    private String img_url;
//
//    /**
//     * 식당 상세 설명
//     */
//    @Column(name = "restaurant_content", length = 500)
//    private String content;
//
//    /**
//     * 식당 주소 기본
//     */
//    @Column(name = "restaurant_address_base", length = 100)
//    private String addressBase;
//
//    /**
//     * 식당 주소 상세
//     */
//    @Column(name = "restaurant_address_detail", length = 100)
//    private String addressDetail;
//
//    /**
//     * 식당위치 위도
//     */
//    @Column(name = "restaurant_latitude")
//    private String latitude;
//
//    /**
//     * 식당위치 경도
//     */
//    @Column(name = "restaurant_longitude")
//    private String longitude;
//
//    /**
//     * 식당 전화번호
//     */
//    @Column(name = "restaurant_call_number", length = 11)
//    private String callNumber;
//
//    /**
//     * 식당 운영시간 설명
//     */
//    @Column(name = "restaurant_operation_time", length = 300)
//    private String operationTime;

    @Id
    private long restaurant_id;
    private String name;
    private String explanation;
    private String img_url;
    private String content;
    private Integer category_code;
    private String address;
    private String latitude;
    private String longitude;
    private String phone_number;
    private String operation_time;
    private long price;
    private String district;
    private Date created_at;
    private Date updated_at;
    private String status;

    @Column(name = "code_id")
    private BigInteger code_id;

    protected RestaurantEntity() {
    }

    // 식당 정보 저장
    public RestaurantEntity(long restaurant_id, String name, String explanation, String img_url, String content, Integer category_code, String address, String latitude, String longitude, String phone_number, String operation_time, long price, String district, Date created_at, Date updated_at, String status, BigInteger code_id) {
        this.restaurant_id = restaurant_id;
        this.name = name;
        this.explanation = explanation;
        this.img_url = img_url;
        this.content = content;
        this.category_code = category_code;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone_number = phone_number;
        this.operation_time = operation_time;
        this.price = price;
        this.district = district;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.code_id = code_id;
    }

    // 위도 경도 조회
    public RestaurantEntity(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
