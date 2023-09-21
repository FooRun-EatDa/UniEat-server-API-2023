package foorun.unieat.common.util;

import foorun.unieat.common.model.coordinate.MinMaxWithinDistance;

public class CoordinateUtil {
    public static double EARTH_RADIUS = 6371.01; /* 지구 반지름 (단위: km) */

    public static MinMaxWithinDistance getMinMaxByArea(double latitude, double longitude, double distanceByKilometer) {
        MinMaxWithinDistance resultModel = new MinMaxWithinDistance();
        resultModel.setDistanceByKilometer(distanceByKilometer);

        double distanceForLat = (1 / (EARTH_RADIUS * (Math.PI / 180)));
        double distanceForLng = (1 / (EARTH_RADIUS * (Math.PI / 180) * Math.cos(Math.toRadians(latitude))));

        double minLat = latitude - (distanceByKilometer * distanceForLat);
        double minLng = longitude - (distanceByKilometer * distanceForLng);
        double maxLat = latitude + (distanceByKilometer * distanceForLat);
        double maxLng = longitude + (distanceByKilometer * distanceForLng);

        resultModel.setMin_latitude(minLat);
        resultModel.setMin_longitude(minLng);
        resultModel.setMax_latitude(maxLat);
        resultModel.setMax_longitude(maxLng);

        return resultModel;
    }

    public static Double calculateHaversineFormula(double latitude1, double longitude1, double latitude2, double longitude2) {
        double deltaLatitude = Math.toRadians(Math.abs(latitude2 - latitude1));
        double deltaLongitude = Math.toRadians(Math.abs(longitude2 - longitude1));

        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);
        double a = sinDeltaLat * sinDeltaLat + Math.cos(Math.toRadians(latitude2)) * Math.cos(Math.toRadians(latitude1)) * sinDeltaLng * sinDeltaLng;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * EARTH_RADIUS;
    }
}