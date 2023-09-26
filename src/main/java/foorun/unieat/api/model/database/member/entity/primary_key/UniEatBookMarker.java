package foorun.unieat.api.model.database.member.entity.primary_key;

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
public class UniEatBookMarker implements Serializable {
    private String provider;
    private String primaryId;
    private Long restaurantId;
}
