package foorun.unieat.api.model.database.restaurant.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;
import foorun.unieat.api.model.database.restaurant.repository.custom.RestaurantRepositoryCustom;
import foorun.unieat.common.model.coordinate.MinMaxWithinDistance;
import foorun.unieat.common.util.CoordinateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static foorun.unieat.api.model.database.restaurant.entity.QRestaurantEntity.restaurantEntity;
import static foorun.unieat.api.model.database.member.entity.QUnieatMemberBookMarkEntity.unieatMemberBookMarkEntity;

@Slf4j
@RequiredArgsConstructor
public class RestaurantRepositoryCustomImpl implements RestaurantRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RestaurantEntity> inArea(Double latitude, Double longitude, Double distance) {
        MinMaxWithinDistance area = CoordinateUtil.getMinMaxByArea(latitude, longitude, distance);
        List<RestaurantEntity> resultList = jpaQueryFactory.selectFrom(restaurantEntity)
                .where(
                        restaurantEntity.latitude.between(area.getMin_latitude().doubleValue(), area.getMax_latitude().doubleValue()),
                        restaurantEntity.longitude.between(area.getMin_longitude().doubleValue(), area.getMax_longitude().doubleValue())
                ).fetch();

        return resultList;
    }

    @Override
    public List<RestaurantEntity> searchKeyword(String search) {
        List<RestaurantEntity> resultList = jpaQueryFactory.selectFrom(restaurantEntity)
                .where(
                        restaurantEntity.name.contains(search)
                ).fetch();

        return resultList;
    }

    @Override
    public List<RestaurantEntity> bookmark(String provider, String memberId) {
        List<RestaurantEntity> resultList = jpaQueryFactory.select(restaurantEntity)
                .from(unieatMemberBookMarkEntity, restaurantEntity)
                .where(unieatMemberBookMarkEntity.provider.eq(provider),
                        unieatMemberBookMarkEntity.primaryId.eq(memberId),
                        unieatMemberBookMarkEntity.restaurantId.eq(restaurantEntity.primaryId)
                ).fetch();

        return resultList;
    }
}