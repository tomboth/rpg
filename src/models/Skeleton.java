package models;

public class Skeleton extends GameCharacter {
    public Skeleton(int x, int y, int level) {
        this.level = level;
        this.coordinateX = x;
        this.coordinateY = y;
        hp = 2* level * d6();
        dp = level/2 * d6();
        sp = level * d6();
    }

    @Override
    public String toString() {
        return "[" + (coordinateX+1)  + "," + (coordinateY+1) +
                "]   Skeleton  (Level " + level + ") HP: " + hp + " | dp: "
                + dp + " | Sp: " +sp ;
    }
}
