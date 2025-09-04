import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Platformer extends JFrame {
	@Serial
	private static final long serialVersionUID = 5736902251450559962L;

	// Bild als Klassenvariable
	private BufferedImage image;

	public Platformer() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("HelloWorld");
		this.setBounds(0, 0, 320, 240);

		// Bild laden
		try {
			image = ImageIO.read(new File("testImg.bmp")); // Bild aus Projektordner laden
		} catch (IOException e) {
			System.err.println("Bild konnte nicht geladen werden: " + e.getMessage());
		}

		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g; // cast zu Graphics2D

		// Erst das Bild zeichnen (falls es geladen wurde)
		if (image != null) {
			g2d.drawImage(image, 10, 30, this); // Bild bei (10,30) zeichnen
		}

		// Danach weiterhin deine Formen zeichnen
		Line2D.Double line = new Line2D.Double(20.0, 50.0, 50.0, 200.0);
		g2d.draw(line);

		Rectangle2D.Double rect = new Rectangle2D.Double(100.0, 50.0, 60.0, 80.0);
		g2d.draw(rect);

		Ellipse2D.Double circle = new Ellipse2D.Double(200.0, 100.0, 80.0, 80.0);
		g2d.draw(circle);
	}


}
