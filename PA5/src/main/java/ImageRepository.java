import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ImageRepository {
    private final List<ImageItem> images = new ArrayList<>();

    public void addImage(ImageItem image) {
        images.add(image);
    }

    public void removeImage(String name) {
        images.removeIf(img -> img.name().equals(name));
    }

    public void updateImage(String name, ImageItem newImage) {
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).name().equals(name)) {
                images.set(i, newImage);
                return;
            }
        }
    }

    public void displayImage(String name) throws IOException {
        for (ImageItem img : images) {
            if (img.name().equals(name)) {
                File file = img.location().toFile();
                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                } else {
                    throw new FileNotFoundException("Image file not found: " + file.getAbsolutePath());
                }
                return;
            }
        }
        throw new IllegalArgumentException("Image not found in repository: " + name);
    }

    public void saveToFile(Path filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            oos.writeObject(images);
        }
    }

    public void loadFromFile(Path filePath) throws IOException, ClassNotFoundException {
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(filePath))) {
            List<ImageItem> loadedImages = (List<ImageItem>) ois.readObject();
            images.clear();
            images.addAll(loadedImages);
        }
    }

    public void generateHtmlReport(Path filePath) throws IOException {
        StringBuilder html = new StringBuilder("<html><head><title>Image Repository</title></head><body>");
        html.append("<h1>Image Repository Report</h1><ul>");

        for (ImageItem img : images) {
            html.append("<li><b>").append(img.name()).append("</b> (")
                    .append(img.date()).append(") - Tags: ")
                    .append(String.join(", ", img.tags()))
                    .append("<br><img src=\"").append(img.location()).append("\" width='200'></li>");
        }

        html.append("</ul></body></html>");
        Files.writeString(filePath, html.toString());
        Desktop.getDesktop().browse(filePath.toUri());
    }

    public List<ImageItem> getImages() {
        return images;
    }
}
