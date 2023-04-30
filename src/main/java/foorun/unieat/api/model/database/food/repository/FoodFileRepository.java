package foorun.unieat.api.model.database.food.repository;

import foorun.unieat.api.model.database.food.entity.FoodFileEntity;
import foorun.unieat.api.model.database.food.entity.FoodFileIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodFileRepository extends JpaRepository<FoodFileEntity, FoodFileIdEntity> {

}
