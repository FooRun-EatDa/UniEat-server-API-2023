package foorun.unieat.api.controller;

import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;
import foorun.unieat.api.service.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // 주변 식장 좌표를 뿌려주는 메소드
    @GetMapping("/getCoordinate")
    public Object getCoordinate(@RequestBody Map<String, Object> requestData) throws Exception {

        String latitude  = requestData.get("latitude").toString();
        String longitude = requestData.get("longitude").toString();

        RestaurantEntity restaurant = new RestaurantEntity(latitude, longitude);

        Object result;

        try {
            result = restaurantService.getCoordinate(restaurant);
        } catch (Exception e) {
            throw new Exception("getCoordinate error.");
        }

        return result;
    }

    @PostMapping("/newRestaurant")
    public Object newRestaurant(@RequestBody Map<String, Object> requestData) throws Exception {

        String restaurant_id  = requestData.get("restaurant_id").toString();     // 식당 ID
        String name           = requestData.get("name").toString();              // 식당명
        String explanation    = requestData.get("explanation").toString();       // 식당 대표이미지 sequence
        String img_url        = requestData.get("img_url").toString();           // 식당 한줄 소개
        String content        = requestData.get("content").toString();           // 식당 상세 설명
        String category_code  = requestData.get("category_code").toString();     // 식당 주소 기본
        String address        = requestData.get("address").toString();           // 식당 주소 상세
        String latitude       = requestData.get("latitude").toString();          // 식당위치 위도
        String longitude      = requestData.get("longitude").toString();         // 식당위치 경도
        String phone_number   = requestData.get("phone_number").toString();      // 식당 전화번호
        String operation_time = requestData.get("operation_time").toString();    // 식당 운영시간 설명
        String price          = requestData.get("price").toString();             // 식당 상태 관리
        String district       = requestData.get("district").toString();
        String created_at     = requestData.get("created_at").toString();
        String updated_at     = requestData.get("updated_at").toString();
        String status         = requestData.get("status").toString();
        String code_id        = requestData.get("code_id").toString();

        // restaurant 객체 생성(새로운 가게 저장용)
        RestaurantEntity newRestaurant = new RestaurantEntity(
                Long.parseLong(restaurant_id)
                , name
                , explanation
                , img_url
                , content
                , Integer.parseInt(category_code)
                , address
                , latitude
                , longitude
                , phone_number
                , operation_time
                , Long.parseLong(price)
                , district
                , Date.valueOf(created_at)
                , Date.valueOf(updated_at)
                , status
                , BigInteger.valueOf(Long.parseLong(code_id))
        );

        Map<String, String> result = new HashMap<>();

        // 신규 식당 저장
        try {
            restaurantService.newRestaurant(newRestaurant);
            result.put("result", "new restaurant registration success");
        } catch (Exception e){
            result.put("result", "new restaurant registration fail");
            throw new Exception("new restaurant registration fail");
        }

        return result;
    }

}
