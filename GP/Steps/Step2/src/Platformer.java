import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private Player player;
    private BufferStrategy bufferStrategy;

    public Platformer() {
        // Fenster schließen
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Level auswählen
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("./"));
        fc.setDialogTitle("Select input image");
        FileFilter filter = new FileNameExtensionFilter("Level image (.bmp)", "bmp");
        fc.setFileFilter(filter);
        int result = fc.showOpenDialog(this);
        File selectedFile = new File("");

        addKeyListener(new AL(this));
        setFocusable(true);
        requestFocus();

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        } else {
            dispose();
            System.exit(0);
        }

        try {
            l = new Level(selectedFile.getAbsolutePath());
            player = new Player(100, 300); // Startposition Player

            this.setBounds(0, 0, 1000, 5 * 70);
            this.setVisible(true);

            // Double-Buffering
            createBufferStrategy(2);
            bufferStrategy = this.getBufferStrategy();

            // Game Loop
            new Thread(() -> {
                while (true) {
                    updateGameStateAndRepaint();
                    try {
                        Thread.sleep(16); // ~60 FPS
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateGameStateAndRepaint() {
        l.update();
        player.move();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = null;
        try {
            g2 = (Graphics2D) bufferStrategy.getDrawGraphics();
            draw(g2);
        } finally {
            if (g2 != null) g2.dispose();
        }
        bufferStrategy.show();
    }

    private void draw(Graphics2D g2) {
        // 1. Level-Bild holen
        BufferedImage img_level = (BufferedImage) l.getResultingImage();

        int viewWidth = 1000;
        int viewHeight = this.getHeight();

        int offsetX = (int) player.pos.x - viewWidth / 2;
        int offsetY = (int) player.pos.y - viewHeight / 2;

        // Grenzen prüfen
        offsetX = Math.max(0, Math.min(offsetX, img_level.getWidth() - viewWidth));
        offsetY = Math.max(0, Math.min(offsetY, img_level.getHeight() - viewHeight));

        BufferedImage visibleLevel = img_level.getSubimage(offsetX, offsetY, viewWidth, viewHeight);

        // 3. Level zeichnen
        g2.drawImage(visibleLevel, 0, 0, this);

        // 4. Player über dem Level zeichnen
        g2.drawImage(player.getImage(), (int) player.pos.x, (int) player.pos.y, this);
    }


    public class AL extends KeyAdapter implements KeyListener {
        Platformer p;

        public AL(Platformer p) {
            super();
            this.p = p;
        }

        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();

            if (keyCode == KeyEvent.VK_ESCAPE) {
                dispose();
            }
            if (keyCode == KeyEvent.VK_LEFT) {
                player.vel.x = -5;
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                player.vel.x = 5;
            }
            if (keyCode == KeyEvent.VK_UP) {
                player.vel.y = -5;
            }
            if (keyCode == KeyEvent.VK_DOWN) {
                player.vel.y = 5;
            }
        }

        @Override
        public void keyReleased(KeyEvent event) {
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
                player.vel.x = 0;
            }
            if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
                player.vel.y = 0;
            }
        }

    }


}
