import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    public Vec2 pos, lastPos, velocity;
    private BufferedImage[] walkFrames;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private int frameDelay = 100; // ms zwischen Animation Frames

    public Player(float x, float y) {
        pos = new Vec2(x, y);
        lastPos = new Vec2(x, y);
        velocity = new Vec2(0, 0);

        walkFrames = new BufferedImage[4]; // Anzahl der Animationsbilder anpassen
        try {
            for (int i = 0; i < walkFrames.length; i++) {
                walkFrames[i] = ImageIO.read(new File("./assets/Player/p1_walk/PNG/p1_walk0" + (i+1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(float dx, float dy) {
        lastPos.x = pos.x;
        lastPos.y = pos.y;
        pos.x += dx;
        pos.y += dy;
    }

    public void updateAnimation(boolean moving) {
        if (moving && System.currentTimeMillis() - lastFrameTime > frameDelay) {
            currentFrame = (currentFrame + 1) % walkFrames.length;
            lastFrameTime = System.currentTimeMillis();
        }
        if (!moving) {
            currentFrame = 0;
        }
    }

    public BufferedImage getImage() {
        return walkFrames[currentFrame];
    }
}
