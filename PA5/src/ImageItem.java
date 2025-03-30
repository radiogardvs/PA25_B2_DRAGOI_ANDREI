
import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public record ImageItem(String name, LocalDate date, List<String> tags, Path location) implements Serializable {
}
