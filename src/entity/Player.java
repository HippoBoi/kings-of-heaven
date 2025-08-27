package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    GameCharacter gameCharacter;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, GameCharacter gameCharacter) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.gameCharacter = gameCharacter;

        setDefaultValues();
        getPlayerSprites();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        xScale = 1;
        yScale = 2;
        speed = 4;
        direction = "down";
    }

    public void getPlayerSprites() {
        try {
            String path = "/" + gameCharacter.name + "/" + gameCharacter.name;

            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_up.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_up_walk_01.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_up_walk_02.png")));

            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_down.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_down_walk_01.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_down_walk_02.png")));

            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_left.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_left_walk_01.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_left_walk_02.png")));

            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_right.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_right_walk_01.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "_right_walk_02.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        int directionX = 0;
        int directionY = 0;

        if (keyHandler.upPressed) {
            direction = "up";
            directionY -= 1;
        }
        if (keyHandler.downPressed) {
            direction = "down";
            directionY += 1;
        }
        if (keyHandler.leftPressed) {
            direction = "left";
            directionX -= 1;
        }
        if (keyHandler.rightPressed) {
            direction = "right";
            directionX += 1;
        }

        double length = Math.sqrt((directionX * directionX) + (directionY * directionY));
        if (length != 0) {
            directionX = (int) Math.round(directionX / length * speed);
            directionY = (int) Math.round(directionY / length * speed);
        }

        x += directionX;
        y += directionY;

        isMoving = keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;
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
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage curSprite = null;
        int spriteWidth = gamePanel.tileSize * xScale;
        int spriteHeight = gamePanel.tileSize * yScale;

        switch (direction) {
            case "up":
                if (spriteNumber == 0 || spriteNumber == 2) {
                    curSprite = up1;
                } else if (spriteNumber == 1) {
                    curSprite = up2;
                } else if (spriteNumber == 3) {
                    curSprite = up3;
                }

                break;
            case "down":
                if (spriteNumber == 0 || spriteNumber == 2) {
                    curSprite = down1;
                } else if (spriteNumber == 1) {
                    curSprite = down2;
                } else if (spriteNumber == 3) {
                    curSprite = down3;
                }

                break;
            case "left":
                if (spriteNumber == 0 || spriteNumber == 2) {
                    curSprite = left1;
                } else if (spriteNumber == 1) {
                    curSprite = left2;
                } else if (spriteNumber == 3) {
                    curSprite = left3;
                }

                break;
            case "right":
                if (spriteNumber == 0 || spriteNumber == 2) {
                    curSprite = right1;
                } else if (spriteNumber == 1) {
                    curSprite = right2;
                } else if (spriteNumber == 3) {
                    curSprite = right3;
                }

                break;
        }

        graphics2D.drawImage(curSprite, x, y, spriteWidth, spriteHeight, null);
    }
}
