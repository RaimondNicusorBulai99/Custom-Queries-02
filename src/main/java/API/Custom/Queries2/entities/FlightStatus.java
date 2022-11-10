package API.Custom.Queries2.entities;

import java.util.Random;

public enum FlightStatus {
    ON_TIME,
    DELAYED,
    CANCEllED;

    private static final Random random = new Random();
    public static FlightStatus randomStatus(){
        FlightStatus[] flightStatuses = values();
        return flightStatuses[random.nextInt(flightStatuses.length)];
    }
}