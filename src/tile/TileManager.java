package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;
    int mapTileNumber[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNumber = new int[gamePanel.maxWorldX][gamePanel.maxWorldY];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            final int totalTiles = 4;

            String scene = gamePanel.gameScene.getSceneName();
            for (int i = 0; i < totalTiles; i++) {
                final String path = "/tiles/" + scene + "/tile_0" + String.valueOf(i) + ".png";
                final BufferedImage sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                tile[i] = new Tile();
                tile[i].sprite = sprite;
            }
        } catch (IOException e) {
            System.out.println("[ERROR] couldn't get tile images");
        }
    }

    public void loadMap() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/maps/map_01.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int columns = 0;
            int rows = 0;

            while (columns < gamePanel.maxWorldX && rows < gamePanel.maxWorldY) {
                String line = bufferedReader.readLine();

                while (columns < gamePanel.maxWorldX) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[columns]);

                    mapTileNumber[columns][rows] = num;
                    columns += 1;
                }
                if (columns == gamePanel.maxWorldX) {
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
        int worldColumns = 0;
        int worldRows = 0;

        while (worldColumns < gamePanel.maxWorldX && worldRows < gamePanel.maxWorldY) {
            int tilePos = mapTileNumber[worldColumns][worldRows];

            int x = worldColumns * gamePanel.tileSize;
            int y = worldRows * gamePanel.tileSize;
            int screenX = x - gamePanel.player.x + gamePanel.player.screenX;
            int screenY = y - gamePanel.player.y + gamePanel.player.screenY;

            graphics2D.drawImage(tile[tilePos].sprite, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            worldColumns += 1;

            if (worldColumns == gamePanel.maxWorldX) {
                worldColumns = 0;
                worldRows += 1;
            }
        }
    }
}
