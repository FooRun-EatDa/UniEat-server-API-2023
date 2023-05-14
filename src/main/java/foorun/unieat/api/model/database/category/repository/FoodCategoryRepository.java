package foorun.unieat.api.model.database.category.repository;

import foorun.unieat.api.model.database.category.entity.FoodCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepository extends JpaRepository<FoodCategoryEntity, String> {
}