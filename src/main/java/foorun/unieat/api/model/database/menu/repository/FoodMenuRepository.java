package foorun.unieat.api.model.database.menu.repository;

import foorun.unieat.api.model.database.menu.entity.FoodMenuEntity;
import foorun.unieat.api.model.database.menu.entity.primary_key.FoodMenuId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodMenuRepository extends JpaRepository<FoodMenuEntity, FoodMenuId> {
    List<FoodMenuEntity> findByRestaurantId(Long restaurantId);
}