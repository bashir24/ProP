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

/**
 * Platformer: neue, moderne Version der Plattform-Spiel-Klasse
 * ==============================
 * NEU:
 * 1. BufferStrategy für flüssiges Rendering (verhindert Flackern)
 * 2. Player-Objekt für Bewegung und Animation
 * 3. Kamera folgt automatisch dem Spieler
 * 4. Erweiterte Tastatursteuerung (UP, DOWN, LEFT, RIGHT + ESC)
 * 5. Sauberes File-Handling und NullPointer-Schutz
 */
public class Platformer extends JFrame {
    @Serial
    private static final long serialVersionUID = 5736902251450559962L;

    private Level level;
    private Player player;               // NEU: Spieler-Objekt für Bewegung/Animation
    private BufferStrategy bufferStrategy; // NEU: BufferStrategy für flüssiges Zeichnen

    public Platformer() {
        // Fenster schließen beim Klick auf "X"
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
            // NEU: Level und Player erst nach Dateiauswahl initialisieren
            level = new Level(selectedFile.getAbsolutePath());
            player = new Player(100, 100); // Startposition des Spielers

            // Fenstergröße und Sichtbarkeit
            this.setBounds(100, 100, 1000, 5 * 70);
            this.setVisible(true);

            // NEU: BufferStrategy initialisieren für flüssiges Zeichnen
            createBufferStrategy(2);
            bufferStrategy = this.getBufferStrategy();

            // NEU: Tastatursteuerung hinzufügen
            addKeyListener(new KeyHandler(this));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * NEU: Aktualisiert Level und Spieler, anschließend wird neu gezeichnet
     */
    private void updateGameStateAndRepaint() {
        level.update(); // Level-Logik aktualisieren
        repaint();      // Neu zeichnen
    }

    /**
     * NEU: Überschreibt paint, um BufferStrategy zu nutzen
     * Flackerfreies Zeichnen von Level + Spieler
     */
    @Override
    public void paint(Graphics g) {
        if (bufferStrategy == null) return; // Schutz vor NullPointer
        Graphics2D g2 = null;
        try {
            g2 = (Graphics2D) bufferStrategy.getDrawGraphics();
            draw(g2); // NEU: Methode zum Zeichnen von Level und Player
        } finally {
            if (g2 != null) g2.dispose();
        }
        bufferStrategy.show(); // Zeigt den Puffer an (flüssiges Rendering)
    }

    /**
     * NEU: Zeichnet das Level und den Spieler
     * - Kamera folgt dem Spieler
     * - Level wird als Subimage gezeichnet
     * - Spieler relativ zur Kamera gezeichnet
     */
    private void draw(Graphics2D g2) {
        BufferedImage levelImage = (BufferedImage) level.getResultingImage();

        // Kamera-Offset auf Spieler zentrieren
        level.offsetX = Math.max(0, Math.min(player.pos.x - 500, levelImage.getWidth() - 1000));

        // Level-Subimage für Kamera-Ansicht
        BufferedImage subLevel = levelImage.getSubimage((int) level.offsetX, 0, 1000, levelImage.getHeight());
        g2.drawImage(subLevel, 0, 0, this);

        // Spieler relativ zur Kamera zeichnen
        g2.drawImage(player.getImage(), (int) (player.pos.x - level.offsetX), (int) player.pos.y, this);
    }

    /**
     * NEU: Getter für den Spieler
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * NEU: Innerer KeyAdapter für Tastatursteuerung
     * - Unterstützung: LEFT, RIGHT, UP, DOWN, ESC
     * - Bewegt Spieler und aktualisiert Animation
     */
    private class KeyHandler extends KeyAdapter {
        Platformer game;

        public KeyHandler(Platformer game) {
            this.game = game;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            boolean moved = false;

            if (key == KeyEvent.VK_ESCAPE) dispose(); // Spiel schließen
            if (key == KeyEvent.VK_LEFT) { player.move(-10, 0); moved = true; }
            if (key == KeyEvent.VK_RIGHT) { player.move(10, 0); moved = true; }
            if (key == KeyEvent.VK_UP) { player.move(0, -10); moved = true; }
            if (key == KeyEvent.VK_DOWN) { player.move(0, 10); moved = true; }

            player.updateAnimation(moved); // Animation nur aktualisieren, wenn Bewegung
            updateGameStateAndRepaint();   // Level + Player neu zeichnen
        }
    }

    /**
     * Hauptmethode: startet das Spiel
     */
    public static void main(String[] args) {
        new Platformer(); // NEU: Spiel starten
    }
}
