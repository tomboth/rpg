package models;

public class Boss extends GameCharacter {

    public Boss(int level) {
        this.level = level;
        hp = 2 * level * d6() + d6();
        dp = level/2 * d6() + d6()/2;
        sp = level * d6() + level;
    }

    @Override
    public String toString() {
        return "[" + (coordinateX+1)  + "," + (coordinateY+1) +
                "]   Boss  (Level " + level + ") HP: " + hp + " | dp: "
                + dp + " | Sp: " +sp ;
    }
}
