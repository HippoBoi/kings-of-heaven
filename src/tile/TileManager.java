package tile;

import entity.Camera;
import main.Drawable;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    String scene;
    public Tile[] tile;
    public int[][] mapTileNumber;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.scene = gamePanel.gameScene.getSceneName();

        tile = new Tile[10];
        mapTileNumber = new int[gamePanel.maxWorldX][gamePanel.maxWorldY];

        getTileImage();
        loadMap();
    }

    public int getTileLayer(String tileName) {
        switch (scene) {
            default:
                switch (tileName) {
                    case "/tiles/outside/tile_00.png":
                        return -1;
                    case "/tiles/outside/tile_01.png":
                        return -1;
                    case "/tiles/outside/tile_02.png":
                        return 1;
                    case "/tiles/outside/tile_03.png":
                        return 1;
                    case "/tiles/outside/tile_04.png":
                        return -1;
                    case "/tiles/outside/tile_05.png":
                        return -1;
                    case "/tiles/outside/tile_06.png":
                        return -1;
                }
        }
        return 0; // Default to depth-sorted layer
    }

    public boolean isTileSolid(String tileName) {
        switch (scene) {
            default:
                switch (tileName) {
                    case "/tiles/outside/tile_04.png":
                        return true;
                }
                break;
        }
        return false;
    }

    public void getTileImage() {
        try {
            final int totalTiles = 7;

            for (int i = 0; i < totalTiles; i++) {
                final String path = "/tiles/" + scene + "/tile_0" + i + ".png";
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
            if (inputStream == null) {
                System.out.println("[TileManager ERROR]: couldn't find map txt file");
                return;
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int columns = 0;
            int rows = 0;

            while (columns < gamePanel.maxWorldX && rows < gamePanel.maxWorldY) {
                String line = bufferedReader.readLine();

                if (line == null) {
                    break;
                }

                while (columns < gamePanel.maxWorldX) {
                    String[] numbers = line.split(" ");

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
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Drawable> getBackgroundTiles() {
        List<Drawable> backgroundTiles = new ArrayList<>();
        
        int worldColumns = 0;
        int worldRows = 0;

        while (worldColumns < gamePanel.maxWorldX && worldRows < gamePanel.maxWorldY) {
            if (worldColumns >= mapTileNumber.length || worldRows >= mapTileNumber[worldColumns].length) {
                worldColumns++;
                continue;
            }

            int tilePos = mapTileNumber[worldColumns][worldRows];

            int x = worldColumns * gamePanel.tileSize;
            int y = worldRows * gamePanel.tileSize;

            if (isTileVisible(x, y)) {
                Tile curTile = tile[tilePos];
                Tile defaultTile = tile[0];
                if (curTile.layer == -1) {
                    backgroundTiles.add(new DrawableTile(curTile, x, y, gamePanel));
                }

                if (curTile.layer > 0) {
                    backgroundTiles.add(new DrawableTile(defaultTile, x, y, gamePanel));
                }
            }

            worldColumns += 1;

            if (worldColumns == gamePanel.maxWorldX) {
                worldColumns = 0;
                worldRows += 1;
            }
        }
        
        return backgroundTiles;
    }

    public List<Drawable> getDepthSortedTiles() {
        List<Drawable> depthTiles = new ArrayList<>();
        
        int worldColumns = 0;
        int worldRows = 0;

        while (worldColumns < gamePanel.maxWorldX && worldRows < gamePanel.maxWorldY) {
            if (worldColumns >= mapTileNumber.length || worldRows >= mapTileNumber[worldColumns].length) {
                worldColumns++;
                continue;
            }

            int tilePos = mapTileNumber[worldColumns][worldRows];

            int x = worldColumns * gamePanel.tileSize;
            int y = worldRows * gamePanel.tileSize;

            if (isTileVisible(x, y)) {
                Tile curTile = tile[tilePos];
                if (curTile.layer >= 0) {
                    depthTiles.add(new DrawableTile(curTile, x, y, gamePanel));
                }
            }

            worldColumns += 1;

            if (worldColumns == gamePanel.maxWorldX) {
                worldColumns = 0;
                worldRows += 1;
            }
        }
        
        return depthTiles;
    }

    private boolean isTileVisible(int x, int y) {
        Camera camera = gamePanel.camera;
        return x + gamePanel.tileSize > camera.x - camera.screenX && 
               x - gamePanel.tileSize < camera.x + camera.screenX &&
               y + gamePanel.tileSize > camera.y - camera.screenY && 
               y - gamePanel.tileSize < camera.y + camera.screenY;
    }
}
