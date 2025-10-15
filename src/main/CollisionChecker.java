package main;

import entity.Entity;
import tile.Tile;
import tile.TileManager;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeft = entity.x + entity.hitbox.x;
        int entityRight = entity.x + entity.hitbox.x + entity.hitbox.width;
        int entityTop = entity.y + entity.hitbox.y;
        int entityBot = entity.y + entity.hitbox.y + entity.hitbox.height;

        int entityLeftCol = entityLeft / gamePanel.tileSize;
        int entityRightCol = entityRight / gamePanel.tileSize;
        int entityTopRow = entityTop / gamePanel.tileSize;
        int entityBotRow = entityBot / gamePanel.tileSize;

        int nearestTilePos1;
        int nearestTilePos2;
        Tile nearestTile1;
        Tile nearestTile2;

        TileManager tileManager = gamePanel.tileManager;

        if (entity.directions.contains("up")) {
            entityTopRow = (entityTop - entity.speed) / gamePanel.tileSize;
            nearestTilePos1 = tileManager.mapTileNumber[entityLeftCol][entityTopRow];
            nearestTilePos2 = tileManager.mapTileNumber[entityRightCol][entityTopRow];

            nearestTile1 = tileManager.tile[nearestTilePos1];
            nearestTile2 = tileManager.tile[nearestTilePos2];

            if (nearestTile1.isSolid || nearestTile2.isSolid) {
                entity.isColliding = true;
            }
        }
        if (entity.directions.contains("left")) {
            entityLeftCol = (entityLeft - entity.speed) / gamePanel.tileSize;
            nearestTilePos1 = tileManager.mapTileNumber[entityLeftCol][entityTopRow];
            nearestTilePos2 = tileManager.mapTileNumber[entityLeftCol][entityBotRow];

            nearestTile1 = tileManager.tile[nearestTilePos1];
            nearestTile2 = tileManager.tile[nearestTilePos2];

            if (nearestTile1.isSolid || nearestTile2.isSolid) {
                entity.isColliding = true;
            }
        }
        if (entity.directions.contains("right")) {
            entityRightCol = (entityRight + entity.speed) / gamePanel.tileSize;
            nearestTilePos1 = tileManager.mapTileNumber[entityRightCol][entityTopRow];
            nearestTilePos2 = tileManager.mapTileNumber[entityRightCol][entityBotRow];

            nearestTile1 = tileManager.tile[nearestTilePos1];
            nearestTile2 = tileManager.tile[nearestTilePos2];

            if (nearestTile1.isSolid || nearestTile2.isSolid) {
                entity.isColliding = true;
            }
        }
        if (entity.directions.contains("up")) {
            entityTopRow = (entityTop - entity.speed) / gamePanel.tileSize;
            nearestTilePos1 = tileManager.mapTileNumber[entityLeftCol][entityTopRow];
            nearestTilePos2 = tileManager.mapTileNumber[entityRightCol][entityTopRow];

            nearestTile1 = tileManager.tile[nearestTilePos1];
            nearestTile2 = tileManager.tile[nearestTilePos2];

            if (nearestTile1.isSolid || nearestTile2.isSolid) {
                entity.isColliding = true;
            }
        }
        if (entity.directions.contains("down")) {
            entityBotRow = (entityBot + entity.speed) / gamePanel.tileSize;
            nearestTilePos1 = tileManager.mapTileNumber[entityLeftCol][entityBotRow];
            nearestTilePos2 = tileManager.mapTileNumber[entityRightCol][entityBotRow];

            nearestTile1 = tileManager.tile[nearestTilePos1];
            nearestTile2 = tileManager.tile[nearestTilePos2];

            if (nearestTile1.isSolid || nearestTile2.isSolid) {
                entity.isColliding = true;
            }
        }
    }
}
