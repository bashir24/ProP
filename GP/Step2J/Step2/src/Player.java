import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Player {

    public Vec2 pos = new Vec2(100, 190);
    public Vec2 prevPos = new Vec2(100, 190);
    public Vec2 vel = new Vec2(0, 0);

    private BufferedImage[] walk;
    private int frame = 0;

    public Player(){
        try {
            walk = new BufferedImage[]{
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk01.png")),
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk02.png")),
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk03.png")),
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk04.png")),
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk05.png")),
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk06.png")),
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk07.png")),
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk08.png")),
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk09.png")),
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk10.png")),
                    ImageIO.read(new File("assets/Player/p1_walk/PNG/p1_walk11.png")),

            };
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    public void move(float dx, float dy){
        prevPos.x = pos.x;
        prevPos.y = pos.y;
        pos.x += dx;
        pos.y += dy;
        if(dx != 0 || dy != 0){
            frame = (frame + 1) % walk.length;
        }
    }

    public BufferedImage getImage() {return walk[frame];}
    public int getWidth() {return walk[frame].getWidth();}
    public int getHeight() {return walk[frame].getHeight();}

}
