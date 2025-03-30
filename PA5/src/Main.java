import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ImageRepository repository = new ImageRepository();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command: ");
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+", 2);
            String commandName = parts[0];

            Command command = null;

            try {
                switch (commandName) {
                    case "add":
                        String[] addArgs = parts[1].split(" ");
                        if (addArgs.length < 4) {
                            throw new InvalidCommandException("Usage: add <name> <date> <tags> <location>");
                        }
                        command = new AddCommand(addArgs[0], addArgs[1], addArgs[2], addArgs[3]);
                        break;
                    case "remove":
                        if (parts.length < 2) {
                            throw new InvalidCommandException("Usage: remove <name>");
                        }
                        command = new RemoveCommand(parts[1]);
                        break;
                    case "display":
                        if (parts.length < 2) {
                            throw new InvalidCommandException("Usage: display <name>");
                        }
                        command = new DisplayCommand(parts[1]);
                        break;
                    case "exit":
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        throw new InvalidCommandException("Unknown command: " + commandName);
                }

                if (command != null) {
                    command.execute(repository);
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
