import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Aircraft> aircraftList = new ArrayList<>();

        Airliner airliner1 = new Airliner("Boeing 737", "737-800", "N12345", 180);
        Airliner airliner2 = new Airliner("Airbus A320", "A320-200", "N67890", 160);
        Freighter freighter1 = new Freighter("CargoMaster", "CM-500", "F56789", 10000);
        Freighter freighter2 = new Freighter("HeavyLift", "HL-750", "F98765", 12000);
        Drone drone1 = new Drone("SkyDrone", "SD-200", "D98765", 2.5);

        aircraftList.addAll(Arrays.asList(airliner1, airliner2, freighter1, freighter2, drone1));

        List<Flight> flights = Arrays.asList(
                new Flight("F001", airliner1, LocalTime.of(10, 0), LocalTime.of(10, 33)),
                new Flight("F002", freighter1, LocalTime.of(10, 15), LocalTime.of(10, 45)),
                new Flight("F003", drone1, LocalTime.of(11, 0), LocalTime.of(11, 31)),
                new Flight("F004", airliner2, LocalTime.of(9, 30), LocalTime.of(10, 1)),
                new Flight("F005", freighter2, LocalTime.of(10, 44), LocalTime.of(11, 17))
        );

        Airport airport = new Airport(3);
        for (Flight flight : flights) {
            airport.assignFlight(flight);
        }

        System.out.println("Airport Schedule:");
        airport.printSchedule();

        System.out.println("Minimum runways required: " + airport.getMinRunwaysRequired(flights));
    }
}
