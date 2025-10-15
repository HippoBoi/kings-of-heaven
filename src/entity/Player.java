package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    GameCharacter gameCharacter;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, GameCharacter gameCharacter) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.gameCharacter = gameCharacter;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        final int hitboxX = gamePanel.tileSize - 32;
        final int hitboxY = gamePanel.tileSize + 16;
        final int hitboxWidth = gamePanel.tileSize - 32 - 4;
        final int hitboxHeight = gamePanel.tileSize - 16 - 4;
        hitbox = new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);

        setDefaultValues();
        getPlayerSprites();
    }

    public void setDefaultValues() {
        x = gamePanel.tileSize * 10;
        y = gamePanel.tileSize * 6;
        xScale = 1;
        yScale = 2;
        speed = 4;
        directions = new ArrayList<String>(List.of("down"));
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

        String lastDirection = directions.getLast();
        directions.clear();
        directions.add(lastDirection);

        if (keyHandler.upPressed) {
            directionY -= speed;
            directions.add("up");
        }
        if (keyHandler.downPressed) {
            directionY += speed;
            directions.add("down");
        }
        if (keyHandler.leftPressed) {
            directionX -= speed;
            directions.add("left");
        }
        if (keyHandler.rightPressed) {
            directionX += speed;
            directions.add("right");
        }

        isColliding = false;
        gamePanel.collisionChecker.checkTile(this);

        if ((directionX < 0 && collidingLeft) || (directionX > 0 && collidingRight)) {
            int storedDirection = directionX;
            directionX = 0;

            if (collidingUp || collidingDown) {
                directionX = storedDirection;
            }
        }

        if ((directionY < 0 && collidingUp) || (directionY > 0 && collidingDown)) {
            directionY = 0;
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

        graphics2D.drawImage(curSprite, screenX, screenY, spriteWidth, spriteHeight, null);

        if (gamePanel.DEBUG) {
            graphics2D.setColor(new Color(255, 0, 0, 100));
            graphics2D.fillRect(
                    screenX + hitbox.x,
                    screenY + hitbox.y,
                    hitbox.width,
                    hitbox.height
            );
        }
    }
}
