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
    public Tile[] tile;
    public int mapTileNumber[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNumber = new int[gamePanel.maxWorldX][gamePanel.maxWorldY];

        getTileImage();
        loadMap();
    }

    public int getTileLayer(String tileName) {
        switch (tileName) {
            case "/tiles/outside/tile_02.png":
                return 1;
            case "/tiles/outside/tile_03.png":
                return 1;
        }
        return 0;
    }

    public boolean isTileSolid(String tileName) {
        switch (tileName) {
            case "/tiles/outside/tile_04.png":
                return true;
        }
        return false;
    }

    public void getTileImage() {
        try {
            String scene = gamePanel.gameScene.getSceneName();

            final int totalTiles = 7;

            for (int i = 0; i < totalTiles; i++) {
                final String path = "/tiles/" + scene + "/tile_0" + String.valueOf(i) + ".png";
                final BufferedImage sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                tile[i] = new Tile();
                tile[i].sprite = sprite;
                tile[i].name = path;
                tile[i].layer = getTileLayer(tile[i].name);
                tile[i].isSolid = isTileSolid(tile[i].name);
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

                if (line == null) {
                    break;
                }

                while (columns < gamePanel.maxWorldX) {
                    String numbers[] = line.split(" ");

                    if (columns >= numbers.length) {
                        mapTileNumber[columns][rows] = 0;
                    }
                    else {
                        int num = Integer.parseInt(numbers[columns]);
                        mapTileNumber[columns][rows] = num;
                    }

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

    public void draw(Graphics2D graphics2D, int layer) {
        int worldColumns = 0;
        int worldRows = 0;

        while (worldColumns < gamePanel.maxWorldX && worldRows < gamePanel.maxWorldY) {
            if (worldColumns > mapTileNumber.length || worldRows > mapTileNumber[worldColumns].length) {
                worldColumns++;
                continue;
            }

            int tilePos = mapTileNumber[worldColumns][worldRows];

            int x = worldColumns * gamePanel.tileSize;
            int y = worldRows * gamePanel.tileSize;
            int screenX = x - gamePanel.player.x + gamePanel.player.screenX;
            int screenY = y - gamePanel.player.y + gamePanel.player.screenY;

            Tile curTile = tile[tilePos];
            Tile defaultTile = tile[0];

            if (x + gamePanel.tileSize > gamePanel.player.x - gamePanel.player.screenX && x - gamePanel.tileSize < gamePanel.player.x + gamePanel.player.screenX
                && (y + gamePanel.tileSize > gamePanel.player.y - gamePanel.player.screenY && y - gamePanel.tileSize < gamePanel.player.y + gamePanel.player.screenY)) {

                if (curTile.layer == layer) {
                    graphics2D.drawImage(curTile.sprite, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
                }

                if (curTile.layer != layer && curTile.layer == 1) {
                    graphics2D.drawImage(defaultTile.sprite, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
                }
            }

            worldColumns += 1;

            if (worldColumns == gamePanel.maxWorldX) {
                worldColumns = 0;
                worldRows += 1;
            }
        }
    }
}
