package main.CapaPresentacio.Vista;

import main.CapaPresentacio.Controllers.CtrlPresentacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;


/**
 * This class implements the view of the game in the GUI
 */
public class vistaGame extends JFrame implements ActionListener {


    //Menu items
    private final JMenuBar menuBarVista = new JMenuBar();
    private final JMenu menuFile = new JMenu("Menu");
    private final JMenuItem menuItemQuit = new JMenuItem("Quit");
    private final JMenu menuOptions = new JMenu("Help");
    private final JMenuItem menuItemAssistance = new JMenuItem("Assistance");

    private final JMenuItem menuItemRules = new JMenuItem("Rules");

    //buttons for guesses which is a  2D array
    private static JButton[][] guessButtons;

    //feedback labels which is a 2D array
    private static JLabel[][] feedbackLabels;

    private final CtrlPresentacion ctrl;

    private MainWindow mainWindow;

    private final GridBagConstraints c = new GridBagConstraints();

    private JPanel guessGridPanel;
    private JPanel feedbackGridPanel;

    private JPanel guessLabelPanel;

    private JTable scoreTable;

    //to check the rows clicked
    private static int numRows = 0;

    //to check the buttons clicked
    private static int numCols = 0;

    //size of the column which is equal to the no. of pegs
    private final int colSize;

    private Color selectedColor;

    //boolean value to check if the button is initialized
    private boolean[][] isGuessMade;

    private int currentRow;

    //the guesses made by the players
    private int[][] guesses;

    //the secretCode for each turn
    private int[] secretCode;

    /**
     * Turn 1 is of Human Player
     */
    private int currentTurn = 1;

    Color defaultButtonColor = Color.GRAY;
    Color defaultFeedbackColor = Color.GRAY;

    //no of rounds played until now
    private int roundsPlayed = 0;

    //game is over when roundsPlayed equal total rounds
    private boolean isGameOver;

    private vistaAssistance assistanceWindow;

    private vistaSecretCode secretCodeWindow;

    private Dimension window;

    private int defaultButtonSize;

    /**
     * Constructor for vistaGame.
     * @param ctrl - The controller object for handling the game logic.
     * @param mainWindow - The MainWindow object for the GUI.
     */
    public vistaGame(CtrlPresentacion ctrl, MainWindow mainWindow){
        super("MASTERMIND");
        this.ctrl = ctrl;
        mainWindow.dispose();
        this.numRows = ctrl.getNumGuesses();
        System.out.println("Num guesses set easy : " + numRows);
        this.numCols = ctrl.getCodeLength();
        this.colSize = ctrl.getNoOfPegs();
        this.mainWindow=mainWindow;
        this.window = mainWindow.getSize();
        this.defaultButtonSize = window.width*78/1000;
        setResizable(false);
        initMenuBarVista();
        initializeComponents();
        if (ctrl.isNewGame()) {
            this.secretCode = ctrl.generateSecretCodePlayer2();
            System.out.println("secret code here in new game is: " + Arrays.toString(secretCode));
        } else {
            this.secretCode = ctrl.getSecretCodePlayer2();
            System.out.println("secret code here in load is: " + Arrays.toString(secretCode));
            recoverStateOfColors();
        }
    }

    /**
     * Initialization of the menu bar for the game.
     */
    private void initMenuBarVista() {
        MenuItemsActionListener menuActionListener = new MenuItemsActionListener(ctrl, this);
        JMenuItem reset = new JMenuItem("New Game");
        reset.addActionListener(menuActionListener);
        reset.setActionCommand("new");

        JMenuItem open = new JMenuItem("Load Game");
        open.addActionListener(e -> {
            vistaLoadGame loadGameWindow = new vistaLoadGame(ctrl, mainWindow);
            vistaGame.this.dispose();
        });
        open.setActionCommand("open");

        JMenuItem save = new JMenuItem("Save Game");
        save.addActionListener(menuActionListener);
        save.setActionCommand("save");

        menuItemQuit.addActionListener(e -> {
            dispose();
            MainWindow mainWindow = new MainWindow(ctrl);
            mainWindow.setVisible(true);
        });

        menuItemAssistance.addActionListener(e -> {
            if (assistanceWindow != null) {
                assistanceWindow.dispose();
            }
            assistanceWindow = new vistaAssistance(ctrl);
            assistanceWindow.setVisible(true);
        });

        menuItemRules.addActionListener(e -> {
            String rules = ctrl.getInfo();
            mainWindow.showRules(rules);
        });

        menuFile.add(reset);
        menuFile.add(open);
        menuFile.add(save);
        menuFile.add(menuItemQuit);
        menuOptions.add(menuItemAssistance);
        menuOptions.add(menuItemRules);
        menuBarVista.add(menuFile);
        menuBarVista.add(menuOptions);
    }

    /**
     * Convert an integer to a corresponding Color.
     * @param guess - The integer representing a guess.
     * @return The corresponding Color for the guess.
     */
    public static Color integerToColor(int guess) {
        Color color;
        switch (guess) {
            case 1:
                color = Color.RED;
                break;
            case 2:
                color = Color.ORANGE;
                break;
            case 3:
                color = Color.YELLOW;
                break;
            case 4:
                color = Color.green;
                break;
            case 5:
                color = Color.blue;
                break;
            case 6:
                color = Color.cyan;
                break;
            case 7:
                color = Color.pink;
                break;
            case 8:
                color = Color.magenta;
                break;
            case 9:
                color = Color.black;
                break;
            case 10:
                color = Color.gray;
                break;
            case 11:
                color = Color.lightGray;
                break;
            case 12:
                color = Color.darkGray;
                break;
            default:
                color = null;
                break;
        }
        return color;
    }




    /**
     * Convert a Color to a corresponding color name in string.
     * @param color - The Color to be converted.
     * @return The corresponding color name in string.
     */

    private String colorToName(Color color){
        if (color.equals(Color.red)) return "Red";
        else if (color.equals(Color.orange)) return "Orange";
        else if (color.equals(Color.yellow)) return "Yellow";
        else if (color.equals(Color.green)) return "Green";
        else if (color.equals(Color.blue)) return "Blue";
        else if (color.equals(Color.cyan)) return "Cyan";
        else if (color.equals(Color.pink)) return "Pink";
        else if (color.equals(Color.magenta)) return "Magenta";
        else if (color.equals(Color.black)) return "Black";
        else if (color.equals(Color.gray)) return "Gray";
        else if (color.equals(Color.lightGray)) return "LightGray";
        else if (color.equals(Color.darkGray)) return "DarkGray";
        else return "Unknown Color";
    }

    /**
     * Initialization of the reset button.
     */
    private void initializeResetButton() {
        JButton resetButton = new JButton("RESTART");
        resetButton.setFont(new Font("Century Schoolbook", Font.BOLD, 20));
        resetButton.setForeground(Color.LIGHT_GRAY);
        resetButton.setBackground(Color.BLUE);
        System.out.println(ctrl.getNoOfPegs());
        resetButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to restart the game?",
                    "Confirmation",JOptionPane.YES_NO_OPTION);
            if(response == JOptionPane.YES_OPTION){
                restartGame();
                resetScores();
            }
        });


        JLabel guessLabel = new JLabel("Make a guess!");
        guessLabel.setFont(new Font("Century Schoolbook", Font.BOLD, 45));
        guessLabel.setBorder(new EmptyBorder(window.height*13/100, window.width*37/100, 0, 0));

        JPanel buttonWrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonWrapperPanel.add(resetButton);
        buttonWrapperPanel.add(guessLabel);
        buttonWrapperPanel.setBorder(new EmptyBorder(window.height*-3/100,window.width*3/100,0,0));

        JPanel northPanel = (JPanel) getContentPane().getComponent(0);
        northPanel.add(buttonWrapperPanel);
    }

    /**
     * Reset the scores for the players.
     */
    private void resetScores(){
        ctrl.setScorePlayer1(0);
        ctrl.setScorePlayer2(0);
        scoreTable.setValueAt(ctrl.getScorePlayer1(),0,1);
        scoreTable.setValueAt(ctrl.getScorePlayer2(),1,1);
    }

    /**
     * Initialization of the score labels.
     */
    private void initializeScore() {
        JPanel scorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        System.out.println("The score of the player is " + ctrl.getScorePlayer1());
        System.out.println("The score of the AI is " + ctrl.getScorePlayer2());
        String[] columnNames = {"Player", "Score"};
        String[][] data = {
                {ctrl.getPlayer1Name(), Integer.toString(ctrl.getScorePlayer1())},
                {ctrl.getPlayer2Name(), Integer.toString(ctrl.getScorePlayer2())}
        };
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        scoreTable = new JTable(tableModel);
        scoreTable.setShowGrid(true); // Show grid lines

        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setPreferredSize(new Dimension(window.width*30/100, window.height*30/100));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        scorePanel.add(scrollPane, c);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(scorePanel, BorderLayout.EAST);
        this.add(wrapperPanel, BorderLayout.NORTH);
    }


    /**
     * Initialization of the color buttons
     */
    private void initializeColorButtons(){
        // Assuming maximum 12 colors available for 12 pegs
        Color[] allColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.PINK, Color.MAGENTA, Color.BLACK, Color.GRAY, Color.LIGHT_GRAY, Color.DARK_GRAY};
        Color[] colors = Arrays.copyOfRange(allColors, 0, colSize);
        JButton[] colorButtons = new JButton[colors.length];
        JPanel colorButtonPanel = new JPanel();
        colorButtonPanel.setLayout(new FlowLayout());

        for (int i = 0; i < colors.length; i++) {
            colorButtons[i] = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    g.setColor(getBackground());
                    g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
                    super.paintComponent(g);
                }
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(50, 50);
                }
                @Override
                protected void paintBorder(Graphics g) {
                    g.setColor(getForeground());
                    g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
                }
            };
            colorButtons[i].setOpaque(false);
            colorButtons[i].setContentAreaFilled(false);
            colorButtons[i].setBackground(colors[i]);
            colorButtons[i].setBorderPainted(false);

            int auxI = i;
            colorButtons[i].addActionListener(e -> {

                System.out.println(Arrays.toString(secretCode));
                selectedColor = colors[auxI];
            });
            colorButtonPanel.add(colorButtons[i]);
        }
        //to move the colorButtonPanel down on the y-axis
        JPanel colorButtonWrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints ColorButton = new GridBagConstraints();
        ColorButton.gridwidth = GridBagConstraints.REMAINDER;
        ColorButton.weighty = 1.0;
        colorButtonWrapperPanel.add(colorButtonPanel, ColorButton);

        this.add(colorButtonWrapperPanel, BorderLayout.SOUTH);
    }

    /**
     * Initialization of guess buttons
     */
    private void initializeGuessButtons(){
        guessButtons = new JButton[numRows][numCols];
        isGuessMade = new boolean[numRows][numCols];
        guesses = new int[numRows][numCols];

        guessGridPanel = new JPanel(new GridBagLayout());

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 10, 10);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                c.gridx = j;
                c.gridy = i;
                guessButtons[i][j] = new JButton();
                guessButtons[i][j].setOpaque(true);
                guessButtons[i][j].setBorderPainted(true);
                guessButtons[i][j].setBackground(defaultButtonColor);
                guessButtons[i][j].setPreferredSize(new Dimension(defaultButtonSize, defaultButtonSize));
                int auxI = i;
                int auxJ = j;
                guessButtons[i][j].addActionListener(e -> {
                    if (auxI != currentRow) {
                        return;
                    }
                    if (selectedColor != null) {
                        guessButtons[auxI][auxJ].setBackground(selectedColor);
                        guesses[auxI][auxJ] = ctrl.colorToInteger(selectedColor);
                        isGuessMade[auxI][auxJ] = true;
                        currentRow = auxI;
                        ctrl.setSecretCodes(secretCode,secretCode);
                        checkCurrentGuess();
                    }
                });
                guessGridPanel.add(guessButtons[i][j], c);
            }
        }
    }

    /**
     * Initialization of feedback labels
     */
    private void initializeFeedbackButtons(){
        feedbackLabels = new JLabel[numRows][numCols];
        feedbackGridPanel = new JPanel(new GridBagLayout());
        feedbackGridPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        GridBagConstraints Feedback = new GridBagConstraints();
        Feedback.fill = GridBagConstraints.BOTH;
        Feedback.insets = new Insets(10, 10, 10, 10);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Feedback.gridx = j;
                Feedback.gridy = i;
                feedbackLabels[i][j] = new JLabel();
                feedbackLabels[i][j].setOpaque(true);
                feedbackLabels[i][j].setBackground(defaultFeedbackColor);
                feedbackLabels[i][j].setPreferredSize(new Dimension(defaultButtonSize, defaultButtonSize));
                feedbackLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                feedbackGridPanel.add(feedbackLabels[i][j], Feedback);
            }
        }
    }

    /**
     * Initialization of Guess labels
     */
    private void initializeGuessLabels(){
        guessLabelPanel = new JPanel(new GridBagLayout());
        for (int i = 0; i < numRows; i++) {
            JLabel guessLabel = new JLabel("Guess " + (i + 1) + ": ");
            guessLabel.setPreferredSize(new Dimension(100, 70));
            GridBagConstraints label = new GridBagConstraints();
            label.gridx = 0;
            label.gridy = i;
            guessLabelPanel.add(guessLabel, label);
        }
    }

    /**
     * Initialization of all the components of the game
     * Calls other initialization methods
     */
    private void initializeComponents() {
        this.setJMenuBar(menuBarVista);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 1300);
        this.setLocationRelativeTo(null);

        initializeScore();
        initializeGuessButtons();
        initializeFeedbackButtons();
        initializeGuessLabels();
        initializeResetButton();

        JPanel gridsPanel = new JPanel();
        gridsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.insets = new Insets(0, 50, 0, 0); // padding left

        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gridsPanel.add(guessLabelPanel, gbcPanel);

        gbcPanel.gridx = 1;
        gridsPanel.add(guessGridPanel, gbcPanel);

        gbcPanel.gridx = 2;
        gridsPanel.add(feedbackGridPanel, gbcPanel);

        // Add the container panel to the frame
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(Box.createHorizontalStrut(75), BorderLayout.WEST);
        containerPanel.add(gridsPanel, BorderLayout.CENTER);
        this.add(containerPanel, BorderLayout.CENTER);
        initializeColorButtons();
    }

    /**
     * To check the current Guess made by the human player
     */
    private void checkCurrentGuess(){
        if(roundsPlayed == 0){
            ctrl.setRoundsPlayed(roundsPlayed);
        }
        if (isGameOver) {
            return;
        }
        for (int j = 0; j < numCols; j++) {
            if (!isGuessMade[currentRow][j]) {
                return;
            }
        }
        ctrl.setScorePlayer2(ctrl.getScorePlayer2()+1);                         //Increments the score of AI by 1 for each guess made by the player
        scoreTable.setValueAt(ctrl.getScorePlayer2(),1,1);
        int[] guess = Arrays.copyOf(guesses[currentRow], numCols);
        System.out.println("Guess: " + Arrays.toString(guess));
        System.out.println("Secret Code: " + Arrays.toString(secretCode));
        int[] feedback = ctrl.getFeedback(guess, currentTurn == 1);
        // update feedback grid
        int blackPegs = feedback[0]; // correct colors in the correct position
        int whitePegs = feedback[1]; // correct colors in the wrong position

        int totalBlackPegsPlaced = 0;
        for (int i = 0; i < blackPegs; i++) {
            feedbackLabels[currentRow][i].setBackground(Color.BLACK);
            totalBlackPegsPlaced++;
        }

        for (int i = totalBlackPegsPlaced ; i < totalBlackPegsPlaced +  whitePegs; i++) {
            feedbackLabels[currentRow][i].setBackground(Color.WHITE);
        }

        for(int j = 0; j < numCols; j++){
            if(guesses[currentRow][j] != secretCode[j]){
                break;
            }
        }

        if(blackPegs == numCols){
            JOptionPane.showMessageDialog(this, "You have guessed the code correctly.", "Hurray!", JOptionPane.INFORMATION_MESSAGE);
            if(currentTurn == 1){
                ctrl.setRoundsPlayed(++roundsPlayed);
                if (roundsPlayed > ctrl.getTotalRounds()) {
                    declareWinner();
                    return;
                }
                currentTurn = 2; // Switch turn to player2
                currentRow=0;
                enterSecretCodeForAI();
                playAITurn(); // Start AI turn
            } else {
                currentTurn = 1; // Switch turn back to player1
            }
        }


        else {
            //incorrect, continue to next row
            currentRow++;
            if(currentRow >= numRows){
                ctrl.setRoundsPlayed(++roundsPlayed);
                //If all guesses have been used, player has lost
                JOptionPane.showMessageDialog(this, "You have reached the max attempts. Better luck next time.", "MAX ATTEMPTS", JOptionPane.INFORMATION_MESSAGE);
                currentTurn = 2; // Switch turn to player2
                enterSecretCodeForAI();
                playAITurn(); // Start AI turn
            }
        }

    }

    /**
     * JOptionPane for the input dialog to set the secretCode for AI to guess
     */
    private void enterSecretCodeForAI() {
        secretCodeWindow = new vistaSecretCode(ctrl);
        secretCodeWindow.setVisible(true); //Won't go to the next line of code until the secret code has been submitted
        secretCode = ctrl.getSecretCodePlayer2();
    }


    /**
     * Guesses made by the AI player
     */
    private void playAITurn(){
        System.out.println("Secret Code: " + Arrays.toString(secretCode));
        if(secretCode == guesses[currentRow]){return;}
        // Reset game board after the human player turn
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                guessButtons[i][j].setBackground(defaultButtonColor);
                isGuessMade[i][j] = false;
                feedbackLabels[i][j].setBackground(defaultButtonColor);
                guessButtons[i][j].setEnabled(false);
                guesses[i][j] = 0;
            }
        }

        currentRow = 0;
        List<Integer> secretCodeList = new ArrayList<>();
        for (int i : secretCode) {
            secretCodeList.add(i);
        }
        List<List<Integer>> AIguesses;
        try {
            System.out.println("Secret Code List: " + secretCodeList);
            AIguesses = ctrl.solveUsingAlgorithm(secretCodeList);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error while generating AI guess: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < AIguesses.size(); i++) {
            currentRow=i;

            for (int j = 0; j < AIguesses.get(i).size(); j++) {
                guesses[i][j] = AIguesses.get(i).get(j);
                guessButtons[i][j].setBackground(integerToColor(guesses[i][j]));
            }

            ctrl.setScorePlayer1(ctrl.getScorePlayer1()+1);                     //Increments the score of the player by 1 for each guess made by the AI
            scoreTable.setValueAt(ctrl.getScorePlayer1(),0,1);
            int[] feedback = ctrl.getFeedback(guesses[i], false); // Changed currentRow to i
            int blackPegs = feedback[0];
            int whitePegs = feedback[1];

            for (int k = 0; k < blackPegs; k++) {
                feedbackLabels[i][k].setBackground(Color.BLACK);
            }

            for (int k = blackPegs; k < blackPegs + whitePegs; k++) {
                feedbackLabels[i][k].setBackground(Color.WHITE);
            }

            if(blackPegs == numCols){
                isGameOver = true;
                String secretCodeColors = Arrays.stream(secretCode)
                        .mapToObj(vistaGame::integerToColor)
                        .map(this::colorToName)
                        .collect(Collectors.joining(", "));

                String message = String.format(
                        "AI Player has guessed the secret code (%s) correctly in %d steps.",
                        secretCodeColors,
                        currentRow + 1
                );
                JOptionPane.showMessageDialog(this, message, "Hurray!", JOptionPane.INFORMATION_MESSAGE);
                currentTurn = 1; // Switch turn back to player1
                currentRow = 0;
                resetGame();
            }

            else {
                // If the AI's guess is incorrect, continue to next row
                if (i + 1 >= numRows) {
                    isGameOver = true;
                    JOptionPane.showMessageDialog(this, "AI has reached the MAX ATTEMPTS", "MAX ATTEMPTS", JOptionPane.INFORMATION_MESSAGE);
                    currentTurn = 1; // Switch turn back to player1
                    currentRow = 0;
                    resetGame();
                }
            }
        }

        if(roundsPlayed >= ctrl.getTotalRounds()){
            declareWinner();
            showPlayAgainDialog();
            return;
        }

        if (isGameOver) {
            currentTurn=1;
            resetGame();
        }
    }

    /**
     * If the game is over, compares the scores and declares the winner
     */
    private void declareWinner() {
        if (ctrl.getScorePlayer1() > ctrl.getScorePlayer2()) {
            JOptionPane.showMessageDialog(this, "Game Over. "+ ctrl.getPlayer1Name() +" wins with "+ctrl.getScorePlayer1()+" points. Thanks for playing!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Game Over. "+ ctrl.getPlayer2Name() +" wins with "+ctrl.getScorePlayer2()+" points. Thanks for playing!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        ctrl.updateRanking(ctrl.getPlayer1Name(),ctrl.getScorePlayer1());
        ctrl.saveRecords();
        resetScores();
    }

    /**
     * Resets the game completely when a RESTART button is pressed
     */
    private void resetGame() {
        isGameOver = false;
        secretCode = ctrl.generateSecretCodePlayer2();
        System.out.println("New secret code for next game is: " + Arrays.toString(secretCode));
        // Reset game board

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                guessButtons[i][j].setBackground(defaultButtonColor);
                isGuessMade[i][j] = false;
                feedbackLabels[i][j].setBackground(defaultButtonColor);
                guesses[i][j] = 0;
            }
        }

        // Enable all buttons for player's turn
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                guessButtons[i][j].setEnabled(true);
            }
        }

        currentRow = 0;
        currentTurn = 1;
    }

    private void restartGame() {
        isGameOver = false;
        secretCode = ctrl.generateSecretCodePlayer2();
        System.out.println("New secret code for next game is: " + Arrays.toString(secretCode));
        // Reset game board

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                guessButtons[i][j].setBackground(defaultButtonColor);
                isGuessMade[i][j] = false;
                feedbackLabels[i][j].setBackground(defaultButtonColor);
                guesses[i][j] = 0;
            }
        }

        // Enable all buttons for player's turn
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                guessButtons[i][j].setEnabled(true);
            }
        }

        currentRow = 0;
        currentTurn = 1;
        roundsPlayed = 0;
    }

    /**
     * Asks for continuity or not when the game is over
     */
    private void showPlayAgainDialog() {
        int dialog1 = JOptionPane.showConfirmDialog(this, "Would you like to play another game?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (dialog1 == JOptionPane.YES_OPTION){
            int dialog2 = JOptionPane.showConfirmDialog(this,"Would you like to play with same settings?","Confirm",JOptionPane.YES_NO_OPTION);
            if(dialog2==JOptionPane.YES_OPTION){
                restartGame();
            }
            else{
                this.dispose();
                mainWindow.initSettingsTable();
                vistaNewGame vg = new vistaNewGame(ctrl,mainWindow);
                vg.setVisible(true);
            }
        }
        else {
            this.dispose();
            mainWindow.initSettingsTable();
            mainWindow.setVisible(true);
        }
    }

    /**
     * Getters and setters to save colors of buttons to load later while loading
     */

    public void recoverStateOfColors() {
        List<int[]> guessesPlayer1 = ctrl.getGuessesPlayer1();
        List<int[]> feedbackPlayer1 = ctrl.getFeedbackPlayer1();

        //guesses
        int[] lastFeedback = feedbackPlayer1.get(feedbackPlayer1.size() - 1);
        if(!Arrays.equals(lastFeedback, new int[]{4,0})){
            for(int i=0; i<guessesPlayer1.size(); i++){
                int[] guessRow = guessesPlayer1.get(i);
                for(int j=0; j<guessRow.length; j++){
                    guessButtons[i][j].setBackground(integerToColor(guessRow[j]));
                    guesses[i][j]=guessRow[j];
                }
            }

            //feedback
            for(int i=0; i<feedbackPlayer1.size(); i++){
                int[] feedbackRow = feedbackPlayer1.get(i);
                int blackPegs = feedbackRow[0];
                int whitePegs=feedbackRow[1];
                for(int j=0; j<blackPegs; ++j){
                    feedbackLabels[i][j].setBackground(Color.BLACK);
                }

                for(int j=0; j<whitePegs; j++){
                    feedbackLabels[i][j].setBackground(Color.WHITE);
                }
            }

            // set currentRow and enable the guess buttons for the next guess
            currentRow = guessesPlayer1.size();
            for (int j = 0; j < numCols; j++) {
                guessButtons[currentRow][j].setEnabled(true);
            }
        }
    }

    public void newGame(){
        vistaNewGame vn = new vistaNewGame(ctrl,this.mainWindow);
        mainWindow.setVisible(true);
        vn.setVisible(true);
        this.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}