package com.wayzor.wayzor_backend.util;

import java.util.Map;

public class StationUtil {
    private static final Map<String, String> cityToStationMap = Map.of(
            "Shimla", "SML",
            "Delhi", "NDLS",
            "Mumbai", "CSTM",
            "Jaipur", "JP",
            "Goa", "MAO"
    );

    public static String getStationCode(String city) {
        return cityToStationMap.getOrDefault(city, "NDLS"); // fallback
    }
}
