package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;
    int mapTileNumber[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNumber = new int[gamePanel.maxScreenTilesX][gamePanel.maxScreenTilesY];

        getTileImage();
        loadMap();
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

    public void loadMap() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/maps/map_01.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int columns = 0;
            int rows = 0;

            while (columns < gamePanel.maxScreenTilesX && rows < gamePanel.maxScreenTilesY) {
                String line = bufferedReader.readLine();

                while (columns < gamePanel.maxScreenTilesX) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[columns]);

                    mapTileNumber[columns][rows] = num;
                    columns += 1;
                }
                if (columns == gamePanel.maxScreenTilesX) {
                    columns = 0;
                    rows += 1;
                }
            }
            // bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        int columns = 0;
        int rows = 0;
        int x = 0;
        int y = 0;

        while (columns < gamePanel.maxScreenTilesX && rows < gamePanel.maxScreenTilesY) {
            int tilePos = mapTileNumber[columns][rows];

            graphics2D.drawImage(tile[tilePos].sprite, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            columns += 1;
            x += gamePanel.tileSize;

            if (columns == gamePanel.maxScreenTilesX) {
                columns = 0;
                x = 0;

                rows += 1;
                y += gamePanel.tileSize;
            }
        }
    }
}
