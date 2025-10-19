package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FollowerCharacter extends Entity {
    Character character;
    PlayerCharacter playerToFollow;
    GamePanel gamePanel;
    int myPosition;

    public final int screenX;
    public final int screenY;

    public FollowerCharacter(GamePanel gamePanel, Character character, int myPosition, PlayerCharacter playerToFollow) {
        this.gamePanel = gamePanel;
        this.character = character;
        this.myPosition = myPosition;
        this.playerToFollow = playerToFollow;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        x = gamePanel.tileSize * 10;
        y = gamePanel.tileSize * 6;
        xScale = 1;
        yScale = 2;

        getCharacterSprites(character);
    }

    public void update() {
        isMoving = true;
        if (isMoving) {
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNumber += 1;

                if (spriteNumber > 3) {
                    spriteNumber = 0;
                }

                spriteCounter = 0;
            }
        }
        else {
            spriteCounter = 9;
            spriteNumber = 0;
        }

        System.out.println(x + " " + y);
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage curSprite;
        int spriteWidth = gamePanel.tileSize * xScale;
        int spriteHeight = gamePanel.tileSize * yScale;

        curSprite = down1;

        int playerScreenX = x - gamePanel.camera.x + gamePanel.camera.screenX;
        int playerScreenY = y - gamePanel.camera.y + gamePanel.camera.screenY;

        graphics2D.drawImage(curSprite, playerScreenX, playerScreenY, spriteWidth, spriteHeight, null);
    }

    @Override
    public int getDrawY() {
        return y + gamePanel.tileSize;
    }
}