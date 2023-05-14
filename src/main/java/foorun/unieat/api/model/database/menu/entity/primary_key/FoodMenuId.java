package foorun.unieat.api.model.database.menu.entity.primary_key;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class FoodMenuId implements Serializable {
    private Long restaurantId;
    private Long menuId;
}
