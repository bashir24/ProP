import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    public Vec2 pos;        // Aktuelle Position
    public Vec2 lastPos;    // Letzte Position
    public Vec2 vel;        // Geschwindigkeit

    private BufferedImage[] walkFrames; // Laufanimation
    private int currentFrame = 0;
    private int frameCounter = 0;

    public Player(float x, float y) {
        pos = new Vec2(x, y);
        lastPos = new Vec2(x, y);
        vel = new Vec2(0, 0);
        loadImages();
    }

    private void loadImages() {
        try {
            // Beispiel: lade 4 Laufbilder p1_walk1.png ... p1_walk4.png
            walkFrames = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                walkFrames[i] = ImageIO.read(new File("./assets/Player/p1_walk/PNG/p1_walk0" + (i+1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move() {
        lastPos.x = pos.x;
        lastPos.y = pos.y;
        pos.x += vel.x;
        pos.y += vel.y;

        // Animation aktualisieren
        if (vel.x != 0) {
            frameCounter++;
            if (frameCounter % 10 == 0) { // alle 10 Frames wechseln
                currentFrame = (currentFrame + 1) % walkFrames.length;
            }
        }
    }

    public BufferedImage getImage() {
        return walkFrames[currentFrame];
    }
}
