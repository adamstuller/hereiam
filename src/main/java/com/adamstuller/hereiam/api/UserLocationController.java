package com.adamstuller.hereiam.api;

import com.adamstuller.hereiam.models.UserLocation;
import com.adamstuller.hereiam.service.UserLocationService;
import com.vividsolutions.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/userLocation")
@RestController
public class UserLocationController {

    private final UserLocationService userLocationService;
    Logger logger = LoggerFactory.getLogger(UserLocationController.class);

    @Autowired
    public UserLocationController(UserLocationService userLocationService) {
        this.userLocationService = userLocationService;
    }

    @PostMapping
    public void addUserLocation(@Validated @RequestBody UserLocation userLocation){
        userLocationService.addUserLocation(userLocation);
    }

    @GetMapping
    public List<String> getAllUserLocations(){
        List<UserLocation> userLocations =  userLocationService.getAllUserLocation();
        return userLocations.stream().map(userLocation -> userLocation.toString()).collect(Collectors.toList());
    }

    @GetMapping( path = "{token}")
    public UserLocation getByToken(@PathVariable("token") String token){
        return userLocationService.getByToken(token);
    }

    @DeleteMapping(path = "{token}")
    public void deleteUserLocation(@PathVariable("token") String token){
        userLocationService.deleteUserLocation(token);
    }

    @PutMapping(path = "{token}")
    public int updateUserLocation(
            @PathVariable("token") String token,
            @RequestBody UserLocation userLocation
    ){
        return userLocationService.updateUserLocation(token, userLocation);
    }

    @PostMapping(path = "/radius")
    public List<Point> getPointsWithinRadius(
            @RequestParam Integer radius,
            @RequestBody UserLocation center
    ) {
        return userLocationService.getPointsWithinRadius(center, radius);
    }


}
