import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serial;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Platformer extends JFrame {
	@Serial
	private static final long serialVersionUID = 5736902251450559962L;

	private BufferedImage levelImage;
	private int viewX = 0;
	private final int viewY = 0;
	private final int viewWidth = 1000;
	private final int viewHeight = 350;

	public Platformer() {
		// Fenster schließen = Programm beenden
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Level-Datei auswählen
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("./"));
		fc.setDialogTitle("Select level image");
		FileFilter filter = new FileNameExtensionFilter("Level image (.bmp)", "bmp");
		fc.setFileFilter(filter);

		File selectedFile;
		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			selectedFile = fc.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		} else {
			dispose();
			System.exit(0);
			return;
		}

		try {

			// Statt das Level-Bild direkt zu laden und anzuzeigen, wird jetzt die Level-Klasse verwendet:
			Level level = new Level(selectedFile);
			levelImage = level.getLevelImage();

			this.setBounds(0, 0, viewWidth + 16, viewHeight + 39);
			this.setVisible(true);

			// KeyAdapter für Links/Rechts-Scrollen
			addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						viewX = Math.max(0, viewX - 10);
						repaint();
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						viewX = Math.min(levelImage.getWidth() - viewWidth, viewX + 10);
						repaint();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		if (levelImage == null) return;
		Graphics2D g2d = (Graphics2D) g;

		//Zeichnet nur einen Ausschnitt des Level-Bildes (Viewport) anstelle des gesamten Bildes:
		g2d.drawImage(levelImage,
				8, 31, viewWidth + 8, viewHeight + 31,   // Zielbereich
				viewX, viewY, viewX + viewWidth, viewY + viewHeight, // Quellbereich
				this);
	}


}
