package foorun.unieat.api.model.database.restaurant.repository.custom;

import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantRepositoryCustom {
    List<RestaurantEntity> inArea(Double latitude, Double longitude, Double distance);
    List<RestaurantEntity> searchKeyword(String search);
}