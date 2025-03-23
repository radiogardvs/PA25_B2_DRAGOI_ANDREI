
import java.util.ArrayList;
import java.util.List;

public class ImageRepository {
    private List<Image> images;

    public ImageRepository() {
        images = new ArrayList<>();
    }

    public void addImage(Image image) throws ImageException {
        if (image == null) {
            throw new ImageException("Image cannot be null.");
        }
        images.add(image);
    }

    public void displayImages() {
        for (Image image : images) {
            System.out.println(image);
        }
    }
}
