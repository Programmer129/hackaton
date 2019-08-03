package com.hackathon.whereami.api;

public interface LocationService {
    String saveAndDetectLocation(double latitude, double longitude);
}
