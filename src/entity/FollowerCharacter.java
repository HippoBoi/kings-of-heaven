package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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

        directions = new ArrayList<>(List.of("down"));

        getCharacterSprites(character);
    }

    public void update() {
        if (myPosition < playerToFollow.storedPosition.size() && playerToFollow.storedPosition.get(myPosition) != null) {
            Position pos = playerToFollow.storedPosition.get(myPosition);
            x = pos.x;
            y = pos.y;
        }

        spriteNumber = playerToFollow.spriteNumber;
        spriteCounter = playerToFollow.spriteCounter;
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage curSprite = down1;
        int spriteWidth = gamePanel.tileSize * xScale;
        int spriteHeight = gamePanel.tileSize * yScale;

        String playerDirection;
        if (myPosition < playerToFollow.storedPosition.size() && playerToFollow.storedPosition.get(myPosition) != null) {
            Position pos = playerToFollow.storedPosition.get(myPosition);
            playerDirection = pos.direction;

            directions.clear();
            directions.add(playerDirection);
        }

        if (directions.contains("up")) {
            if (spriteNumber == 0 || spriteNumber == 2) {
                curSprite = up1;
            } else if (spriteNumber == 1) {
                curSprite = up2;
            } else if (spriteNumber == 3) {
                curSprite = up3;
            }
        }
        if (directions.contains("down")) {
            if (spriteNumber == 0 || spriteNumber == 2) {
                curSprite = down1;
            } else if (spriteNumber == 1) {
                curSprite = down2;
            } else if (spriteNumber == 3) {
                curSprite = down3;
            }
        }
        if (directions.contains("left")) {
            if (spriteNumber == 0 || spriteNumber == 2) {
                curSprite = left1;
            } else if (spriteNumber == 1) {
                curSprite = left2;
            } else if (spriteNumber == 3) {
                curSprite = left3;
            }
        }
        if (directions.contains("right")) {
            if (spriteNumber == 0 || spriteNumber == 2) {
                curSprite = right1;
            } else if (spriteNumber == 1) {
                curSprite = right2;
            } else if (spriteNumber == 3) {
                curSprite = right3;
            }
        }

        int playerScreenX = x - gamePanel.camera.x + gamePanel.camera.screenX;
        int playerScreenY = y - gamePanel.camera.y + gamePanel.camera.screenY;

        graphics2D.drawImage(curSprite, playerScreenX, playerScreenY, spriteWidth, spriteHeight, null);
    }

    @Override
    public int getDrawY() {
        return y + gamePanel.tileSize;
    }
}