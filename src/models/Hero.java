package models;

public class Hero extends GameCharacter {

    public String heroDown = "img/hero-down.png";
    public String heroUp = "img/hero-up.png";
    public String heroLeft = "img/hero-left.png";
    public String heroRight = "img/hero-right.png";
    public String heroLink = heroDown;

    public Hero() {
        this.coordinateX = 0;
        this.coordinateY = 0;
        maxHp = 20 + 3 * d6();
        hp = maxHp;
        dp = 2 * d6();
        sp = 1 + d6();
    }

    public void levelUp() {
        level++;
        maxHp += d6();
        dp += d6();
        sp += d6();
    }

    @Override
    public String toString() {
        return "[" + (coordinateX + 1) + "," + (coordinateY + 1) + "]   Hero (Level " + level + ") HP: " + hp + "/" + maxHp + " | DP: " + dp + " | Sp: " + sp + ((hasKey) ? " has key" : " no key");
    }
}
