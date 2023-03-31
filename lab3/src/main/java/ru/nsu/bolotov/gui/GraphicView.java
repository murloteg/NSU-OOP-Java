package ru.nsu.bolotov.gui;

import ru.nsu.bolotov.sharedlogic.field.Field;
import ru.nsu.bolotov.util.UtilConsts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GraphicView {
    private final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private final Color standardBackgroundColor = new Color(0xFFD4ABFF, true);
    private final Font standardButtonFont = new Font("Menlo", Font.ITALIC, 18);
    private final int fieldSize;
    private final JFrame frame;
    private JPanel panel;
    private ArrayList<JButton> cellsList;

    public GraphicView(int fieldSize) {
        this.fieldSize = fieldSize;
        frame = new JFrame();
        initializeFrame();
    }

    public void prepareGameField() {
        clearPanel();
        frame.remove(panel);
        cellsList = new ArrayList<>();
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D graphics2D = (Graphics2D) graphics;
                Font generalFont = new Font("Menlo", Font.BOLD, 36);
                graphics2D.setFont(generalFont);
                graphics2D.drawString("GAME FIELD", dimension.width / 2 - dimension.width / 19, dimension.height / 15);

                int sideSize = 800;
                Rectangle2D rectangle = new Rectangle2D.Float((float) dimension.width / 2 - (float) dimension.width / 5 - 30,
                                                            (float) dimension.height / 4 - (float) dimension.height / 7,
                                                                sideSize, sideSize);
                graphics2D.setStroke(new BasicStroke(4));
                graphics2D.draw(rectangle);
                int distanceBetweenColumns = sideSize / (fieldSize);
                for (int i = 0; i < fieldSize; ++i) {
                    Point2D verticalLineStart = new Point2D.Double(rectangle.getMinX() + i * distanceBetweenColumns, rectangle.getMaxY());
                    Point2D verticalLineEnd = new Point2D.Double(rectangle.getMinX() + i * distanceBetweenColumns, rectangle.getMinY());
                    Line2D verticalLine = new Line2D.Float(verticalLineStart, verticalLineEnd);
                    graphics2D.draw(verticalLine);

                    Point2D horizontalLineStart = new Point2D.Double(rectangle.getMinX(), rectangle.getMaxY() - i * distanceBetweenColumns);
                    Point2D horizontalLineEnd = new Point2D.Double(rectangle.getMaxX(), rectangle.getMaxY() - i * distanceBetweenColumns);
                    Line2D horizontalLine = new Line2D.Float(horizontalLineStart, horizontalLineEnd);
                    graphics2D.draw(horizontalLine);
                }
            }
        };

        int sideSize = 800;
        int distanceBetweenColumns = sideSize / (fieldSize);
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                JButton currentCell = new JButton("???");
                currentCell.setBounds((int) ((float) dimension.width / 2 - (float) dimension.width / 5 - 30 + i * distanceBetweenColumns) + 3, (int) ((float) dimension.height / 4 - (float) dimension.height / 7 + 4) + j * distanceBetweenColumns, distanceBetweenColumns - 5, distanceBetweenColumns - 5);
                currentCell.setToolTipText(j + " " + i);
                currentCell.addActionListener(event -> currentCell.setSelected(true));
                panel.add(currentCell);
                cellsList.add(currentCell);
            }
        }

        panel.setBackground(standardBackgroundColor);
        panel.setLayout(null);
        addMenuButton();
        frame.add(panel);
    }

    public void updateGameField(Field userField, Field logicField, long currentTime) {
        int[] userArrayOfFieldCells = userField.getArrayOfFieldCells();
        int[] logicArrayOfFieldCells = logicField.getArrayOfFieldCells();
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                int position = i * fieldSize + j;
                if (userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.OPEN) {
                    if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.BOMB) {
                        cellsList.get(i * fieldSize + j).setIcon(new ImageIcon("/Users/mac/IntelliJProjects/Java-Projects/lab3/src/main/resources/bomb.png"));
                        cellsList.get(i * fieldSize + j).setText("");
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.VISITED_EMPTY) {
                        cellsList.get(i * fieldSize + j).setText("0");
                    } else {
                        cellsList.get(i * fieldSize + j).setText(Integer.toString(logicArrayOfFieldCells[position]));
                    }
                } else if (userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.FLAGGED) {
                    cellsList.get(i * fieldSize + j).setText("FLAGGED");
                }
            }
        }
    }

    public String getNextActionAsString() {
        ImageIcon openCellIcon = new ImageIcon("/Users/mac/IntelliJProjects/Java-Projects/lab3/src/main/resources/dig128.png");
        ImageIcon putTheFlagIcon = new ImageIcon("/Users/mac/IntelliJProjects/Java-Projects/lab3/src/main/resources/flag128.png");
        ImageIcon clearTheCellIcon = new ImageIcon("/Users/mac/IntelliJProjects/Java-Projects/lab3/src/main/resources/clear128.png");

        JRadioButton openCell = new JRadioButton("OPEN");
        JRadioButton putTheFlag = new JRadioButton("FLAG");
        JRadioButton clearTheCell = new JRadioButton("CLEAR");

//        openCell.setIcon(openCellIcon);
        openCell.setActionCommand("OPEN");
        openCell.setFont(standardButtonFont);
        openCell.setSelected(true);
        openCell.setBounds(dimension.width / 15, 100, 256, 256);

//        putTheFlag.setIcon(putTheFlagIcon);
        putTheFlag.setActionCommand("FLAG");
        putTheFlag.setSelected(false);
        putTheFlag.setFont(standardButtonFont);
        putTheFlag.setBounds(dimension.width / 15, 330, 256, 256);

//        clearTheCell.setIcon(clearTheCellIcon);
        clearTheCell.setActionCommand("CLEAR");
        clearTheCell.setSelected(false);
        clearTheCell.setFont(standardButtonFont);
        clearTheCell.setBounds(dimension.width / 15, 530, 256, 256);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(openCell);
        buttonGroup.add(putTheFlag);
        buttonGroup.add(clearTheCell);

        openCell.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }
        });
        putTheFlag.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                putTheFlag.setSelected(true);
            }
        });
        clearTheCell.addActionListener(event -> clearTheCell.setSelected(true));

        panel.add(openCell);
        panel.add(putTheFlag);
        panel.add(clearTheCell);

        String selectedPosition = "";
        while (selectedPosition.isEmpty()) {
            for (JButton button : cellsList) {
                if (button.isSelected()) {
                    selectedPosition = button.getToolTipText();
                    button.setSelected(false);
                    break;
                }
            }
        }

        String actionType = "";
        while (actionType.isEmpty()) {
            if (openCell.isSelected()) {
                actionType = openCell.getActionCommand();
            } else if (putTheFlag.isSelected()) {
                actionType = putTheFlag.getActionCommand();
            } else if (clearTheCell.isSelected()) {
                actionType = clearTheCell.getActionCommand();
            }
        }
        return actionType + " " + selectedPosition;
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

    private void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    private void addMenuButton() {
        JButton menuButton = new JButton("Menu");
        menuButton.setFont(standardButtonFont);
        menuButton.setBounds(dimension.width / 15, dimension.height - dimension.height / 5, 200, 80);

        menuButton.addActionListener(event -> setDefaultPanelWithMenu());
        panel.add(menuButton);
    }

    private void initializeFrame() {
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
