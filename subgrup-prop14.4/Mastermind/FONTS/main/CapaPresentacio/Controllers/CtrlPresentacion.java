package main.CapaPresentacio.Controllers;

import main.CapaDomini.Controllers.CtrlDomini;
import main.CapaDomini.Models.GameSave;
import main.CapaPresentacio.Vista.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CtrlPresentacion {

    private CtrlDomini cd;

    private MainWindow mainWindow;

    private boolean isNewGame;


    /**
     * Constructor for the CtrlPresentacion class. Initializes the controller for the game.
     */
    public CtrlPresentacion(){
        this.cd= new CtrlDomini();
        mainWindow = new MainWindow(this);
    }

    /**
     * Sets Player 1's name.
     * @param humanPlayerName - Name of Player 1.
     */
    public void setPlayer1Name(String humanPlayerName) {
        cd.createHumanPlayer(humanPlayerName);
    }

    /**
     * Selects the algorithm for the game.
     * @param command - Algorithm to be used.
     */
    public void selectAlgorithm(String command) {
        cd.selectAlgorithm(command);
    }

    /**
     * Selects the difficulty level for the game.
     * @param command - Difficulty level to be set.
     */
    public void selectDifficultyLevel(String command) {
        cd.selectDifficultyLevel(command);
    }

    /**
     * Handles error during the game.
     * @param s - Error message to be displayed.
     */
    public void errorManagement(String s) {
        JOptionPane.showMessageDialog(new JFrame("error"),
                s, "error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Sets the length of the code for the game.
     * @param parseInt - Length of the code.
     */
    public void setCodeLength(int parseInt) {
        cd.setCodeLength(parseInt);
    }

    /**
     * Sets the number of pegs for the game.
     * @param parseInt - Number of pegs.
     */

    public void setNoOfPegs(int parseInt) {
        cd.setNumberOfPegs(parseInt);
    }

    /**
     * Gets Player 1's name.
     * @return Player 1's name.
     */
    public String getPlayer1Name() {
        return  cd.getPlayer1name();
    }

    /**
     * Gets the current algorithm used in the game.
     * @return Current algorithm.
     */
    public String getAlgorithm() {
        return cd.getCurrentAlgorithm();
    }

    /**
     * Gets the current difficulty level of the game.
     * @return Current difficulty level.
     */
    public String getDifficulty() {
        return cd.getDifficultyLevel1();
    }

    /**
     * Gets the length of the code in the game.
     * @return Length of the code.
     */
    public int getCodeLength() {
        return Integer.parseInt(cd.getCodeLength());
    }

    /**
     * Gets the number of pegs in the game.
     * @return Number of pegs.
     */
    public int getNoOfPegs() {
        return Integer.parseInt(cd.getNumberOfPegs());
    }

    /**
     * Checks if the game settings are valid.
     * @return True if the settings are valid, false otherwise.
     */
    public boolean isSettingsValid(){
        return cd.playerNotNull();
    }

    /**
     * Gets the number of guesses made in the game.
     * @return Number of guesses.
     */
    public int getNumGuesses(){
        return Integer.parseInt(cd.getNumGuesses());
    }


    /**
     * Gets Player 2's name.
     * @return Player 2's name.
     */
    public String getPlayer2Name() {
        return cd.getPlayer2name();
    }

    /**
     * Sets Player 2's name.
     * @param selectedAlgorithm - Name of Player 2.
     */
    public void setPlayer2Name(String selectedAlgorithm) {
        cd.createAIPlayer(selectedAlgorithm);
    }

    /**
     * Gets Player 1's score.
     * @return Player 1's score.
     */
    public int getScorePlayer1() {
        return Integer.parseInt(cd.getScorePlayer1());
    }

    /**
     * Gets Player 2's score.
     * @return Player 2's score.
     */
    public int getScorePlayer2() {
        return Integer.parseInt(cd.getScorePlayer2());
    }

    public void setScorePlayer1(int score){
        cd.setScorePlayer1(score);
    }

    public void setScorePlayer2(int score){
        cd.setScorePlayer2(score);
    }

    /**
     * Gets current resolution.
     * @return Dimension instance with the current screen resolution.
     */
    public Dimension getCurrentResolution() {
        Toolkit toolkit =  Toolkit.getDefaultToolkit();
        Dimension dim = toolkit.getScreenSize();
        return dim;
    }

    /**
     * Generates a secret code for Player 2.
     * @return Generated secret code.
     */
    public int[] generateSecretCodePlayer2(){
        String s = cd.generateSecretCodePlayer2();
        return changeStringToArray(s);
    }

    /**
     * Updates global Ranking if the its possible to.
     * @param player1Name - Name of Player 1.
     * @param player1Score - Updated score of Player 1.
     */
    public void updateRanking(String player1Name, int player1Score) {
        cd.updateRanking(player1Name,player1Score);
    }

    /**
     * Provides feedback for the provided guess.
     * @param guess - Guess for which feedback is required.
     * @param b - true if player 1 else false
     * @return Feedback for the guess.
     */
    public int[] getFeedback(int[] guess, boolean b) {
        String s = cd.getFeedback(guess,b);
        return changeStringToArray(s);
    }

    /**
     * Sets the secret codes for the game.
     * @param secretCode - Secret code for Player 1.
     * @param secretCode1 - Secret code for Player 2.
     */
    public void setSecretCodes(int[] secretCode, int[] secretCode1) {
        cd.setSecretCodes(secretCode,secretCode1);
    }

    /**
     * Solves the game using the specified algorithm.
     * @param secretCodeList - The secret code to solve.
     * @return The solved secret code list.
     * @throws Exception If there was a problem-solving the code.
     */
    public List<List<Integer>> solveUsingAlgorithm(List<Integer> secretCodeList) throws Exception {
        return cd.solveUsingAlgorithm(secretCodeList);
    }

    /**
     * Sets the total number of rounds for the game.
     * @param totalRounds - Total number of rounds.
     */
    public void setTotalRounds(int totalRounds) {
        cd.setTotalRounds(totalRounds);
    }

    /**
     * Gets the total number of rounds set for the game.
     * @return Total number of rounds.
     */
    public int getTotalRounds(){
        return Integer.parseInt(cd.getTotalRounds());
    }

    /**
     * Saves the current state of the game.
     * @param path - The path where to save the game state.
     * @throws IOException If there was a problem saving the game state.
     * @throws Exception If there was a problem saving the game state.
     */
    public void save(String path) throws IOException, Exception{
        cd.save(path);
    }

    /**
     * Loads a saved game state.
     * @param path - The path from where to load the game state.
     * @throws Exception If there was a problem loading the game state.
     */
    public void load(String path) throws Exception {
        cd.load(path);
    }

    /**
     * Gets the current round of the game.
     * @return Current round.
     */
    public int getRoundsPlayed() {
        return Integer.parseInt(cd.getCurrentRound());
    }

    /**
     * Sets the current round of the game.
     * @param currentRound - The current round.
     */
    public void setRoundsPlayed(int currentRound){
        cd.setCurrentRound(currentRound);
    }

    /**
     * Checks if it's a new game.
     * @return True if it's a new game, false otherwise.
     */
    public boolean isNewGame(){
        return this.isNewGame;
    }

    /**
     * Marks the game as a loaded game.
     */
    public void isLoadedGame(){
        this.isNewGame=false;
    }

    /**
     * Marks the game as a new game.
     */
    public void startNewGame(){
        this.isNewGame=true;
    }

    /**
     * Gets the secret code of Player 2.
     * @return The secret code of Player 2.
     */
    public int[] getSecretCodePlayer2() {
        String s = cd.getSecretCodePlayer2();
        return changeStringToArray(s);
    }


    /**
     * Gets the top records of the game.
     * @return The top records.
     */
    public List<String> getTopRecords(){
        return cd.getTopRecords();
    }

    /**
     * Saves the records to a file.
     */
    public void saveRecords(){
        cd.saveRecordsToFile();
    }

    /**
     * Gets the next correct color from the secret code as a clue , always starting from the second color.
     * @param forPlayer1 - If true, the peg is for Player 1, otherwise for Player 2.
     * @return The correct peg and its position.
     */
    public String getNextCorrectPegAndPosition(boolean forPlayer1){
        return cd.getNextCorrectPegAndPosition(forPlayer1);
    }

    /**
     * Gets information about the game.
     * @return The game information.
     */
    public String getInfo(){
        return cd.getInfo();
    }

    public List<int[]> getGuessesPlayer1(){
        String guesses = cd.getGuessesPlayer1();
        return changeStringToListOfArrays(guesses);
    }

    public List<int[]> getFeedbackPlayer1(){
        String feedbacks = cd.getFeedbackPlayer1();
        return changeStringToListOfArrays(feedbacks);
    }

    public int[] changeStringToArray(String s){
        return Arrays.stream(s.substring(1, s.length() - 1).split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public List<int[]> changeStringToListOfArrays(String s){
        return Arrays.stream(s.split(";"))
                .map(this::changeStringToArray)
                .collect(Collectors.toList());
    }


    /**
     * Convert a Color to a corresponding integer.
     * @param color - The Color to be converted.
     * @return The corresponding integer for the color.
     */

    public static int colorToInteger(Color color) {
        if (color.equals(Color.RED)) {
            return 1;
        } else if (color.equals(Color.ORANGE)) {
            return 2;
        } else if (color.equals(Color.YELLOW)) {
            return 3;
        } else if (color.equals(Color.GREEN)) {
            return 4;
        } else if (color.equals(Color.BLUE)) {
            return 5;
        } else if (color.equals(Color.cyan)) {
            return 6;
        } else if (color.equals(Color.pink)) {
            return 7;
        } else if (color.equals(Color.magenta)) {
            return 8;
        } else if (color.equals(Color.black)) {
            return 9;
        } else if (color.equals(Color.gray)) {
            return 10;
        } else if (color.equals(Color.lightGray)) {
            return 11;
        } else if (color.equals(Color.darkGray)) {
            return 12;
        } else {
            return 0;
        }
    }



}
