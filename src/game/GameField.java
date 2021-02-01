package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 640;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 800;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left;
    private boolean right = true;
    private boolean up;
    private boolean down;
    private boolean inGame = true;


    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(125, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(40) * DOT_SIZE;
        appleY = new Random().nextInt(40) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iip = new ImageIcon("dot.png");
        dot = iip.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String message = "Game Over";
            g.setColor(Color.WHITE);
            g.drawString(message, 250, SIZE / 2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        else if (right) {
            x[0] += DOT_SIZE;
        }
        else if (up) {
            y[0] -= DOT_SIZE;
        }
        else if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }
    private void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if(x[0] == SIZE) {
            inGame = false;
        }
        else if(x[0] == 0) {
            inGame = false;
        }
        else if(y[0] == SIZE) {
            inGame = false;
        }
        else if(y[0] == 0) {
            inGame = false;
        }
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_A && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_D && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_W && !down) {
                up = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_S && !up) {
                down = true;
                right = false;
                left = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

}
