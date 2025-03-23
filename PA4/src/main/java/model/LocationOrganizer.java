package model;
import java.util.*;
import java.util.stream.Collectors;

public class LocationOrganizer {
    public static Map<LocationType, List<Location>> groupLocationsByType(List<Location> locations) {
        return locations.stream()
                .collect(Collectors.groupingBy(Location::getType));
    }
}
