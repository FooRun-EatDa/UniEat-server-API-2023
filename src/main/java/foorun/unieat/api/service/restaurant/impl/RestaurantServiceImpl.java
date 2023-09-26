package foorun.unieat.api.service.restaurant.impl;

import foorun.unieat.api.model.database.menu.entity.FoodMenuEntity;
import foorun.unieat.api.model.database.menu.repository.FoodMenuRepository;
import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;
import foorun.unieat.api.model.database.restaurant.repository.RestaurantRepository;
import foorun.unieat.api.service.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;

    @Override
    public List<RestaurantEntity> getRestaurantInArea(double latitude, double longitude, double distance) {
        return restaurantRepository.inArea(latitude, longitude, distance);
    }

    @Override
    public List<RestaurantEntity> getRestaurantByKeyword(String search) {
        return restaurantRepository.searchKeyword(search);
    }

    @Override
    public List<RestaurantEntity> getRestaurantByBookMark(String provider, String memberId) {
        return restaurantRepository.bookmark(provider, memberId);
    }

    @Override
    public List<FoodMenuEntity> getMenuByRestaurantId(Long restaurantId) {
        return foodMenuRepository.findByRestaurantId(restaurantId);
    }

    /*
    // 위도, 경도를 라디안 단위로 변환하는 메서드
    private double toRadians(double degree) {
        return degree * Math.PI / 180.0;
    }

    // Haversine 공식을 사용하여 두 지점 간의 거리를 계산하는 메서드
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 지구의 반지름 (단위: km)
        double earthRadius = 6371.0;

        return earthRadius * c;
    }

    // 입력 받은 좌표로 주변 범위의 음식점을 찾아주는 메소드
    @Override
    public Object getCoordinate(RestaurantEntity restaurantEntity) {

        double minLat = 90.0;  // 위도 최소값
        double maxLat = 0.0;   // 위도 최대값

        double minLon = 180.0; // 경도 최소값
        double maxLon = 0.0;   // 경도 최대값

        double maxKilometer = 30.0; // 입력받은 줌심점 좌표의 반경 (현재 30km)

        // 입력 받은 위도를 long타입으로 변환
        double latitude = Long.parseLong(restaurantEntity.getLatitude());

        // 입력 받은 경도를 long타입으로 변환
        double longitude = Long.parseLong(restaurantEntity.getLongitude());

        for (double lat = -90.0; lat <= 90.0; lat += 0.1) {
            for (double lon = -180.0; lon <= 180.0; lon += 0.1) {

                double distance = calculateDistance(latitude, longitude, lat, lon);

                if (distance <= maxKilometer) {

                    // minLat(위도 최소값)
                    if (minLat > lat) {
                        minLat = lat;
                    }

                    // maxLat(위도 최대값)
                    if (maxLat < lat) {
                        maxLat = lat;
                    }

                    // minLon (경도 최소값)
                    if (minLon > lon) {
                        minLon = lon;
                    }

                    // maxLon (경도 최대값)
                    if (maxLon < lon) {
                        maxLon = lon;
                    }
                }
            }
        }

        // 주변 식당 검색
        List<RestaurantEntity> resultRestaurant = entityManager.createQuery(
                        "select u from RestaurantEntity u where u.latitude between :minLat and :maxLat and u.longitude between :minLon and :maxLon", RestaurantEntity.class)
                .setParameter("minLat", minLat)
                .setParameter("maxLat", maxLat)
                .setParameter("minLon", minLon)
                .setParameter("maxLon", maxLon)
                .getResultList();

        return resultRestaurant;
    }

    @Override
    public RestaurantEntity newRestaurant(RestaurantEntity newRestaurant) {
        entityManager.persist(newRestaurant);
        return newRestaurant;
    }
*/

}