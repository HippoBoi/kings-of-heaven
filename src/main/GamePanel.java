package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int baseTileSize = 16;
    final int scale = 3;

    public final int tileSize = baseTileSize * scale;

    final int maxScreenX = 16;
    final int maxScreenY = 12;
    final int screenWidth = tileSize * maxScreenX;
    final int screenHeight = tileSize * maxScreenY;
    final double maxFPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);
    TileManager tileManager = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / maxFPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long curTime;

        while (gameThread != null) {
            curTime = System.nanoTime();
            delta += (curTime - lastTime) / drawInterval;
            lastTime = curTime;

            if (delta > 1) {
                update();
                repaint();

                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D)graphics;
        tileManager.draw(graphics2D);
        player.draw(graphics2D);
        graphics2D.dispose();
    }
}
