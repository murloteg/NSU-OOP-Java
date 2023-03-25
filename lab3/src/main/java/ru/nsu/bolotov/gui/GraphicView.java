package ru.nsu.bolotov.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;

public class GraphicView {
    private final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private final Color standardBackgroundColor = new Color(0xFFD4ABFF, true);
    private final Font standardButtonFont = new Font("Menlo", Font.ITALIC, 18);
    private JFrame frame;
    private JPanel panel;

    public GraphicView() {
        createFrame();
    }

    public void prepareGameField() {
        clearPanel();
        frame.remove(panel);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D graphics2D = (Graphics2D) graphics;
                Font generalFont = new Font("Menlo", Font.BOLD, 36);
                graphics2D.setFont(generalFont);
                graphics2D.drawString("GAME FIELD", dimension.width / 2 - dimension.width / 19, dimension.height / 15);

                Rectangle2D rectangle = new Rectangle2D.Float(50, 50, 200, 200);
                graphics2D.draw(rectangle);
            }
        };
        panel.setBackground(standardBackgroundColor);
        panel.setLayout(null);
        addMenuButton();
        frame.add(panel);
    }

    public void showGameRules() {
        clearPanel();
        frame.remove(panel);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D graphics2D = (Graphics2D) graphics;
                Font generalFont = new Font("Menlo", Font.BOLD, 36);
                graphics2D.setFont(generalFont);
                graphics2D.drawString("GAME RULES", dimension.width / 2 - dimension.width / 19, dimension.height / 15);
                Font rulesFont = new Font("Menlo", Font.PLAIN, 20);
                graphics2D.setFont(rulesFont);
                graphics2D.drawString("1) Your goal is to open all cells without bombs.", dimension.width / 12, dimension.height / 10);
                graphics2D.drawString("2) When the game starts, you can do only these actions:", dimension.width / 12, dimension.height / 8);
                graphics2D.drawString("\t\t\t\t\t\t\t OPEN THE CELL (x, y);", dimension.width / 12, dimension.height / 8 + 35);
                graphics2D.drawString("\t\t\t\t\t\t\t PUT THE FLAG (x, y);", dimension.width / 12, dimension.height / 8 + 70);
                graphics2D.drawString("\t\t\t\t\t\t\t CLEAR THE CELL FROM THE FLAG (x, y);", dimension.width / 12, dimension.height / 8 + 105);
                graphics2D.drawString("3) You must finish as fast as you can!", dimension.width / 12, dimension.height / 8 + 145);
            }
        };
        panel.setBackground(standardBackgroundColor);
        panel.setLayout(null);
        addMenuButton();
        frame.add(panel);
    }

    public void updateGameField(JFrame frame) {

    }

    private void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    private void addMenuButton() {
        JButton menuButton = new JButton("Menu");
        menuButton.setFont(standardButtonFont);
        menuButton.setBounds(dimension.width / 15, dimension.height / 2 - dimension.height / 10, 200, 80);

        menuButton.addActionListener(event -> setDefaultPanelWithMenu());
        panel.add(menuButton);
    }

    private void createFrame() {
        frame = new JFrame();
        frame.setBounds(0, 0, dimension.width, dimension.height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Minesweeper");

        panel = new JPanel();
        frame.getContentPane().add(panel);
        setDefaultPanelWithMenu();
        frame.setVisible(true);
    }

    private void setDefaultPanelWithMenu() {
        clearPanel();
        frame.remove(panel);
        panel = new JPanel();

        panel.setBackground(standardBackgroundColor);
        panel.setLayout(null);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(standardButtonFont);
        newGameButton.setBounds(dimension.width / 8 + dimension.width / 9, dimension.height / 7, 200, 80);

        JButton aboutButton = new JButton("About");
        aboutButton.setFont(standardButtonFont);
        aboutButton.setBounds(dimension.width / 8 + dimension.width / 3, dimension.height / 7, 200, 80);

        JButton scoresButton = new JButton("High Scores");
        scoresButton.setFont(standardButtonFont);
        scoresButton.setBounds(dimension.width - dimension.width / 8 - dimension.width / 10 - dimension.width / 9, dimension.height / 7, 200, 80);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(standardButtonFont);
        exitButton.setBounds(dimension.width / 8 + dimension.width / 3, dimension.height / 2 - dimension.height / 10, 200, 80);

        newGameButton.addActionListener(event -> prepareGameField());
        aboutButton.addActionListener(event -> showGameRules());
        exitButton.addActionListener(event -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));

        panel.add(newGameButton);
        panel.add(aboutButton);
        panel.add(scoresButton);
        panel.add(exitButton);
        frame.add(panel);
    }
}
