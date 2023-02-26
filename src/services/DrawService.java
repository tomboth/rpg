package services;

import models.GameCharacter;
import models.GameMap;
import models.Skeleton;

import java.awt.*;

public class DrawService {
    public GameMap gameMap;
    public String floorLink = "img/floor.png";
    public String wallLink = "img/wall.png";
    public String skeletonLink = "img/skeleton.png";
    public String bossLink = "img/boss.png";

    public DrawService(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void drawTiles(Graphics graphics) {
        PositionedImage floor = new PositionedImage(floorLink, 0, 0);
        PositionedImage wall = new PositionedImage(wallLink, 0, 0);
        for (int i = 0; i < gameMap.tileArray.length; i++) {
            for (int j = 0; j < gameMap.tileArray[i].length; j++) {
                if (gameMap.tileArray[i][j].isWall) {
                    wall.posX = gameMap.tileArray[i][j].coordinateX;
                    wall.posY = gameMap.tileArray[i][j].coordinateY;
                    wall.draw(graphics);
                } else {
                    floor.posX = gameMap.tileArray[i][j].coordinateX;
                    floor.posY = gameMap.tileArray[i][j].coordinateY;
                    floor.draw(graphics);
                }
            }
        }
    }

    public void drawSkeletons(Graphics graphics) {
        for (Skeleton skeleton : gameMap.skeletonList) {
            PositionedImage skeletonImage = new PositionedImage(skeletonLink,
                    skeleton.coordinateX * gameMap.unit,
                    skeleton.coordinateY * gameMap.unit);
            skeletonImage.draw(graphics);
        }
    }

    public void drawHero(Graphics graphics) {
        if (gameMap.hero == null) return;
        PositionedImage heroImage = new PositionedImage(gameMap.hero.heroLink,
                gameMap.hero.coordinateX * gameMap.unit,
                gameMap.hero.coordinateY * gameMap.unit);
        heroImage.draw(graphics);
    }

    public void drawBoss(Graphics graphics) {
        if (gameMap.boss == null) return;
        PositionedImage bossImage = new PositionedImage(bossLink,
                gameMap.boss.coordinateX * gameMap.unit,
                gameMap.boss.coordinateY * gameMap.unit);
        bossImage.draw(graphics);
    }

    public void drawStats(Graphics graphics) {
        Font myFont = new Font("Courier New", 1, 17);
        graphics.setFont(myFont);
        if (gameMap.hero == null) {
            String dead = "OH NO! YOU DIED!!!";
            graphics.drawString(dead, 10, 756);
        } else {
            graphics.drawString(gameMap.hero.toString(), 10, 756);
            printNearbyMonsters(graphics);
        }
    }

    public void printNearbyMonsters(Graphics graphics) {
        int x = 10;
        int y = 792;
        if (gameMap.boss != null) {
            y = printMonster(graphics, gameMap.boss, x, y);
        }
        for (Skeleton skeleton : gameMap.skeletonList) {
            y = printMonster(graphics, skeleton, x, y);
        }
    }

    public int printMonster(Graphics graphics, GameCharacter monster, int x, int y) {
        int heroX = gameMap.hero.coordinateX;
        int heroY = gameMap.hero.coordinateY;
        int monsterX = monster.coordinateX;
        int monsterY = monster.coordinateY;
        if (Math.abs(heroX - monsterX) <= 1 && Math.abs(heroY - monsterY) <= 1) {
            graphics.drawString(monster.toString(), x, y);
            y += 36;
        }
        if      ((heroX == monsterX
                && Math.abs(heroY - monsterY) == 2
                && !gameMap.tileArray[monsterX][(heroY + monsterY) / 2].isWall) ||
                (heroY == monsterY
                        && Math.abs(heroX - monsterX) == 2
                        && !gameMap.tileArray[(heroX + monsterX) / 2][monsterY].isWall)) {
            graphics.drawString(monster.toString(), x, y);
            y += 36;
        }
        return y;
    }
}
