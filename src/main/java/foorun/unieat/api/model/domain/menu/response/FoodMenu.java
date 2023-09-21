package foorun.unieat.api.model.domain.menu.response;

import foorun.unieat.api.model.base.dto.UniEatResponseDTO;
import foorun.unieat.api.model.database.menu.entity.FoodMenuEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class FoodMenu implements UniEatResponseDTO {
    /**
     * 식당 ID
     */
    private Long restaurant_id;

    /**
     * 메뉴 ID
     */
    private Long menu_id;

    /**
     * 노출 순서
     */
    private Integer seq;

    /**
     * 메뉴 이름
     */
    private String menu_name;

    /**
     * 메뉴 이미지
     */
    private String menu_image_url;

    /**
     * 메뉴 가격(단위: 원)
     */
    private Long menu_price;

    /**
     * 메뉴 항목 ID
     */
    private String menu_category_id;

    /**
     * 메뉴 설명
     */
    private String menu_description;

    /**
     * 메뉴 추천 여부
     */
    private boolean menu_recommend;

    public static FoodMenu of(FoodMenuEntity entity) {
        return FoodMenu.of(entity.getRestaurantId(),
                entity.getMenuId(),
                entity.getSequence(),
                entity.getMenuName(),
                entity.getMenuImageUrl(),
                entity.getPrice(),
                entity.getCategoryId(),
                entity.getDescription(),
                entity.isRecommended()
                );
    }
}