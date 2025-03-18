package org.lab4;

import model.Location;
import model.LocationType;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Location> locations = Arrays.asList(
                new Location("Base Alpha", LocationType.FRIENDLY),
                new Location("Outpost Bravo", LocationType.FRIENDLY),
                new Location("Zone X", LocationType.NEUTRAL),
                new Location("Enemy Camp 1", LocationType.ENEMY),
                new Location("Enemy Camp 2", LocationType.ENEMY),
                new Location("Friendly Outpost", LocationType.FRIENDLY),
                new Location("Neutral Zone 2", LocationType.NEUTRAL),
                new Location("Enemy Hideout", LocationType.ENEMY)
        );

        TreeSet<Location> friendlyLocations = locations.stream()
                .filter(l -> l.getType() == LocationType.FRIENDLY)
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println("Friendly locations (sorted):");
        friendlyLocations.forEach(System.out::println);

        LinkedList<Location> enemyLocations = locations.stream()
                .filter(l -> l.getType() == LocationType.ENEMY)
                .sorted(Comparator.comparing(Location::getType).thenComparing(Location::getName))
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println("Enemy locations (sorted by type, then name):");
        enemyLocations.forEach(System.out::println);
    }
}
