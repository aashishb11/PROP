package main.CapaDomini.Controllers;

import main.CapaDomini.Models.*;
import main.CapaDades.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class responsible for managing the game.
 * It interacts with persistence layer and presentation layer.
 */
public class CtrlDomini {

    //instance of this class
    private static CtrlDomini instance;

    //For loading and saving of game instance and records
    private final CtrlPersistencia cp;

    //Mastermind game board
    private MastermindBoard board;

    //game with code length and number of pegs
    private int codeLength;
    private int numberOfPegs;

    //players in the game : player1 & player2
    private Player player1;
    private Player player2; //AI

    //instance of the maquina interface
    private Maquina AIPlayer;

    //difficulty level of the game
    private DifficultyLevel difficultyLevel;

    //assistance for providing hints during the game
    private final Assistance assistance;

    //ranking system for the game, also useful to pull records
    private Ranking ranking;

    //Algorithm used by the AI for the game
    private String currentAlgorithm;

    //Maximum attempts allowed in the game
    private int maxSteps;

    //Total rounds in a game
    private int totalRounds;

    //current round in the game
    private int currentRound;

    private int scorePlayer1 = 0;

    private int scorePlayer2 = 0;

    /**
     * Constructor for the CtrlDomini class
     * Initializes the game with a certain codeLength and numberOfPegs
     * Loads ranking from a persistent file (Records)
     * Initializes players with the specified game configurations
     */
    public CtrlDomini()
    {
        cp = new CtrlPersistencia();
        codeLength = 4;
        numberOfPegs = 6;
        board = new MastermindBoard(codeLength,numberOfPegs);
        assistance=new Assistance(board);
        difficultyLevel=new DifficultyLevel("Easy",10,false);
        maxSteps = difficultyLevel.getNumGuesses();
        player1=new Human(codeLength,numberOfPegs,"default");
        player2=new FiveGuess(codeLength,numberOfPegs,maxSteps);
        ranking=cp.loadRecordsFromFile();
        currentRound = 0;
    }

    /**
     * Get the name of player1.
     * @return a string representing the name of player1.
     */
    public String getPlayer1name()
    {
        return player1.getName();
    }
    /**
     * Get the name of player2.
     * @return a string representing the name of player2.
     */
    public String getPlayer2name()
    {
        return player2.getName();
    }

    /**
     * Returns the codeLength used in the game.
     * @return The codeLength used in the game.
     */
    public String getCodeLength() {
        return Integer.toString(codeLength);
    }

    /**
     * Returns the number of pegs used in the game.
     * @return The number of pegs used in the game.
     */

    public String getNumberOfPegs() {
        return Integer.toString(numberOfPegs);
    }

    /**
     * Returns player 1's secret code.
     * @return Player 1's secret code.
     */
    public int[] getSecretCodePlayer1() {
        System.out.println("Getting player1's secret code: " + Arrays.toString(board.getSecretCodePlayer1()));
        return board.getSecretCodePlayer1();
    }

    /**
     * Returns player 2's secret code.
     * @return Player 2's secret code.
     */
    public String getSecretCodePlayer2() {
       int[] secretCode = board.getSecretCodePlayer1();
        return Arrays.toString(secretCode);
    }

    /**
     * Returns the guesses made by player1
     * @return Player 1's guesses
     */
    //if problem occurs it will be here
    public String getGuessesPlayer1() {
        List<int[]> guesses = board.getGuessesPlayer1();
        return guesses.stream().map(Arrays::toString).collect(Collectors.joining(";"));
    }

    /**
     * Returns the guesses made by player2
     * @return Player 2's guesses
     */
    public List<int[]> getGuessesPlayer2() {
        System.out.println("Getting player2's guesses: " + board.getGuessesPlayer2());
        return board.getGuessesPlayer2();
    }

    /**
     * Returns the feedback obtained by player1
     * @return Player 1's obtained feedback
     */
    public String getFeedbackPlayer1() {
        System.out.println("Getting player1's feedback: " + board.getFeedbackPlayer1());
        List<int[]> feedBack = board.getFeedbackPlayer1();
        return feedBack.stream()
                .map(Arrays::toString)
                .collect(Collectors.joining(";"));
    }

    /**
     * Returns the feedback obtained by player1
     * @return Player 1's obtained feedback
     */
    public List<int[]> getFeedbackPlayer2() {
        System.out.println("Getting player2's feedback: " + board.getFeedbackPlayer2());
        return board.getFeedbackPlayer2();
    }

    /**
     * Get the name of the current difficulty level.
     * @return the name of the current difficulty level.
     */
    public String getDifficultyLevel1(){
        System.out.println("Getting difficulty level: " + this.difficultyLevel.getName());
        return this.difficultyLevel.getName();
    }

    /**
     * Get the number of guesses made.
     * @return the number of guesses made.
     */
    public String getNumGuesses(){
        System.out.println("Getting number of guesses: " + difficultyLevel.getNumGuesses());
        return Integer.toString(difficultyLevel.getNumGuesses());
    }



    /**
     * To get the current algorithm being used
     * @return the current algorithm being used by the AI.
     */
    public String getCurrentAlgorithm() {
        return currentAlgorithm;
    }

    /** Feedbacks based on the guesses made by the player
     *
     * @param guessPlayer1 The guess made by player1
     * @param b boolean to represent if the feedback is for player1 or player2
     * @return An array of integers representing the feedback
     */
    public String getFeedback(int[] guessPlayer1, boolean b) {
        int[] feedBack = board.getFeedback(guessPlayer1,b);
        return Arrays.toString(feedBack);
    }

    /**
     * Provides feedback about the number of pegs placed in the right position
     * in the last guess made by the player.
     *
     * @param forPlayer1 Boolean to specify if feedback is for player 1.
     * @return String feedback about the number of correctly positioned pegs.
     */
    public String getRightPegsInRightPosition(boolean forPlayer1) {
        return assistance.getRightPegsInRightPosition(forPlayer1);
    }

    /**
     * Provides feedback about the number of correct pegs placed in the wrong position
     * in the last guess made by the player.
     *
     * @param forPlayer1 Boolean to specify if feedback is for player 1.
     * @return String feedback about the number of correct but wrongly positioned pegs.
     */
    public String getRightPegsInWrongPosition(boolean forPlayer1) {
        return assistance.getRightPegsInWrongPosition(forPlayer1);
    }

    /**
     * Gives a hint to the player by revealing the position of a peg in the secret code.
     *
     * @param forPlayer1 Boolean to specify if the hint is for player 1.
     * @return String hint about a peg and its position in the secret code.
     */
    public String getNextCorrectPegAndPosition(boolean forPlayer1) {
        String aux = assistance.getNextCorrectPegAndPosition(forPlayer1);
        assistance.nextPeg();
        return aux;
    }

    /**
     * Returns the current score of player1
     * @return The score of player1
     */
    public String getScorePlayer1(){
        return Integer.toString(this.scorePlayer1);
    }

    /**
     * Returns the current score of player2
     * @return The score of player2
     */
    public String getScorePlayer2(){
        return Integer.toString(this.scorePlayer2);
    }

    /**
     * Top 10 scores for the records functionality
     * @return A list of strings where each string represents a player's name and their score.
     */
   public List<String> getTopRecords(){
       List<Ranking.ScoreEntry> records = ranking.getTopScores(10);
       List<String> recordStrings = new ArrayList<>();

       for (Ranking.ScoreEntry record : records) {
           String recordString = record.getPlayerName() + ", " + record.getScore();
           recordStrings.add(recordString);
       }

       return recordStrings;
   }


    /**
     * Gets the total number of rounds set by the player in the current round
     * @return the total number of rounds
     */

    public String getTotalRounds() {
        return Integer.toString(this.totalRounds);
    }

    /**
     * @return the current round number in the game
     */

    public String getCurrentRound(){
        return Integer.toString(this.currentRound);
    }

    /**
     * Set the name for player1.
     * @param name the new name for player1.
     */
    public void setPlayer1Name(String name)
    {
        player1.setName(name);
    }

    /**
     * Set the name for player2.
     * @param name the new name for player2.
     */
    public void setPlayer2Name(String name)
    {
        player2.setName(name);
    }

    /**
     * Sets the code length for the game and initializes a new board.
     * @param codeLength The length of the secretCode.
     */
    public void setCodeLength(int codeLength){
        this.codeLength=codeLength;
        this.board = new MastermindBoard(this.codeLength, this.numberOfPegs);
    }

    /**
     * Sets the number of pegs for the game and initializes a new board.
     * @param numberOfPegs The number of different colors available.
     */
    public void setNumberOfPegs(int numberOfPegs){
        this.numberOfPegs=numberOfPegs;
        this.board = new MastermindBoard(this.codeLength, this.numberOfPegs);
    }

    /**
     * Set the secret codes for both players.
     * @param secretCodePlayer1 the secret code for player1
     * @param secretCodePlayer2 the secret code for player2
     */
    public void setSecretCodes(int[] secretCodePlayer1, int[] secretCodePlayer2)
    {
        board.setSecretCodes(secretCodePlayer1,secretCodePlayer2);
    }

    /**
     *
     */

    public void setSecretCodePlayer1(int[] secretCodePlayer1){
        board.setSecretCodePlayer1(secretCodePlayer1);
    }

    /**
     *
     */

    public void setSecretCodePlayer2(int[] secretCodePlayer2){
        board.setSecretCodePlayer2(secretCodePlayer2);
    }

    /**
     * Sets the algorithm that the game will use to solve the secretCode.
     * @param currentAlgorithm The name of the algorithm to be used.
     */
    public void setCurrentAlgorithm(String currentAlgorithm) {
        this.currentAlgorithm = currentAlgorithm;
    }

    /**
     * Sets the difficulty level of the game.
     * @param difficultyLevelName The name of the difficulty level.
     */
    public void setDifficultyLevel1(String difficultyLevelName) {
        this.difficultyLevel = new DifficultyLevel(difficultyLevelName, this.difficultyLevel.getNumGuesses(), this.difficultyLevel.isCanGetHelp());
    }

    /**
     * Sets the number of guesses allowed in the game.
     * @param numGuesses The maximum number of guesses allowed.
     */
    public void setNumGuesses(int numGuesses){
        difficultyLevel.setNumGuesses(numGuesses);
    }

    /**
     * Sets the total number of rounds for the game.
     * @param totalRounds The total number of rounds to be played.
     */
    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    /**
     * Sets the current round number.
     * @param currentRound The round number to be set.
     */
    public void setCurrentRound(int currentRound) {
        this.currentRound=currentRound;
    }

    /**
     * Sets the guesses made by player 1.
     * @param guessesPlayer1 list of integer arrays, each representing a guess by player 1.
     */
    public void setGuessesPlayer1(List<int[]> guessesPlayer1) {
        board.setGuessesPlayer1(guessesPlayer1);
    }

    /**
     * Sets the guesses made by player 2.
     * @param guessesPlayer2 list of integer arrays, each representing a guess by player 2.
     */
    public void setGuessesPlayer2(List<int[]> guessesPlayer2) {
        board.setGuessesPlayer2(guessesPlayer2);
    }

    /**
     * Sets the feedback for player 1's guesses.
     * @param feedbackPlayer1 list of integer arrays, each representing the feedback for a guess by player 1.
     */
    public void setFeedbackPlayer1(List<int[]> feedbackPlayer1) {
        StringBuilder sb = new StringBuilder();
        for (int[] feedback : feedbackPlayer1) {
            sb.append(Arrays.toString(feedback)).append(", ");
        }
        if(sb.length() > 0) {  // Check if string builder is not empty
            sb.setLength(sb.length() - 2);  // Remove the last ", "
        }

        System.out.println("The feedbacks we are trying to set are: " + sb.toString());
        board.setFeedbackPlayer1(feedbackPlayer1);
    }


    /**
     * Sets the feedback for player 2's guesses.
     * @param feedbackPlayer2 list of integer arrays, each representing the feedback for a guess by player 2.
     */
    public void setFeedbackPlayer2(List<int[]> feedbackPlayer2) {
        board.setFeedbackPlayer2(feedbackPlayer2);
    }

    /**
     *
     * @param ranking The Ranking object to be used.
     */
    public void setRanking(Ranking ranking){
        this.ranking=ranking;
    }

    /**
     * Sets the score of player 1.
     * @param scorePlayer1 The score to be set for player 1.
     */
    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    /**
     * Sets the score of player 2.
     * @param scorePlayer2 The score to be set for player 2.
     */
    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    /**
     * Sets the number of rounds that have been played.
     * @param roundsPlayed The number of rounds played.
     */
    private void setRoundsPlayed(int roundsPlayed) {
        this.currentRound=roundsPlayed;
    }

    /**
     * Converts a string representation of a list of integer arrays into a list of integer arrays.
     * @param s The string to be converted.
     * @return A list of integer arrays.
     */
    private List<int[]> SetStringToListOfArrays(String s) {
        String[] stringArrays = s.split("],\\[");
        return Arrays.stream(stringArrays)
                .map(this::SetStringToArray)
                .collect(Collectors.toList());
    }

    /**
     * Converts a string representation of an integer array into an integer array.
     * @param str The string to be converted.
     * @return An integer array.
     */
    public int[] SetStringToArray(String str){
        return Arrays.stream(str.replace("[", "").replace("]", "").split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    /**
     * Create a human player.
     * @param name the name of the human player.
     */
    public void createHumanPlayer(String name){
        player1=new Human(codeLength,numberOfPegs,name);
    }

    /**
     * Create an AI player of a given type.
     * @param type the type of AI player, either "genetic" or "fiveGuess".
     * @return a Player object representing the AI player.
     */
    public Player createAIPlayer(String type){
        if(type.equalsIgnoreCase("Genetic algorithm")) {
            player2 = new GeneticComputerPlayer(codeLength,numberOfPegs,maxSteps,100,0.7,0.1);
            System.out.println("Genetic Player is created with maxSteps: " + maxSteps);
        } else if(type.equalsIgnoreCase("Five Guess algorithm")) {
            player2 = new FiveGuess(codeLength,numberOfPegs,maxSteps);
        } else {
            throw new IllegalArgumentException("Invalid AI type. Please select either 'genetic' or 'fiveGuess'");
        }
        return player2;
    }

    /**
     * Update the score for a player.
     * @param playerName the name of the player whose score needs to be updated.
     * @param score the new score for the player.
     */
    public void updateRanking(String playerName, int score){
        ranking.updatePlayerScore(playerName,score);
    }

    /**
     * If the guess made by the player is correct or not
     * @param guessPlayer1 guesses made by player1
     * @param b if the guess is made by player1 or player2, true if player1
     * @return boolean value, true if guess is correct, false otherwise
     */
    public boolean isCorrectGuess(int[] guessPlayer1, boolean b) {
        return board.isCorrectGuess(guessPlayer1,b);
    }

    /**
     * Allows us to choose between two algorithm, be it fiveGuess or Genetic
     * @param algorithm the algorithm selected.
     */
    public void selectAlgorithm(String algorithm)
    {
        if(algorithm.equals("Five Guess algorithm")){
            AIPlayer= new FiveGuess(codeLength,numberOfPegs,maxSteps);
        }
        else if(algorithm.equals("Genetic algorithm")){
            AIPlayer= new GeneticComputerPlayer(codeLength,numberOfPegs,maxSteps,100,0.7,0.1);
        }
        else{
            throw new IllegalArgumentException("Please select the right algorithm");
        }
        this.currentAlgorithm = algorithm; //current algorithm
    }

    /**
     * Ue the AI player to solve the game after selecting the algorithm
     * Select algorithm should be called before using solveUsingAlgorithm.
     * @param secretCode The code to be solved by the AI player.
     * @return A list of lists of integers, where each list represents a guess by the AI player.
     * @throws Exception If any errors occur during solving.
     */
    public List<List<Integer>> solveUsingAlgorithm(List<Integer> secretCode) throws Exception {
        if (this.player2 instanceof FiveGuess) {
            return ((FiveGuess) this.player2).solve(secretCode);
        }
        return ((GeneticComputerPlayer) this.player2).solve(secretCode);
    }

    /**
     * Sets the difficulty level of the game.
     * @param difficulty A string representing the desired difficulty level.
     */
    public void selectDifficultyLevel(String difficulty){
        switch (difficulty.toUpperCase()) {
            case "EASY":
                this.difficultyLevel = new DifficultyLevel("EASY", 12, true);
                this.maxSteps = 12;
                break;
            case "MEDIUM":
                this.difficultyLevel = new DifficultyLevel("MEDIUM", 10, true);
                this.maxSteps=10;
                break;
            case "HARD":
                this.difficultyLevel = new DifficultyLevel("HARD", 8, false);
                this.maxSteps=8;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level");
        }
    }

    /**
     * Checks if the player is not null.
     * @return true if the player is not null, false otherwise.
     */
    public boolean playerNotNull() {
        return (codeLength >0 && numberOfPegs > 0 && getCurrentAlgorithm() != null);
    }

    /**
     * Generates the secret code for player 2.
     * @return An integer array representing the secret code.
     */
    public String generateSecretCodePlayer2(){
        int[] secretCode = player2.generateSecretCode();
        return Arrays.toString(secretCode);
    }


    /**
     * Provides basic information about the game.
     * @return A string containing the game information.
     */

    public String getInfo() {
        return "This is a deduction game where the Code-maker, creates a secret code and the Codebreaker, tries to guess that code. Here are some basic rules:\n" +
                "\n" +
                "1. The Code-maker creates a secret code which is a sequence of colors. The number of colors and the length of the code depend on the game settings you choose.\n" +
                "\n" +
                "2. The Codebreaker makes a guess of the secret code. A guess is a sequence of colors, the same length as the secret code.\n" +
                "\n" +
                "3. After each guess, the Code-maker gives feedback to the Codebreaker which includes two types of information:\n" +
                "\n" +
                "       - The black pegs represent the number of pegs that are the right color and in the right position.\n" +
                "\n" +
                "       - The white pegs represent number of pegs that are the right color but in the wrong position.\n" +
                "\n" +
                "4. The Codebreaker should use this feedback to make more informed guesses in the upcoming turns.\n" +
                "\n" +
                "5. The game continues until the Codebreaker guesses the secret code correctly, or until a maximum number of turns have passed.\n" +
                "\n" +
                "6. At the end of the game, the player with the highest score wins.\n" +
                "\n" +
                "Use the feedback and find the secret code. GOOD LUCK!";
    }

    /**
     * Resets the game with the same settings.
     */
    public void resetGameSameSettings() {
        instance = new CtrlDomini();
    }

    /**
     * Saves the current state of the game to the specified path.
     * @param path The path where the game state should be saved.
     */
    public void save(String path){
        GameSave game = new GameSave(this);
        CtrlPersistencia.save(game,path);

        System.out.println("Saving game: -----------------------------------------------------------------------------------------------------------");
        System.out.println("Player1 Name: " + game.getPlayer1Name());
        System.out.println("Player2 Name: " + game.getPlayer2Name());
        System.out.println("Code Length: " + game.getCodeLength());
        System.out.println("Number of Pegs: " + game.getNumberOfPegs());
        System.out.println("SecretCodePlayer1: " + Arrays.toString(game.getSecretCodePlayer1()));
        System.out.println("SecretCodePlayer2: " + Arrays.toString(game.getSecretCodePlayer2()));
        System.out.println("FeedBackPlayer1: " + game.getFeedbackPlayer1().stream().map(Arrays::toString).collect(Collectors.joining(",")));
        System.out.println("FeedBackPlayer2: " + game.getFeedbackPlayer2().stream().map(Arrays::toString).collect(Collectors.joining(",")));
        System.out.println("GuessesPlayer1: " + game.getGuessesPlayer1().stream().map(Arrays::toString).collect(Collectors.joining(",")));
        System.out.println("GuessesPlayer2: " + game.getGuessesPlayer2().stream().map(Arrays::toString).collect(Collectors.joining(",")));
        System.out.println("Player1 Score: " + game.getScorePlayer1());
        System.out.println("Player1 Score: " + game.getScorePlayer2());
        System.out.println("AlgorithmUsed: " + game.getAlgorithmUsed());
        System.out.println("Difficulty level: " + game.getDifficultyLevel1());
        System.out.println("Total Rounds: " + game.getTotalRounds());
        System.out.println("Rounds Played: " + game.getRoundsPlayed());
    }

    /**
     * Saves the records to a file.
     */
    public void saveRecordsToFile(){
        cp.saveRecordsToFile(ranking);
    }

    /**
     * Loads a game state from the specified path.
     * @param path The path where the game state is stored.
     */
    public void load(String path) {
        GameSave game = CtrlPersistencia.load(path);
        if(game != null){
            this.codeLength= game.getCodeLength();
            System.out.println("Loaded Code Length: " + this.codeLength);


            this.numberOfPegs= game.getNumberOfPegs();
            System.out.println("Loaded Number of Pegs: " + this.numberOfPegs);

            this.setSecretCodePlayer1(game.getSecretCodePlayer1());
            System.out.println("Loaded SecretCodePlayer1: " + Arrays.toString(game.getSecretCodePlayer1()));


            this.setSecretCodePlayer2(game.getSecretCodePlayer2());
            System.out.println("Loaded SecretCodePlayer2: " + Arrays.toString(game.getSecretCodePlayer2()));

            this.setGuessesPlayer1(game.getGuessesPlayer1());
            System.out.println("Loaded GuessesPlayer1: " + game.getGuessesPlayer1().stream().map(Arrays::toString).collect(Collectors.joining(",")));

            this.setGuessesPlayer2(game.getGuessesPlayer2());
            System.out.println("Loaded GuessesPlayer2: " + game.getGuessesPlayer2().stream().map(Arrays::toString).collect(Collectors.joining(",")));

            this.setFeedbackPlayer1(game.getFeedbackPlayer1());
            System.out.println("Loaded FeedbackPlayer1: " + game.getFeedbackPlayer1().stream().map(Arrays::toString).collect(Collectors.joining(",")));

            this.setFeedbackPlayer2(game.getFeedbackPlayer2());
            System.out.println("Loaded FeedbackPlayer2: " + game.getFeedbackPlayer2().stream().map(Arrays::toString).collect(Collectors.joining(",")));


            if(game.getPlayer1Name() != null) {
                this.setPlayer1Name(game.getPlayer1Name());
            } else {
                System.err.println("Player 1 name is null!");
            }

            if(game.getPlayer2Name() != null) {
                this.setPlayer2Name(game.getPlayer2Name());
            } else {
                System.err.println("Player 2 name is null!");
            }
            this.setScorePlayer1(game.getScorePlayer1());
            System.out.println("Loaded ScorePlayer1: " + game.getScorePlayer1());

            this.setScorePlayer2(game.getScorePlayer2());
            System.out.println("Loaded ScorePlayer2: " + game.getScorePlayer2());

            this.setCurrentAlgorithm(game.getAlgorithmUsed());
            System.out.println("Loaded Algorithm Used: " + this.currentAlgorithm);

            this.setDifficultyLevel1(game.getDifficultyLevel1());
            System.out.println("Loaded Difficulty Level: " + game.getDifficultyLevel1());

            this.setTotalRounds(game.getTotalRounds());
            System.out.println("Loaded Total Rounds: " + this.totalRounds);

            this.setRoundsPlayed(game.getRoundsPlayed());
            System.out.println("Loaded Rounds Played: " + this.currentRound);

            this.setNumGuesses(game.getMaxSteps());
            System.out.println("Loaded Max Steps: " + this.maxSteps);

        }

    }

    public int[] changeStringToArray(String s ){
        return Arrays.stream(s.substring(1, s.length() - 1).split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public List<int[]> changeStringToListOfArrays(String s){
        return Arrays.stream(s.split(";"))
                .map(this::changeStringToArray)
                .collect(Collectors.toList());
    }

}
