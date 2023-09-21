package foorun.unieat.api.service.restaurant;

import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RestaurantService {
    List<RestaurantEntity> getRestaurantInArea(double latitude, double longitude, double distance);
    List<RestaurantEntity> getRestaurantByKeyword(String search);
}