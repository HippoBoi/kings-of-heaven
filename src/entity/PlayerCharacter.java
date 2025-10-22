package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PlayerCharacter extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    Character character;

    public final int screenX;
    public final int screenY;
    public ArrayList<Position> storedPosition = new ArrayList<>();

    public PlayerCharacter(GamePanel gamePanel, KeyHandler keyHandler, Character character) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.character = character;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        final int hitboxX = gamePanel.tileSize - 32;
        final int hitboxY = gamePanel.tileSize + 24;
        final int hitboxWidth = gamePanel.tileSize - 32 - 4;
        final int hitboxHeight = gamePanel.tileSize - 32;
        hitbox = new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);

        setDefaultValues();
        getCharacterSprites(character);
    }

    public void setDefaultValues() {
        x = gamePanel.tileSize * 10;
        y = gamePanel.tileSize * 6;
        xScale = 1;
        yScale = 2;
        speed = 4;
        directions = new ArrayList<>(List.of("down"));
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

        if (directionX != 0 || directionY != 0) {
            x += directionX;
            y += directionY;

            String currentDirection = directions.getLast();
            storedPosition.addFirst(new Position(x, y, currentDirection));

            if (storedPosition.size() > 50) {
                storedPosition.removeLast();
            }
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

        if (gamePanel.DEBUG) {
            graphics2D.setColor(new Color(255, 0, 0, 180));
            graphics2D.fillRect(
                    playerScreenX + hitbox.x,
                    playerScreenY + hitbox.y,
                    hitbox.width,
                    hitbox.height
            );
        }
    }

    @Override
    public int getDrawY() {
        return y + gamePanel.tileSize;
    }
}
