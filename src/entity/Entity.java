package entity;

import main.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Entity implements Drawable {
    public int x, y;
    public int xScale, yScale;
    public int speed;
    public int spriteCounter;
    public int spriteNumber;

    public BufferedImage up1, up2, up3;
    public BufferedImage down1, down2, down3;
    public BufferedImage left1, left2, left3;
    public BufferedImage right1, right2, right3;

    public Boolean isMoving;

    public List<String> directions;

    public Rectangle hitbox;
    public boolean isColliding = false;
    public boolean collidingUp = false;
    public boolean collidingDown = false;
    public boolean collidingLeft = false;
    public boolean collidingRight = false;

    @Override
    public int getDrawY() {
        return y;
    }

    @Override
    public void draw(Graphics2D g2) {}

    public void getCharacterSprites(Character character) {
        try {
            String path = "/" + character.name + "/" + character.name;

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
}
