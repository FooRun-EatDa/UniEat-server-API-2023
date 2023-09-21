package foorun.unieat.api.model.domain.restaurant.response;

import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;
import foorun.unieat.common.rules.ManagedStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Restaurant {
    /**
     * 식당 ID
     */
    private Long restaurant_id;

    /**
     * 사업자번호
     */
    private String business_number;

    /**
     * 식당명
     */
    private String restaurant_name;

    /**
     * 식당 대표이미지 sequence
     */
    private Integer restaurant_title_image_seq;

    /**
     * 식당 한줄 소개
     */
    private String restaurant_introduction;

    /**
     * 식당 상세 설명
     */
    private String restaurant_content;

    /**
     * 식당 주소 기본
     */
    private String restaurant_address_base;

    /**
     * 식당 주소 상세
     */
    private String restaurant_address_detail;

    /**
     * 식당위치 위도
     */
    private BigDecimal restaurant_latitude;

    /**
     * 식당위치 경도
     */
    private BigDecimal restaurant_longitude;

    /**
     * 식당 전화번호
     */
    private String restaurant_call_number;

    /**
     * 식당 운영시간 설명
     */
    private String restaurant_operation_time;

    /**
     * 식당 상태 관리
     */
    private ManagedStatusType manage_status;

    public static Restaurant of(RestaurantEntity entity) {
        return Restaurant.of(entity.getPrimaryId(),
                entity.getBusinessNumber(),
                entity.getName(),
                entity.getTitleImageSeq(),
                entity.getIntroduction(),
                entity.getContent(),
                entity.getAddressBase(),
                entity.getAddressDetail(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getCallNumber(),
                entity.getOperationTime(),
                entity.getStatus()
                );
    }
}