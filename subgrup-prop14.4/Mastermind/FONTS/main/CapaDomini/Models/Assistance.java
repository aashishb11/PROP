package main.CapaDomini.Models;

import java.util.Arrays;
import java.util.Random;

/**
 * This class is to assist a player by providing feedback
 * about the guesses made by a player.
 */
public class Assistance {

    //Object og Mastermind board
    private MastermindBoard board;

    // Random number generator for picking random peg and position.
    private Random random;

    private int index = 1;


    /**
     * Constructor to initialize Assistance with a given MastermindBoard.
     *
     * @param board The board of the Mastermind game.
     */
    public Assistance(MastermindBoard board)
    {
        this.board=board;
        this.random= new Random();
    }

    /**
     * Provides feedback about the number of pegs placed in the right position
     * in the last guess made by the player.
     *
     * @param forPlayer1 Boolean to specify if feedback is for player 1.
     * @return String feedback about the number of correctly positioned pegs.
     */
    public String getRightPegsInRightPosition(boolean forPlayer1)
    {
        int[] lastFeedback = forPlayer1 ? board.getFeedbackPlayer1().get(board.getFeedbackPlayer1().size() - 1) : board.getFeedbackPlayer2().get(board.getFeedbackPlayer2().size() - 1);

        return "There are " + lastFeedback[0] + " peg(s) in the right position.";
    }

    /**
     * Provides feedback about the number of correct pegs placed in the wrong position
     * in the last guess made by the player.
     *
     * @param forPlayer1 Boolean to specify if feedback is for player 1.
     * @return String feedback about the number of correct but wrongly positioned pegs.
     */
    public String getRightPegsInWrongPosition(boolean forPlayer1) {

        int[] lastFeedback = forPlayer1 ? board.getFeedbackPlayer1().get(board.getFeedbackPlayer1().size() - 1) : board.getFeedbackPlayer2().get(board.getFeedbackPlayer2().size() - 1);

        return "There are " + lastFeedback[1] + " peg(s) that are correct but in the wrong position.";
    }

    /**
     * Gives a hint to the player by revealing the position of the next peg in the secret code.
     *
     * @param forPlayer1 Boolean to specify if the hint is for player 1.
     * @return String hint about a peg and its position in the secret code.
     */
    public String getNextCorrectPegAndPosition(boolean forPlayer1) {
        int[] secretCode = forPlayer1 ? board.getSecretCodePlayer1() : board.getSecretCodePlayer2();
        System.out.println(Arrays.toString(secretCode));
        int pegValue = secretCode[this.index-1];
        return pegValue + " " + (this.index);
    }

    public void nextPeg(){
        ++this.index;
        if(index==5)index=1;
    }
}
