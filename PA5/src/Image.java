
import java.util.List;

public class Image {
    private String name;
    private String date;
    private List<String> tags;
    private String location;

    public Image(String name, String date, List<String> tags, String location) {
        this.name = name;
        this.date = date;
        this.tags = tags;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Image{name='" + name + "', date='" + date + "', tags=" + tags + ", location='" + location + "'}";
    }
}
