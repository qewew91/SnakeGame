package game;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Snake Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 640);
        setResizable(false);
        add(new GameField());
        setLocation(400, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
