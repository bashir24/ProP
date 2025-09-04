import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serial;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Platformer extends JFrame {
    @Serial
    private static final long serialVersionUID = 5736902251450559962L;

    private Level l = null;
    private Player player; // Player-Objekt
    private BufferStrategy bufferStrategy;

    public Platformer() {

        // Fenster schließen
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // JFileChooser für Level-Auswahl
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("./"));
        fc.setDialogTitle("Select input image");
        FileFilter filter = new FileNameExtensionFilter("Level image (.bmp)", "bmp");
        fc.setFileFilter(filter);

        int result = fc.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            dispose();
            System.exit(0);
        }

        File selectedFile = fc.getSelectedFile();
        System.out.println("Selected file: " + selectedFile.getAbsolutePath());

        try {
            // Level und Player erst **nach der Dateiauswahl** initialisieren
            l = new Level(selectedFile.getAbsolutePath());
            player = new Player(100, 100);

            // Fenstergröße
            this.setBounds(0, 0, 1000, 5 * 70);
            this.setVisible(true);

            // BufferStrategy initialisieren
            createBufferStrategy(2);
            bufferStrategy = this.getBufferStrategy();

            // Tastatursteuerung hinzufügen
            addKeyListener(new AL(this));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateGameStateAndRepaint() {
        l.update();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        if (bufferStrategy == null) return; // Schutz vor NullPointer
        Graphics2D g2 = null;
        try {
            g2 = (Graphics2D) bufferStrategy.getDrawGraphics();
            draw(g2);
        } finally {
            if (g2 != null) g2.dispose();
        }
        bufferStrategy.show();
    }

    // Zeichnet Level und Player
    private void draw(Graphics2D g2) {
        BufferedImage level = (BufferedImage) l.getResultingImage();
        // Kamera auf Player positionieren, bleibt im Levelbereich
        l.offsetX = Math.max(0, Math.min(player.pos.x - 500, level.getWidth() - 1000));
        if (l.offsetX > level.getWidth() - 1000) l.offsetX = level.getWidth() - 1000;
        if (l.offsetX < 0) l.offsetX = 0;

        BufferedImage bi = level.getSubimage((int) l.offsetX, 0, 1000, level.getHeight());
        g2.drawImage(bi, 0, 0, this);

        // Player relativ zur Kamera zeichnen
        g2.drawImage(player.getImage(), (int) (player.pos.x - l.offsetX), (int) player.pos.y, this);
    }

    // Für andere Klassen zugänglich
    public Player getPlayer() { return player; }

    // Tastatursteuerung für Bewegung und Animation
    public class AL extends KeyAdapter {
        Platformer p;
        public AL(Platformer p) { super(); this.p = p; }

        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();
            boolean moved = false;

            if (keyCode == KeyEvent.VK_ESCAPE) dispose();
            if (keyCode == KeyEvent.VK_LEFT)  { player.move(-10, 0);  moved = true; }
            if (keyCode == KeyEvent.VK_RIGHT) { player.move(10, 0);   moved = true; }
            if (keyCode == KeyEvent.VK_UP)    { player.move(0, -10);  moved = true; }
            if (keyCode == KeyEvent.VK_DOWN)  { player.move(0, 10);   moved = true; }

            player.updateAnimation(moved);
            updateGameStateAndRepaint();
        }
    }
}
