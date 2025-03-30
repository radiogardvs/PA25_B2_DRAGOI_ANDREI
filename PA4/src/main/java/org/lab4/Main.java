package org.lab4;
import com.github.javafaker.Faker;
import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();

        List<Location> locations = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            locations.add(new Location(faker.address().cityName(), LocationType.FRIENDLY));
        }

        for (int i = 0; i < 5; i++) {
            locations.add(new Location(faker.address().cityName(), LocationType.ENEMY));
        }

        for (int i = 0; i < 5; i++) {
            locations.add(new Location(faker.address().cityName(), LocationType.NEUTRAL));
        }

        Map<LocationType, List<Location>> groupedLocations = locations.stream()
                .collect(Collectors.groupingBy(Location::getType));

        System.out.println("Locations grouped by type:");
        groupedLocations.forEach((type, locs) -> {
            System.out.println(type + " locations:");
            locs.forEach(loc -> System.out.println("- " + loc.getName()));
        });

        System.out.println("\nFriendly locations (sorted by name):");
        locations.stream()
                .filter(loc -> loc.getType() == LocationType.FRIENDLY)
                .sorted(Comparator.comparing(Location::getName))
                .forEach(loc -> System.out.println("- " + loc.getName()));

        System.out.println("\nEnemy locations (sorted by type then by name):");
        locations.stream()
                .filter(loc -> loc.getType() == LocationType.ENEMY)
                .sorted(Comparator.comparing(Location::getType).thenComparing(Location::getName))
                .forEach(loc -> System.out.println("- " + loc.getName()));

        List<Route> routes = new ArrayList<>();
        Location start = locations.get(0);
        Location end = locations.get(1);

        Random rand= new Random();
        routes.add(new Route(start, end, rand.nextInt(), rand.nextDouble()));

        System.out.println("\nShortest path from " + start.getName() + " to " + end.getName() + ":");
        List<Location> path = RouteFinder.findShortestPath(start, end, createRoutesMap(locations, routes));
        path.forEach(loc -> System.out.println("- " + loc.getName()));
    }

    private static Map<Location, List<Route>> createRoutesMap(List<Location> locations, List<Route> routes) {
        Map<Location, List<Route>> routesMap = new HashMap<>();
        for (Route route : routes) {
            routesMap.computeIfAbsent(route.getFrom(), k -> new ArrayList<>()).add(route);
        }
        return routesMap;
    }
}
