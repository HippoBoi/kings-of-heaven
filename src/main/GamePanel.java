package main;

import entity.Camera;
import entity.Character;
import entity.FollowerCharacter;
import entity.PlayerCharacter;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {
    final int baseTileSize = 16;
    final int scale = 3;

    public final int tileSize = baseTileSize * scale;

    public final int maxScreenTilesX = 16;
    public final int maxScreenTilesY = 12;
    public final int screenWidth = tileSize * maxScreenTilesX;
    public final int screenHeight = tileSize * maxScreenTilesY;

    public final int maxWorldX = 60;
    public final int maxWorldY = 60;

    final double maxFPS = 60;

    public final boolean DEBUG = false;

    public PartyManager partyManager = new PartyManager(this);

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    public PlayerCharacter player = new PlayerCharacter(this, keyHandler, partyManager.partyMembers.getFirst());
    public Camera camera = new Camera(this);
    public GameScene gameScene = new GameScene();

    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public TileManager tileManager = new TileManager(this);

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
        gameSetup();
    }

    public void gameSetup() {
        camera.setPosition(player.x, player.y);

        partyManager.addFollowerCharacter(partyManager.partyMembers.get(1));
        partyManager.addFollowerCharacter(partyManager.partyMembers.get(2));
        partyManager.addFollowerCharacter(partyManager.partyMembers.get(1));
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

        for (FollowerCharacter followerCharacter : partyManager.followerCharacters) {
            if (followerCharacter != null) {
                followerCharacter.update();
            }
        }

    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D)graphics;

        List<Drawable> backgroundTiles = tileManager.getBackgroundTiles();
        for (Drawable backgroundTile : backgroundTiles) {
            backgroundTile.draw(graphics2D);
        }

        List<Drawable> drawables = new ArrayList<>(tileManager.getDepthSortedTiles());
        drawables.add(player);

        for (FollowerCharacter followerCharacter : partyManager.followerCharacters) {
            if (followerCharacter != null) {
                drawables.add(followerCharacter);
            }
        }

        drawables.sort(Comparator.comparingInt(Drawable::getDrawY));

        for (Drawable drawable : drawables) {
            drawable.draw(graphics2D);
        }
        
        graphics2D.dispose();
    }
}
