public class RemoveCommand implements Command {
    private final String name;

    public RemoveCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(ImageRepository repository) {
        repository.removeImage(name);
    }
}
