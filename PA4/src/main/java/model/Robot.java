package model;

import java.util.List;
import java.util.Map;

public class Robot {
    private Location currentLocation;
    private Map<Location, List<Route>> routes;

    public Robot(Location currentLocation, Map<Location, List<Route>> routes) {
        this.currentLocation = currentLocation;
        this.routes = routes;
    }

    public void moveTo(Location destination, boolean preferSafety) {
        // Aici implementăm logica pentru a alege drumul
        // folosind ori timpul, ori siguranța, în funcție de preferință
    }
}
