package com.tekup.carpool_backend.service.admin;

import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.response.StatResponse;

import java.util.Map;

public interface StatisticsService {
    Long  getTotalPassenger();
    Long getTotalDrivers();

    Map<Object, Long> getTotalRidesPerYear();

    Map<Object, Long> getTotalRidesPerDay();

    StatResponse getAllStatistics();

    User getDriverWithMostRides();

    User getPassengerWithMostRides();


}
