package com.coha.review.api;

import com.coha.review.api.request.CreateAndEditRestaurantRequest;
import com.coha.review.api.response.RestaurantDetailView;
import com.coha.review.api.response.RestaurantView;
import com.coha.review.model.RestaurantEntity;
import com.coha.review.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantApi {
    private final RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<RestaurantView> getRestaurants() {
        return restaurantService.getAllRestaurants();
    }
    @GetMapping("/restaurant/{restaurantID}")
    public RestaurantDetailView getRestaurant(@PathVariable Long restaurantID) {
        return restaurantService.getRestaurantDetail(restaurantID);
    }

    @PostMapping("/restaurant")
    public void createRestaurant(@RequestBody CreateAndEditRestaurantRequest request) {
        restaurantService.createRestaurant(request);
    }

    @PutMapping("/restaurant/{restaurantId}")
    public void editRestaurant(@PathVariable Long restaurantId, @RequestBody CreateAndEditRestaurantRequest request) {
        restaurantService.editRestaurant(restaurantId, request);
    }

    @DeleteMapping("/restaurant/{restaurantId}")
    public void deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
    }
}