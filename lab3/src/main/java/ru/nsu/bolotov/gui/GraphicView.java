package ru.nsu.bolotov.gui;

import ru.nsu.bolotov.sharedlogic.field.Field;
import ru.nsu.bolotov.util.UtilConsts;
import ru.nsu.bolotov.exceptions.gui.InvalidImagePathException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphicView {
    private final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private final Color standardBackgroundColor = new Color(0xFFD4ABFF, true);
    private final Font standardFont = new Font("Menlo", Font.ITALIC, 18);
    private final int fieldSize;
    private final JFrame frame;
    private JPanel panel;
    private JLabel timerLabel;
    private ArrayList<JButton> cellsList;
    private boolean isGameLaunched;

    public GraphicView(int fieldSize) {
        this.fieldSize = fieldSize;
        isGameLaunched = false;
        frame = new JFrame();
        initializeFrame();
    }

    public boolean getStatusOfGameLaunch() {
        return isGameLaunched;
    }

    public void prepareGameField() {
        clearPanel();
        frame.remove(panel);
        cellsList = new ArrayList<>();
        isGameLaunched = true;
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

                BufferedImage openCellImage;
                BufferedImage putTheFlagImage;
                BufferedImage clearTheCellImage;
                try {
                    openCellImage = ImageIO.read(new File(ImagePathFinder.findPath("OPEN")));
                    putTheFlagImage = ImageIO.read(new File(ImagePathFinder.findPath("FLAG")));
                    clearTheCellImage = ImageIO.read(new File(ImagePathFinder.findPath("CLEAR")));
                } catch (IOException e) {
                    throw new InvalidImagePathException();
                }

                graphics2D.setFont(standardFont);
                graphics2D.drawImage(openCellImage, dimension.width / 15, 175, this);
                graphics2D.drawString("Open", dimension.width / 15 + openCellImage.getWidth() / 3, 330);

                graphics2D.drawImage(putTheFlagImage, dimension.width / 15, 400, this);
                graphics2D.drawString("Put the flag", dimension.width / 15, 550);

                graphics2D.drawImage(clearTheCellImage, dimension.width / 15, 600, this);
                graphics2D.drawString("Clear the cell", dimension.width / 15 - clearTheCellImage.getWidth() / 10, 750);
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
        timerLabel = new JLabel();
        timerLabel.setText("0");
        timerLabel.setFont(new Font("Menlo", Font.BOLD, 100));
        timerLabel.setBounds(dimension.width / 2 + dimension.width / 3 + dimension.width / 35, dimension.height / 8, 120, 120);
        panel.add(timerLabel);
        frame.add(panel);
    }

    public void updateGameField(Field userField, Field logicField, long currentTime) {
        int[] userArrayOfFieldCells = userField.getArrayOfFieldCells();
        int[] logicArrayOfFieldCells = logicField.getArrayOfFieldCells();
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                int position = i * fieldSize + j;
                if (userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.OPEN) {
                    cellsList.get(i * fieldSize + j).setText("");
                    if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.BOMB) {
                        cellsList.get(i * fieldSize + j).setIcon(new ImageIcon(ImagePathFinder.findPath("BOMB")));
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.ONE_NEIGHBOR) {
                        cellsList.get(i * fieldSize +j).setIcon(new ImageIcon(ImagePathFinder.findPath("ONE")));
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.TWO_NEIGHBORS) {
                        cellsList.get(i * fieldSize +j).setIcon(new ImageIcon(ImagePathFinder.findPath("TWO")));
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.THREE_NEIGHBORS) {
                        cellsList.get(i * fieldSize +j).setIcon(new ImageIcon(ImagePathFinder.findPath("THREE")));
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.FOUR_NEIGHBORS) {
                        cellsList.get(i * fieldSize +j).setIcon(new ImageIcon(ImagePathFinder.findPath("FOUR")));
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.FIVE_NEIGHBORS) {
                        cellsList.get(i * fieldSize +j).setIcon(new ImageIcon(ImagePathFinder.findPath("FIVE")));
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.SIX_NEIGHBORS) {
                        cellsList.get(i * fieldSize +j).setIcon(new ImageIcon(ImagePathFinder.findPath("SIX")));
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.SEVEN_NEIGHBORS) {
                        cellsList.get(i * fieldSize +j).setIcon(new ImageIcon(ImagePathFinder.findPath("SEVEN")));
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.EIGHT_NEIGHBORS) {
                        cellsList.get(i * fieldSize +j).setIcon(new ImageIcon(ImagePathFinder.findPath("EIGHT")));
                    } else {
                        cellsList.get(i * fieldSize +j).setIcon(new ImageIcon(ImagePathFinder.findPath("ZERO")));
                    }
                } else if (userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.FLAGGED) {
                    cellsList.get(i * fieldSize + j).setIcon(new ImageIcon(ImagePathFinder.findPath("CELL_FLAG")));
                    cellsList.get(i * fieldSize + j).setText("");
                } else if (userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.CLEARED) {
                    cellsList.get(i * fieldSize + j).setIcon(null);
                    cellsList.get(i * fieldSize + j).setText("???");
                }
            }
        }
        timerLabel.setText(Long.toString(currentTime));
    }

    public String getNextActionAsString() {
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

        ImageIcon dialogIcon = new ImageIcon(ImagePathFinder.findPath("QUESTION"));
        String[] options = {"CLEAR", "FLAG", "OPEN"};
        Map<Integer, String> definitionMap = new HashMap<>();
        int position = 0;
        for (String option : options) {
            definitionMap.put(position, option);
            ++position;
        }

        int selectedAction = JOptionPane.showOptionDialog(
                panel,
                "Select next action",
                "YOUR TURN",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                dialogIcon,
                options,
                null);

        return definitionMap.get(selectedAction) + " " + selectedPosition;
    }

    public void displayExceptionInfo(String message) {
        ImageIcon errorIcon = new ImageIcon(ImagePathFinder.findPath("ERROR"));
        JOptionPane.showMessageDialog(panel, message, "Exception!", JOptionPane.ERROR_MESSAGE, errorIcon);
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
        menuButton.setFont(standardFont);
        menuButton.setBounds(dimension.width / 15, dimension.height - dimension.height / 5, 200, 80);

        menuButton.addActionListener(event -> {
            setDefaultPanelWithMenu();
            isGameLaunched = false;
        });
        // TODO: add dialog window.
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
        newGameButton.setFont(standardFont);
        newGameButton.setBounds(dimension.width / 8 + dimension.width / 9, dimension.height / 7, 200, 80);

        JButton aboutButton = new JButton("About");
        aboutButton.setFont(standardFont);
        aboutButton.setBounds(dimension.width / 8 + dimension.width / 3, dimension.height / 7, 200, 80);

        JButton scoresButton = new JButton("High Scores");
        scoresButton.setFont(standardFont);
        scoresButton.setBounds(dimension.width - dimension.width / 8 - dimension.width / 10 - dimension.width / 9, dimension.height / 7, 200, 80);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(standardFont);
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
