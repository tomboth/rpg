package models;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    public int level;
    public int unit;
    public Tile[][] tileArray;
    public Hero hero;
    public Boss boss;
    public List<Skeleton> skeletonList;

    public GameMap(int unit, int level) {
        this.unit = unit;
        this.level = level;
        setUpGameMap();
    }

    private void setUpGameMap() {
        tileArray = new Tile[10][10];
        fillTileArrayCoordinates();
        setWallTiles();
        placeHero();
        placeSkeletons();
        placeBoss();
    }

    private void placeBoss() {
        boss = new Boss(level);
        int c = 0;
        do {
            int x = (int) (Math.random() * tileArray.length);
            int y = (int) (Math.random() * tileArray[0].length);
            if (!tileArray[x][y].isWall && !tileArray[x][y].hasSkeleton && !tileArray[x][y].hasHero) {
                tileArray[x][y].hasBoss = true;
                this.boss.coordinateX = x;
                this.boss.coordinateY = y;
                c++;
            }
        } while (c < 1);
    }

    private void placeHero() {
        hero = new Hero();
        tileArray[0][0].hasHero = true;
    }

    private void placeSkeletons() {
        skeletonList = new ArrayList<>();
        int numberOfSkeletons = level + 2;
        while (numberOfSkeletons > 0) {
            int x = (int) (Math.random() * tileArray.length);
            int y = (int) (Math.random() * tileArray[0].length);
            if (!tileArray[x][y].isWall && !tileArray[x][y].hasSkeleton && !tileArray[x][y].hasHero) {
                tileArray[x][y].hasSkeleton = true;
                skeletonList.add(new Skeleton(x, y, level));
                numberOfSkeletons--;
            }
        }
        skeletonList.get(0).hasKey = true;
    }

    private void setWallTiles() {
        int[][] wallCoordinates = {{0, 3}, {1, 3}, {1, 5}, {1, 7}, {1, 8}, {2, 1}, {2, 2}, {2, 3}, {2, 5}, {2, 7}, {2, 8},
                {3, 5}, {4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 5}, {4, 6}, {4, 7}, {4, 8},
                {5, 1}, {5, 3}, {6, 1}, {6, 3}, {6, 5}, {6, 6}, {6, 8},
                {7, 5}, {7, 6}, {7, 8}, {8, 1}, {8, 2}, {8, 3}, {8, 8}, {9, 3}, {9, 5}, {9, 6}};
        for (int i = 0; i < wallCoordinates.length; i++) {
            tileArray[wallCoordinates[i][1]][wallCoordinates[i][0]].isWall = true;
        }
    }

    // TODO> this is position, not coordinate
    private void fillTileArrayCoordinates() {
        int coordX = 0;
        int coordY = 0;
        for (int i = 0; i < tileArray.length; i++) {
            for (int j = 0; j < tileArray[i].length; j++) {
                tileArray[i][j] = new Tile();
                tileArray[i][j].coordinateX = coordX;
                tileArray[i][j].coordinateY = coordY;
                coordY += unit;
            }
            coordX += unit;
            coordY = 0;
        }
    }
}
