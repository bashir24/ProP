import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile {
    public BoundingBox box;
    private BufferedImage image;

    public Tile(BufferedImage image, float x, float y, int size) {
        this.image = image;
        this.box = new BoundingBox(x, y, x + size, y + size);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, (int) box.min.x, (int) box.min.y, null);
    }
}
