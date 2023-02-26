package services;

import models.GameCharacter;
import models.GameMap;
import models.Hero;

public class CharacterService {
    public GameMap gameMap;
    Hero hero;

    public CharacterService(GameMap gameMap) {
        this.gameMap = gameMap;
        hero = this.gameMap.hero;
    }

    public void move(String direction) {
        if (direction == "up") {
            if (hero.coordinateY > 0 && !gameMap.tileArray[hero.coordinateX][hero.coordinateY - 1].isWall) {
                hero.coordinateY--;
            }
            hero.heroLink = hero.heroUp;
        } else if (direction == "down") {
            if (hero.coordinateY < 9 && !gameMap.tileArray[hero.coordinateX][hero.coordinateY + 1].isWall) {
                hero.coordinateY++;
            }
            hero.heroLink = hero.heroDown;
        } else if (direction == "left") {
            if (hero.coordinateX > 0 && !gameMap.tileArray[hero.coordinateX - 1][hero.coordinateY].isWall) {
                hero.coordinateX--;
            }
            hero.heroLink = hero.heroLeft;
        } else if (direction == "right") {
            if (hero.coordinateX < 9 && !gameMap.tileArray[hero.coordinateX + 1][hero.coordinateY].isWall) {
                hero.coordinateX++;
            }
            hero.heroLink = hero.heroRight;
        }
        checkIfTwoCharactersOnSameTile();
    }

    private void checkIfTwoCharactersOnSameTile() {
        if (hero == null) return;
        if (gameMap.boss != null) {
            if (hero.coordinateX == gameMap.boss.coordinateX && hero.coordinateY == gameMap.boss.coordinateY) {
                fight(hero, gameMap.boss, -1);
            }
        }
        for (int i = 0; i < gameMap.skeletonList.size(); i++) {
            if (hero.coordinateX == gameMap.skeletonList.get(i).coordinateX && hero.coordinateY == gameMap.skeletonList.get(i).coordinateY) {
                fight(hero, gameMap.skeletonList.get(i), i);
            }
        }
    }

    private void fight(Hero hero, GameCharacter npc, int index) {
        while (isEveryoneAlive(hero, npc)) {
            attack(hero, npc);
            if (isEveryoneAlive(hero, npc)) {
                attack(npc, hero);
            }
        }
        if (hero.isAlive) {
            hero.levelUp();
            if (index == -1) {
                gameMap.boss = null;
            } else {
                if (gameMap.skeletonList.get(index).hasKey) {
                    hero.hasKey = true;
                }
                gameMap.skeletonList.remove(index);
            }
            if (gameMap.boss == null && gameMap.hero.hasKey) moveToNextLevel();
        } else {
            endGame();
        }
    }

    public void attack(GameCharacter attacker, GameCharacter defender) {
        int sv = attacker.sp + attacker.d6() * 2;
        if (sv > defender.dp) {
            defender.hp = defender.hp - (Math.max(0, (sv - defender.dp)));
        }
        if (defender.hp <= 0) {
            defender.isAlive = false;
        }
    }

    public boolean isEveryoneAlive(Hero hero, GameCharacter npc) {
        return (hero.isAlive && npc.isAlive);
    }

    public void moveToNextLevel() {
        int level = gameMap.level;
        gameMap = new GameMap(72, level + 1);
        gameMap.hero = hero;
        gameMap.hero.hasKey = false;
        gameMap.hero.coordinateX = 0;
        gameMap.hero.coordinateY = 0;
        gameMap.hero.heroLink = gameMap.hero.heroDown;
    }

    public void endGame() {
        gameMap.hero = null;
    }
}
