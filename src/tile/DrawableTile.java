package tile;

import main.Drawable;
import main.GamePanel;

import java.awt.Graphics2D;

public class DrawableTile implements Drawable {
    final Tile tile;
    final int worldX, worldY;
    final GamePanel gamePanel;

    public DrawableTile(Tile tile, int worldX, int worldY, GamePanel gamePanel) {
        this.tile = tile;
        this.worldX = worldX;
        this.worldY = worldY;
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gamePanel.player.x + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.y + gamePanel.player.screenY;
        g2.drawImage(tile.sprite, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    @Override
    public int getDrawY() {
        if (tile.layer == -1) {
            return Integer.MAX_VALUE;
        }
        return worldY;
    }

    public int getLayer() {
        return tile.layer;
    }
    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }
}
