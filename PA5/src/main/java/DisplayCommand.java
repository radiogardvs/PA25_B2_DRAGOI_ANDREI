import java.io.IOException;

public class DisplayCommand implements Command {
    private final String name;

    public DisplayCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(ImageRepository repository) throws IOException {
        repository.displayImage(name);
    }
}
