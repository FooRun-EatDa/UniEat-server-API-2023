package foorun.unieat.api.model.database.restaurant.entity;

import foorun.unieat.api.model.database.file.entity.BaseFileEntity;
import foorun.unieat.api.model.database.file.entity.ImageFileEntity;
import foorun.unieat.api.model.domain.JsonSerializable;
import lombok.*;

import javax.persistence.*;

/**
 * 식당 이미지 정보
 * 복합키 존재
 */
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@IdClass(RestaurantFileIdEntity.class)
@Table(name = "restaurant_file")
@Deprecated
public class RestaurantFileEntity extends BaseFileEntity implements JsonSerializable {

    @Id
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @Builder
    protected RestaurantFileEntity(boolean thumbnail, int sequence, RestaurantEntity restaurant, ImageFileEntity file) {
        super(thumbnail, sequence, file);
        this.restaurant = restaurant;
    }

    public static RestaurantFileEntity of(RestaurantEntity restaurant, ImageFileEntity file, boolean thumbnail, int sequence) {
        return RestaurantFileEntity.builder()
                .restaurant(restaurant)
                .file(file)
                .thumbnail(thumbnail)
                .sequence(sequence)
                .build();
    }
}
