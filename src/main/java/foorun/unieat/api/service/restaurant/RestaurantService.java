package foorun.unieat.api.service.restaurant;

import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface RestaurantService {

    Object getCoordinate(RestaurantEntity restaurantEntity);

    RestaurantEntity newRestaurant(RestaurantEntity restaurantEntity);
}
