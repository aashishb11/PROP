package main.CapaDomini.Models;

import main.CapaDomini.Controllers.CtrlDomini;

import java.io.Serializable;
import java.util.List;

/**
 * This class is a serializable class which is used to save and load the instance of the game to resume play
 */
public class GameSave implements Serializable {

    //deserialize same class that was serialized
    private static final long serialVersionUID = 1L;

    //To maintain players rank in the game

    // game of code length and number of pegs
    private final int codeLength;
    private final int numberOfPegs;

    //to store the secret codes of the players
    private final int[] secretCodePlayer1;
    private final int[] secretCodePlayer2;

    //to store the guesses made by the players
    private final List<int[]> guessesPlayer1;
    private final List<int[]> guessesPlayer2;

    //to store the feedbacks provided to the players
    private final List<int[]> feedbackPlayer1;
    private final List<int[]> feedbackPlayer2;

    //player's names
    private final String player1Name;
    private final String player2Name;

    //player's scores
    private final int scorePlayer1;
    private final int scorePlayer2;

    //algorithm used, either genetic or five guess
    private final String algorithmUsed;

    //to store the difficulty level of the game
    private final String difficultyLevel1;

    //to store total number of rounds and rounds played
    private final int totalRounds;
    private final int roundsPlayed;

    private final int maxSteps;

    /**
     * Constructor
     * @param cd Control domini instance
     */
    public GameSave(CtrlDomini cd){

        this.codeLength= Integer.parseInt(cd.getCodeLength());
        this.numberOfPegs=Integer.parseInt(cd.getNumberOfPegs());


        this.secretCodePlayer1= cd.getSecretCodePlayer1();
        this.secretCodePlayer2= cd.changeStringToArray(cd.getSecretCodePlayer2());

        this.guessesPlayer1= cd.changeStringToListOfArrays(cd.getGuessesPlayer1());
        this.guessesPlayer2=cd.getGuessesPlayer2();


        this.feedbackPlayer1=cd.changeStringToListOfArrays(cd.getFeedbackPlayer1());
        this.feedbackPlayer2=cd.getFeedbackPlayer2();


        this.player1Name=cd.getPlayer1name();
        this.player2Name=cd.getPlayer2name();


        this.algorithmUsed=cd.getCurrentAlgorithm();
        this.difficultyLevel1=cd.getDifficultyLevel1();


        this.totalRounds=Integer.parseInt(cd.getTotalRounds());
        this.roundsPlayed=Integer.parseInt(cd.getCurrentRound());


        this.scorePlayer1= Integer.parseInt(cd.getScorePlayer1());
        this.scorePlayer2=Integer.parseInt(cd.getScorePlayer2());


        this.maxSteps=Integer.parseInt(cd.getNumGuesses());
    }

    /**
     * Necessary getters methods to implement the game saving and loading mechanism in the game
     */
    public int getCodeLength() {
        return codeLength;
    }

    public int getNumberOfPegs() {
        return numberOfPegs;
    }


    public int[] getSecretCodePlayer1() {
        return secretCodePlayer1;
    }

    public int[] getSecretCodePlayer2() {
        return secretCodePlayer2;
    }


    public List<int[]> getGuessesPlayer1() {
        return guessesPlayer1;
    }


    public List<int[]> getGuessesPlayer2() {
        return guessesPlayer2;
    }


    public List<int[]> getFeedbackPlayer1() {
        return feedbackPlayer1;
    }

    public List<int[]> getFeedbackPlayer2() {
        return feedbackPlayer2;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }


    public int getScorePlayer2() {
        return scorePlayer2;
    }


    public String getAlgorithmUsed() {
        return algorithmUsed;
    }


    public String getDifficultyLevel1() {
        return difficultyLevel1;
    }


    public int getTotalRounds() {
        return totalRounds;
    }


    public int getRoundsPlayed() {
        return roundsPlayed;
    }


    public int getMaxSteps(){
        return  maxSteps;
    }


}
