package model;
import com.github.javafaker.Faker;

public class LocationGenerator{
    private static final Faker faker = new Faker();

    public static Location generateLocation(LocationType type) {
        String name = faker.address().cityName();
        return new Location(name, type);
    }
}
