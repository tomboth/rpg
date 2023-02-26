package models;

public abstract class GameCharacter {
    public int coordinateX;
    public int coordinateY;
    public boolean isAlive = true;
    public int hp;
    public int maxHp;
    public int dp;
    public int sp;
    public int level = 1;
    public boolean hasKey;

    public int d6 (){
        return (int) (Math.random()*6 + 1);
    }
}
