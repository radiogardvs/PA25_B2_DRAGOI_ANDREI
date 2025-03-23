
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class ImageDisplay {
    public static void displayImage(Image image) throws IOException {
        File imageFile = new File(image.getLocation());
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(imageFile);
        } else {
            System.out.println("Desktop not supported. Unable to display the image.");
        }
    }
}
