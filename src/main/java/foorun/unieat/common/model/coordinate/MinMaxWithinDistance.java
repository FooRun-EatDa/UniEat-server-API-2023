package foorun.unieat.common.model.coordinate;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MinMaxWithinDistance implements Serializable {
    private Number min_latitude;
    private Number max_latitude;

    private Number min_longitude;
    private Number max_longitude;

    private Number distanceByKilometer;
}