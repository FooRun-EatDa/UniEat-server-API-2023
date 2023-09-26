package foorun.unieat.api.model.database.restaurant.repository;

import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;
import foorun.unieat.api.model.database.restaurant.repository.custom.RestaurantRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long>, RestaurantRepositoryCustom {
}