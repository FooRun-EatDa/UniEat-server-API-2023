package foorun.unieat.api.model.domain.restaurant.response;

import foorun.unieat.api.model.base.dto.UniEatResponseDTO;
import foorun.unieat.api.model.database.restaurant.entity.RestaurantImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class RestaurantImage implements UniEatResponseDTO {
    private Integer seq;
    private String file_url;

    public static RestaurantImage of(RestaurantImageEntity entity) {
        return RestaurantImage.of(
                entity.getSequence(),
                entity.getFileUrl()
        );
    }
}