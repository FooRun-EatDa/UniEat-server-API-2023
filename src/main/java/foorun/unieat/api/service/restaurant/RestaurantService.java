package foorun.unieat.api.service.restaurant;

import foorun.unieat.api.model.database.menu.entity.FoodMenuEntity;
import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantService {
    List<RestaurantEntity> getRestaurantInArea(double latitude, double longitude, double distance);
    List<RestaurantEntity> getRestaurantByKeyword(String search);
    List<RestaurantEntity> getRestaurantByBookMark(String provider, String memberId);
    List<FoodMenuEntity> getMenuByRestaurantId(Long restaurantId);
}