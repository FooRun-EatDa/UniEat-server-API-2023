package foorun.unieat.api.model.database.restaurant.entity.primary_key;

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
public class FoodCategory implements Serializable {
    private Long primaryId;
    private String categoryId;
}