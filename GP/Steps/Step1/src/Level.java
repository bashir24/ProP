import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Level {
    // Das farbcodierte Level-Bild (z.B. level1.bmp)
    private final BufferedImage levelMap;
    // Kachel-Bilder für Gras und Wasser
    private final BufferedImage grassTile;
    private final BufferedImage waterTile;
    // Fertiges zusammengesetztes Level-Bild
    private final BufferedImage outputImage;

    // Konstruktor: lädt Level-Bild und Kacheln, erstellt das fertige Level
    public Level(File levelFile) throws IOException {
        // Level-Bild laden (Farbcodiertes BMP)
        levelMap = ImageIO.read(levelFile);

        // Einzelne Kacheln laden (Gras und Wasser)
        grassTile = ImageIO.read(new File("assets/Tiles/grassMid.png"));
        waterTile = ImageIO.read(new File("assets/Tiles/liquidWaterTop_mid.png"));

        // Großes Level-Bild aus Kacheln erstellen
        outputImage = buildLevelImage();
    }

    // Methode erstellt das fertige Level-Bild aus dem LevelMap-Pixelbild
    private BufferedImage buildLevelImage() {
        int tileW = 70; // Breite einer Kachel in Pixel
        int tileH = 70; // Höhe einer Kachel in Pixel
        int width = levelMap.getWidth() * tileW;   // Breite des gesamten Levels
        int height = levelMap.getHeight() * tileH; // Höhe des gesamten Levels

        // Neues Bild für das gesamte Level erzeugen
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = result.createGraphics(); // Graphics2D-Objekt zum Zeichnen

        // Schleife über alle Pixel des farbcodierten Level-Bildes
        for (int y = 0; y < levelMap.getHeight(); y++) {
            for (int x = 0; x < levelMap.getWidth(); x++) {
                // Farbe des aktuellen Pixels abfragen
                Color c = new Color(levelMap.getRGB(x, y));

                // Pixelkoordinaten in Pixelposition im fertigen Level umrechnen
                int drawX = x * tileW;
                int drawY = y * tileH;

                // Je nach Farbe die passende Kachel zeichnen
                if (c.equals(Color.BLACK)) {          // Schwarz = Gras
                    g2d.drawImage(grassTile, drawX, drawY, null);
                } else if (c.equals(Color.BLUE)) {   // Blau = Wasser
                    g2d.drawImage(waterTile, drawX, drawY, null);
                }
                // Weitere Farben/Kacheln können hier ergänzt werden
            }
        }

        // Graphics2D freigeben
        g2d.dispose();

        // Fertiges Level-Bild zurückgeben
        return result;
    }

    // Getter: liefert das fertige Level-Bild für die Anzeige zurück
    public BufferedImage getLevelImage() {
        return outputImage;
    }
}
