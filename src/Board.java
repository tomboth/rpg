import models.GameMap;
import services.CharacterService;
import services.DrawService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JComponent implements KeyListener {

    private int unit;
    public GameMap gameMap;
    public DrawService drawService;
    public CharacterService characterService;

    public Board() {
        unit = 72;
        setPreferredSize(new Dimension(720, 864));
        setVisible(true);
        gameMap = new GameMap(unit, 1);
        characterService = new CharacterService(gameMap);
        drawService = new DrawService(gameMap);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RPG Game");
        Board board = new Board();
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.addKeyListener(board);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        drawService.gameMap = gameMap;
        drawService.drawTiles(graphics);
        drawService.drawSkeletons(graphics);
        drawService.drawHero(graphics);
        drawService.drawBoss(graphics);
        drawService.drawStats(graphics);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            characterService.move("up");
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            characterService.move("down");
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            characterService.move("left");
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            characterService.move("right");
        }
        this.gameMap = characterService.gameMap;
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

}