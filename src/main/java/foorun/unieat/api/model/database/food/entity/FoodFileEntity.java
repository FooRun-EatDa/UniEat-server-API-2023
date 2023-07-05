package foorun.unieat.api.model.database.food.entity;

import foorun.unieat.api.model.database.file.entity.BaseFileEntity;
import foorun.unieat.api.model.database.file.entity.ImageFileEntity;
import foorun.unieat.api.model.domain.JsonSerializable;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 음식 이미지파일 정보
 * 복합키가 존재
 */
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@DynamicUpdate
@IdClass(FoodFileIdEntity.class)
@Table(name="food_file")
@Deprecated
public class FoodFileEntity extends BaseFileEntity implements JsonSerializable {
    @Id
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="food_id")
    private FoodEntity food;

    @Builder
    protected FoodFileEntity(boolean thumbnail, int sequence, FoodEntity food, ImageFileEntity file) {
        super(thumbnail, sequence, file);
        this.food = food;
    }

    public static FoodFileEntity of(FoodEntity food, ImageFileEntity file, boolean thumbnail, int sequence) {
        return FoodFileEntity.builder()
                .food(food)
                .file(file)
                .thumbnail(thumbnail)
                .sequence(sequence)
                .build();
    }
}
