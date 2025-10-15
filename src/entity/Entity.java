package entity;

import main.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

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
}
