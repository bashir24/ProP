import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Level {
    BufferedImage levelImg, resultingLevelImg;
    public Player player;
    Vec2 lvlSize;
    float offsetX;
    public static ArrayList<BufferedImage> tileImages = new ArrayList<>();
    public int tileSize = 70;

    public Level(String levelMapPath) {
        try {
            lvlSize = new Vec2(0, 0);
            offsetX = 0.0f;

            try {
                // Level image
                levelImg = ImageIO.read(new File(levelMapPath));

                // Tile images
                tileImages.add(ImageIO.read(new File("./assets/Tiles/grassMid.png")));
                tileImages.add(ImageIO.read(new File("./assets/Tiles/liquidWaterTop_mid.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            initLevel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        checkCollision(player);
        // Kamera-Offset anpassen
        float diff = player.pos.x - 500 - offsetX;
        int noMoveZone = 100;
        if (Math.abs(diff) > noMoveZone) {
            if (diff < 0) diff += noMoveZone;
            else diff -= noMoveZone;
            offsetX += diff;
        }
        if (offsetX < 0) offsetX = 0;
        if (offsetX > resultingLevelImg.getWidth() - 1000)
            offsetX = resultingLevelImg.getWidth() - 1000;
    }


    public ArrayList<Tile> tiles = new ArrayList<>();

    public void initLevel() {
        tiles.clear();
        lvlSize.x = tileSize * levelImg.getWidth(null);
        lvlSize.y = tileSize * levelImg.getHeight(null);

        resultingLevelImg = new BufferedImage((int) lvlSize.x, (int) lvlSize.y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) resultingLevelImg.getGraphics();

        for (int y = 0; y < levelImg.getHeight(null); y++) {
            for (int x = 0; x < levelImg.getWidth(null); x++) {
                Color color = new Color(levelImg.getRGB(x, y));

                int tileIndex = -1;
                if (color.equals(Color.BLACK)) tileIndex = 0;
                if (color.equals(Color.BLUE)) tileIndex = 1;

                if (tileIndex >= 0) {
                    BufferedImage img = tileImages.get(tileIndex);
                    g2d.drawImage(img, x * tileSize, y * tileSize, null);

                    // Tile auch als Objekt speichern
                    tiles.add(new Tile(img, x * tileSize, y * tileSize, tileSize));
                }
            }
        }
        g2d.dispose();
    }

    public void checkCollision(Player p) {
        for (Tile t : tiles) {
            if (p.box.intersect(t.box)) {
                Vec2 overlap = p.box.overlapSize(t.box);

                // Richtung bestimmen:
                if (overlap.x < overlap.y) {
                    // Seitliche Kollision
                    if (p.box.min.x < t.box.min.x) {
                        System.out.println("Kollision von rechts gegen Tile (Tile ist rechts)");
                        // Position korrigieren: Spieler links vom Tile setzen
                        p.pos.x = t.box.min.x - p.width;
                    } else {
                        System.out.println("Kollision von links gegen Tile (Tile ist links)");
                        p.pos.x = t.box.max.x;
                    }
                } else {
                    // Vertikale Kollision
                    if (p.box.min.y < t.box.min.y) {
                        System.out.println("Kollision von unten gegen Tile (Tile ist unten)");
                        p.pos.y = t.box.min.y - p.height;
                    } else {
                        System.out.println("Kollision von oben gegen Tile (Tile ist oben)");
                        p.pos.y = t.box.max.y;
                    }
                }

                // BoundingBox neu setzen nach Korrektur:
                p.box.min.x = p.pos.x;
                p.box.min.y = p.pos.y;
                p.box.max.x = p.pos.x + p.width;
                p.box.max.y = p.pos.y + p.height;

                break;
            }
        }
    }





    public Image getResultingImage() {
        return resultingLevelImg;
    }
}