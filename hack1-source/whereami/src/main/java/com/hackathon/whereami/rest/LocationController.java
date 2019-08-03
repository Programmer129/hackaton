package com.hackathon.whereami.rest;

import com.hackathon.whereami.api.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("save")
    public ResponseEntity<Map<String, String>> saveAndDetectLocation(@RequestParam("latitude") double latitude,
                                                                    @RequestParam("longitude") double longitude) {
        Map<String, String> result = new HashMap<>();
        result.put("location", locationService.saveAndDetectLocation(latitude, longitude));
        return ResponseEntity.ok(result);
    }
}
