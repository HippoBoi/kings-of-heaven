package entity;

import main.GamePanel;

public class Camera {
    public int x, y;
    public int screenX, screenY;
    GamePanel gamePanel;

    public Camera(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        this.screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
