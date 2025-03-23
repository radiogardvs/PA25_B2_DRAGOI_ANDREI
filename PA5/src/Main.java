import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            ImageRepository repository = new ImageRepository();

            Image img1 = new Image("Vacation2024", "2024-03-23", Arrays.asList("vacation", "beach", "sunset"), "path/to/vacation2024.jpg");
            Image img2 = new Image("BirthdayParty", "2024-03-15", Arrays.asList("birthday", "friends", "celebration"), "path/to/birthdayparty.jpg");

            repository.addImage(img1);
            repository.addImage(img2);

            System.out.println("Displaying images in repository:");
            repository.displayImages();

            ImageDisplay.displayImage(img1);
        } catch (ImageException | IOException e) {
            e.printStackTrace();
        }
    }
}
