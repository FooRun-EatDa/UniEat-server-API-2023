package foorun.unieat.api.model.database.restaurant.entity;

import foorun.unieat.api.model.domain.JsonSerializable;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Deprecated
public class RestaurantFileIdEntity implements JsonSerializable {
    private Long restaurant;
    private String file;

    public static RestaurantFileIdEntity of(Long restaurantId, String fileId) {
        return new RestaurantFileIdEntity(restaurantId, fileId);
    }
}
