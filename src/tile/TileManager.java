package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];

        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(tile[0].sprite, 0, 0, gamePanel.tileSize, gamePanel.tileSize, null);
        graphics2D.drawImage(tile[0].sprite, 48, 0, gamePanel.tileSize, gamePanel.tileSize, null);
        graphics2D.drawImage(tile[0].sprite, 96, 0, gamePanel.tileSize, gamePanel.tileSize, null);

        graphics2D.drawImage(tile[1].sprite, 0, 48, gamePanel.tileSize, gamePanel.tileSize, null);
        graphics2D.drawImage(tile[1].sprite, 48, 48, gamePanel.tileSize, gamePanel.tileSize, null);
        graphics2D.drawImage(tile[1].sprite, 96, 48, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
