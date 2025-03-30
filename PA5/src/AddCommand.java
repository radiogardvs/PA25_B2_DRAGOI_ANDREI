import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;

public class AddCommand implements Command {
    private final ImageItem image;

    public AddCommand(String name, String date, String tags, String location) {
        this.image = new ImageItem(name, LocalDate.parse(date), Arrays.asList(tags.split(",")), Path.of(location));
    }

    @Override
    public void execute(ImageRepository repository) {
        repository.addImage(image);
    }
}
