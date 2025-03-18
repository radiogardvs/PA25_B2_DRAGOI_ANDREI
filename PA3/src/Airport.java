import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class Airport {
    private List<List<Flight>> runways;

    public Airport(int numberOfRunways) {
        this.runways = new ArrayList<>();
        for (int i = 0; i < numberOfRunways; i++) {
            runways.add(new ArrayList<>());
        }
    }

    public boolean assignFlight(Flight flight) {
        runways.sort(Comparator.comparingInt(List::size));
        for (List<Flight> runway : runways) {
            if (runway.stream().noneMatch(f -> f.conflictsWith(flight))) {
                runway.add(flight);
                return true;
            }
        }
        return false;
    }

    public int getMinRunwaysRequired(List<Flight> flights) {
        flights.sort(Comparator.comparing(Flight::getLandingStart));
        PriorityQueue<LocalTime> minHeap = new PriorityQueue<>();

        for (Flight flight : flights) {
            while (!minHeap.isEmpty() && !minHeap.peek().isAfter(flight.getLandingStart())) {
                minHeap.poll();
            }
            minHeap.add(flight.getLandingEnd());
        }
        return minHeap.size();
    }

    public void printSchedule() {
        for (int i = 0; i < runways.size(); i++) {
            System.out.println("Runway " + (i + 1) + ":");
            for (Flight f : runways.get(i)) {
                System.out.println("  Flight " + f.getFlightId() + " - " + f.getAircraft().getName());
            }
        }
    }
}
