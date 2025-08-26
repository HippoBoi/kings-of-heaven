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

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

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
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_up.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_up_walk_01.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_up_walk_02.png")));

            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_down.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_down_walk_01.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_down_walk_02.png")));

            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_left.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_left_walk_01.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_left_walk_02.png")));

            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_right.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_right_walk_01.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rhay/rhay_right_walk_02.png")));
        } catch (IOException e) {
            // TODO handle error
        }
    }

    public void update() {
        if (keyHandler.upPressed) {
            direction = "up";
            y -= speed;
        }
        if (keyHandler.downPressed) {
            direction = "down";
            y += speed;
        }
        if (keyHandler.leftPressed) {
            direction = "left";
            x -= speed;
        }
        if (keyHandler.rightPressed) {
            direction = "right";
            x += speed;
        }

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
