import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Level {
    private final BufferedImage levelMap;
    private final BufferedImage grassTile;
    private final BufferedImage waterTile;
    private final BufferedImage outputImage;

    public Level(File levelFile) throws IOException {
        // Farbcodiertes Level-Bild laden
        levelMap = ImageIO.read(levelFile);

        // Kacheln laden
        grassTile = ImageIO.read(new File("assets/Tiles/grassMid.png"));
        waterTile = ImageIO.read(new File("assets/Tiles/liquidWaterTop_mid.png"));

        outputImage = buildLevelImage();
    }

    private BufferedImage buildLevelImage() {
        int tileW = 70;
        int tileH = 70;
        int width = levelMap.getWidth() * tileW;
        int height = levelMap.getHeight() * tileH;

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = result.createGraphics();

        for (int y = 0; y < levelMap.getHeight(); y++) {
            for (int x = 0; x < levelMap.getWidth(); x++) {
                Color c = new Color(levelMap.getRGB(x, y));
                int drawX = x * tileW;
                int drawY = y * tileH;

                if (c.equals(Color.BLACK)) {
                    g2d.drawImage(grassTile, drawX, drawY, null);
                } else if (c.equals(Color.BLUE)) {
                    g2d.drawImage(waterTile, drawX, drawY, null);
                }
            }
        }
        g2d.dispose();
        return result;
    }

    public BufferedImage getLevelImage() {
        return outputImage;
    }
}
