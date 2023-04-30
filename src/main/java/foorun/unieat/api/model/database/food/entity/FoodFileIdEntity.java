package foorun.unieat.api.model.database.food.entity;

import foorun.unieat.api.model.domain.JsonSerializable;
import lombok.*;

import javax.persistence.Id;

/**
 * 메뉴와 이미지파일 매핑 정보
 * foodId, fileId 각각의 primary key를 복합키 형태로 갖고 있음
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodFileIdEntity implements JsonSerializable {
    private Long food;    // pk
    private String file;  // pk

    public static FoodFileIdEntity of(Long foodId, String fileId) {
        return new FoodFileIdEntity(foodId, fileId);
    }
}
