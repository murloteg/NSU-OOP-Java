package ru.nsu.bolotov.view.gui;

import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.sharedlogic.field.Field;
import ru.nsu.bolotov.sharedlogic.scores.HighScoresFinder;
import ru.nsu.bolotov.sharedlogic.scores.HighScoresSorter;
import ru.nsu.bolotov.sharedlogic.scores.ScoreCalculator;
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GraphicView {
    private final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private final Color standardBackgroundColor = new Color(0xC3FBD8);
    private final Color additionalBackgroundColor = new Color(0xFFFADD);
    private final Font standardFont = new Font("Menlo", Font.ITALIC, 18);
    private final int fieldSize;
    private final int numberOfBombs;
    private final JFrame frame;
    private JPanel panel;
    private JLabel timerLabel;
    private ArrayList<JButton> cellsList;
    private boolean isGameLaunched;
    private String username = "default";

    public GraphicView(int fieldSize, int numberOfBombs) {
        this.fieldSize = fieldSize;
        this.numberOfBombs = numberOfBombs;
        isGameLaunched = false;
        frame = new JFrame();
        initializeFrame();
    }

    public boolean isGameLaunched() {
        return isGameLaunched;
    }

    public void prepareGameField() {
        clearPanel();
        frame.remove(panel);
        cellsList = new ArrayList<>();

        float rectangleMinX = (float) dimension.width / 2 - (float) dimension.width / 5 - 30;
        float rectangleMinY = (float) dimension.height / 4 - (float) dimension.height / 7;
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D graphics2D = (Graphics2D) graphics;
                Font generalFont = new Font("Menlo", Font.BOLD, 36);
                graphics2D.setFont(generalFont);
                graphics2D.drawString("GAME FIELD", dimension.width / 2 - dimension.width / 19, dimension.height / 15);

                int sideSize = 800;
                Rectangle2D rectangle = new Rectangle2D.Float(rectangleMinX, rectangleMinY, sideSize, sideSize);
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
                currentCell.setBounds((int) (rectangleMinX + (float) distanceBetweenColumns / 20 + i * distanceBetweenColumns),
                        (int) (rectangleMinY + (float) distanceBetweenColumns / 20 + j * distanceBetweenColumns),
                        distanceBetweenColumns - 5, distanceBetweenColumns - 5);
                currentCell.setToolTipText(j + " " + i);
                currentCell.addActionListener(event -> {
                    currentCell.setSelected(true);
                });
                panel.add(currentCell);
                cellsList.add(currentCell);
            }
        }

        panel.setBackground(standardBackgroundColor);
        panel.setLayout(null);
        addMenuButton();

        JLabel timerDescription = new JLabel();
        timerDescription.setText("Current time:");
        timerDescription.setFont(new Font("Menlo", Font.BOLD, 40));
        timerDescription.setBounds(dimension.width - dimension.width / 5 - dimension.width / 30, dimension.height / 10, 400, 40);
        panel.add(timerDescription);

        timerLabel = new JLabel();
        timerLabel.setText("0");
        timerLabel.setFont(new Font("Menlo", Font.BOLD, 100));
        timerLabel.setBounds(dimension.width / 2 + dimension.width / 4 + dimension.width / 15, dimension.height / 6, 180, 100);
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
            Optional<JButton> optionalJButton = findSelectedButtonInArray();
            if (optionalJButton.isPresent()) {
                JButton currentButton = optionalJButton.get();
                selectedPosition = currentButton.getToolTipText();
                currentButton.setSelected(false);
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
        JOptionPane.showMessageDialog(panel, message, "Warning!", JOptionPane.ERROR_MESSAGE, errorIcon);
    }

    public void displayGameStatus(boolean defeatCondition, Field userField, Field logicField, long currentTime) {
        int[] userArrayOfFieldCells = userField.getArrayOfFieldCells();
        int[] logicArrayOfFieldCells = logicField.getArrayOfFieldCells();
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                int position = i * fieldSize + j;
                if ((userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.HIDDEN
                        || userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.FLAGGED)
                        && logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.BOMB) {
                    cellsList.get(i * fieldSize + j).setIcon(new ImageIcon(ImagePathFinder.findPath("DEFUSED_BOMB")));
                    cellsList.get(i * fieldSize + j).setText("");
                }
            }
        }

        timerLabel.setText(Long.toString(currentTime));
        ImageIcon dialogIcon = new ImageIcon(ImagePathFinder.findPath("QUESTION"));
        if (defeatCondition) {
            ImageIcon defeatIcon = new ImageIcon(ImagePathFinder.findPath("DEFEAT"));
            JOptionPane.showMessageDialog(panel, "DEFEAT", "Game status", JOptionPane.INFORMATION_MESSAGE, defeatIcon);
            int answer = JOptionPane.showOptionDialog(panel,
                    "Do you want to return to the menu?",
                    null,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    dialogIcon,
                    null,
                    null);
            if (answer == JOptionPane.YES_OPTION) {
                setDefaultPanelWithMenu();
                frame.revalidate();
                frame.repaint();
            }
        } else {
            ImageIcon victoryIcon = new ImageIcon(ImagePathFinder.findPath("VICTORY"));
            JOptionPane.showMessageDialog(panel, "VICTORY", "Game status", JOptionPane.INFORMATION_MESSAGE, victoryIcon);
            int answer = JOptionPane.showOptionDialog(panel,
                    "Do you want to save your result?",
                    null,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    dialogIcon,
                    null,
                    null);
            if (answer == JOptionPane.YES_OPTION) {
                JTextField inputName = new JTextField();
                inputName.setFont(standardFont);
                inputName.setBackground(additionalBackgroundColor);
                inputName.setBounds(dimension.width - dimension.width / 5, 400, 225, 80);

                JButton confirmName = new JButton();
                confirmName.setText("Confirm Username");
                confirmName.setFont(standardFont);
                confirmName.setBounds(dimension.width - dimension.width / 5, 500, 225, 80);

                confirmName.addActionListener(event -> {
                    if (!inputName.getText().isEmpty()) {
                        username = inputName.getText();
                    }
                    addScoreToTable();
                    setDefaultPanelWithMenu();
                    frame.revalidate();
                    frame.repaint();
                });

                panel.add(inputName);
                panel.add(confirmName);
                panel.revalidate();
                panel.repaint();

                UsernameWaiter usernameWaiter = new UsernameWaiter(confirmName);
                Thread usernameWaiterThread = new Thread(usernameWaiter);
                usernameWaiterThread.start();
                while (!usernameWaiter.isButtonWasActivated()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(exception);
                    }
                }
            }
        }
    }

    private void showGameRules() {
        clearPanel();
        frame.remove(panel);
        Font customFont = new Font("Menlo", Font.PLAIN, 20);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D graphics2D = (Graphics2D) graphics;
                Font generalFont = new Font("Menlo", Font.BOLD, 45);
                graphics2D.setFont(generalFont);
                graphics2D.drawString("GAME RULES", dimension.width / 2 - dimension.width / 19, dimension.height / 15);
                graphics2D.setFont(customFont);
                graphics2D.drawString("1) Your goal is to open all cells without bombs.", dimension.width / 12, dimension.height / 8);
                graphics2D.drawString("2) When the game starts, you can do only these actions:", dimension.width / 12, dimension.height / 8 + 40);
                graphics2D.drawString("OPEN THE CELL (x, y);", dimension.width / 7, dimension.height / 8 + 80);
                graphics2D.drawString("PUT THE FLAG (x, y);", dimension.width / 7, dimension.height / 8 + 120);
                graphics2D.drawString("CLEAR THE CELL FROM THE FLAG (x, y);", dimension.width / 7, dimension.height / 8 + 160);
                graphics2D.drawString("3) You must finish as fast as you can!", dimension.width / 12, dimension.height / 8 + 200);
            }
        };
        panel.setBackground(standardBackgroundColor);
        panel.setLayout(null);
        addMenuButton();

        ImageIcon sakura = new ImageIcon(ImagePathFinder.findPath("SAKURA"));
        JLabel sakuraLabel = new JLabel();
        sakuraLabel.setIcon(sakura);
        sakuraLabel.setBounds(dimension.width / 2 - 100, dimension.height / 7, 920, 752);
        panel.add(sakuraLabel);

        JLabel authorLabel = new JLabel();
        authorLabel.setFont(customFont);
        authorLabel.setText("Created by Kirill Bolotov, 2023.");
        authorLabel.setBounds(dimension.width / 15, dimension.height - dimension.height / 4, 400, 20);
        panel.add(authorLabel);

        frame.add(panel);
    }

    private void showHighScores() {
        clearPanel();
        frame.remove(panel);
        panel = new JPanel();
        Font generalFont = new Font("Menlo", Font.BOLD, 45);
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(generalFont);
        titleLabel.setText("HIGH SCORES:");
        titleLabel.setBounds(dimension.width / 2 - dimension.width / 11, dimension.height / 15, 400, 80);
        panel.add(titleLabel);

        ImageIcon samurai = new ImageIcon(ImagePathFinder.findPath("SAMURAI"));
        JLabel samuraiLabel = new JLabel();
        samuraiLabel.setIcon(samurai);
        samuraiLabel.setBounds(100, 200, 500, 560);
        panel.add(samuraiLabel);

        ImageIcon samuraiInversed = new ImageIcon(ImagePathFinder.findPath("SAMURAI_INVERSED"));
        JLabel samuraiInversedLabel = new JLabel();
        samuraiInversedLabel.setIcon(samuraiInversed);
        samuraiInversedLabel.setBounds(dimension.width - 600, 200, 500, 560);
        panel.add(samuraiInversedLabel);

        int startPositionX = dimension.width / 2 - dimension.width / 13;
        int startPositionY = dimension.height / 5;
        Path path = Paths.get(HighScoresFinder.findPath("PATH"));
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            for (int i = 0; i < UtilConsts.HighScoresConsts.MAX_LENGTH_OF_HIGH_SCORES_LIST; ++i) {
                String currentScoreString = bufferedReader.readLine();
                if (Objects.isNull(currentScoreString)) {
                    break;
                }
                JLabel scoreLabel = new JLabel();
                scoreLabel.setFont(standardFont);
                scoreLabel.setText((i + 1) + ") " + currentScoreString);
                scoreLabel.setBounds(startPositionX, startPositionY + i * 50, 500, 80);
                panel.add(scoreLabel);
            }
        } catch (IOException exception) {
            throw new IOBusinessException(exception);
        }
        panel.setBackground(standardBackgroundColor);
        panel.setLayout(null);
        addMenuButton();
        frame.add(panel);
    }

    private void addScoreToTable() {
        Path path = Paths.get(HighScoresFinder.findPath("PATH"));
        try (FileWriter fileWriter = new FileWriter(path.toFile(), true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            ScoreCalculator.calculateScore(fieldSize, numberOfBombs, Long.parseLong(timerLabel.getText()));
            bufferedWriter.write(username + ", GUI, " + ScoreCalculator.getScore());
            bufferedWriter.newLine();
            bufferedWriter.close();
            HighScoresSorter.sortHighScores();
        } catch (IOException exception) {
            throw new IOBusinessException(exception);
        }
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
            ImageIcon dialogIcon = new ImageIcon(ImagePathFinder.findPath("QUESTION"));
            int answer = JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to return to the menu?",
                    "Confirm your action",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    dialogIcon);
            if (answer == JOptionPane.YES_OPTION) {
                setDefaultPanelWithMenu();
                isGameLaunched = false;
            }
        });
        panel.add(menuButton);
    }

    private Optional<JButton> findSelectedButtonInArray() {
        for (JButton button : cellsList) {
            if (button.isSelected()) {
                return Optional.of(button);
            }
        }
        return Optional.empty();
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

        JLabel intro = new JLabel();
        ImageIcon introIcon = new ImageIcon(ImagePathFinder.findPath("INTRO"));
        intro.setIcon(introIcon);
        intro.setBounds(dimension.width / 2 - dimension.width / 7, 190, 768, 768);
        panel.add(intro);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(standardFont);
        newGameButton.setBounds(dimension.width / 9, 200, 200, 80);

        JButton aboutButton = new JButton("About");
        aboutButton.setFont(standardFont);
        aboutButton.setBounds(dimension.width / 9, 350, 200, 80);

        JButton scoresButton = new JButton("High Scores");
        scoresButton.setFont(standardFont);
        scoresButton.setBounds(dimension.width / 9, 500, 200, 80);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(standardFont);
        exitButton.setBounds(dimension.width / 9, 650, 200, 80);

        newGameButton.addActionListener(event -> {
            prepareGameField();
            isGameLaunched = true;
        });
        aboutButton.addActionListener(event -> showGameRules());
        scoresButton.addActionListener(event -> showHighScores());
        exitButton.addActionListener(event -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));

        UIManager.put("OptionPane.messageFont", new Font("Menlo", Font.PLAIN, 25));
        UIManager.put("OptionPane.buttonFont", new Font("Menlo", Font.PLAIN, 20));

        panel.add(newGameButton);
        panel.add(aboutButton);
        panel.add(scoresButton);
        panel.add(exitButton);
        frame.add(panel);
    }
}
