package main.CapaDomini.Models;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MastermindBoard {

    private int codeLength;
    private int numberOfPegs;
    private int[] secretCodePlayer1;
    private int[] secretCodePlayer2;
    private List<int[]> guessesPlayer1;
    private List<int[]> guessesPlayer2;
    private List<int[]> feedbackPlayer1;
    private List<int[]> feedbackPlayer2;

    /**
     * Constructor to create a MastermindBoard with a given code length and number of pegs.
     * It also initializes the guess and feedback lists for both players.
     *
     * @param codeLength The length of the secret code.
     * @param numberOfPegs The number of pegs used in the game.
     */
    public MastermindBoard(int codeLength, int numberOfPegs) {
        this.codeLength = codeLength;
        this.numberOfPegs = numberOfPegs;

        this.secretCodePlayer1 = new int[codeLength];
        this.secretCodePlayer2 = new int[codeLength];

        this.guessesPlayer1 = new ArrayList<>();
        this.guessesPlayer2 = new ArrayList<>();
        this.feedbackPlayer1 = new ArrayList<>();
        this.feedbackPlayer2 = new ArrayList<>();
    }

    /**
     * Returns the length of the secret code.
     *
     * @return The length of the secret code.
     */
    public int getCodeLength() {
        return codeLength;
    }

    /**
     * Returns the number of pegs used in the game.
     *
     * @return The number of pegs used in the game.
     */
    public int getNumberOfPegs() {
        return numberOfPegs;
    }

    /**
     * Returns player 1's secret code.
     *
     * @return Player 1's secret code.
     */
    public int[] getSecretCodePlayer1() {
        return secretCodePlayer1;
    }

    /**
     * Sets the secret code of player 1
     * @param secretCodePlayer1 the secret code for the player 1.
     */
    public void setSecretCodePlayer1(int[] secretCodePlayer1) {
        this.secretCodePlayer1 = secretCodePlayer1;
    }

    /**
     * Returns player 2's secret code.
     *
     * @return Player 2's secret code.
     */

    public int[] getSecretCodePlayer2() {
        return secretCodePlayer2;
    }

    /**
     * Sets the secret code of player 2
     * @param secretCodePlayer2 the secret code for the player 2.
     */

    public void setSecretCodePlayer2(int[] secretCodePlayer2) {
        this.secretCodePlayer2 = secretCodePlayer2;
    }

    /**
     * Returns player 1's guesses.
     *
     * @return Player 1's guesses.
     */
    public List<int[]> getGuessesPlayer1() {
        return guessesPlayer1;
    }

    /**
     * Sets the guesses for player1 especially after loading the game
     * @param guessesPlayer1 The guesses of player 1
     */
    public void setGuessesPlayer1(List<int[]> guessesPlayer1) {
        this.guessesPlayer1 = guessesPlayer1;
    }

    /**
     * Returns player 2's guesses.
     *
     * @return Player 2's guesses.
     */

    public List<int[]> getGuessesPlayer2() {
        return guessesPlayer2;
    }

    /**
     * Sets the guesses for player 2, mainly after loading the game
     * @param guessesPlayer2 the guesses made by player2
     */
    public void setGuessesPlayer2(List<int[]> guessesPlayer2) {
        this.guessesPlayer2 = guessesPlayer2;
    }

    /**
     * Returns player 1's feedback.
     *
     * @return Player 1's feedback.
     */
    public List<int[]> getFeedbackPlayer1() {
        return feedbackPlayer1;
    }

    /**
     * Sets the feedback for player1, especially after loading the game
     * @param feedbackPlayer1 The feedback for player1
     */
    public void setFeedbackPlayer1(List<int[]> feedbackPlayer1) {
        this.feedbackPlayer1 = feedbackPlayer1;
    }

    /**
     * Returns player 2's feedback.
     *
     * @return Player 2's feedback.
     */
    public List<int[]> getFeedbackPlayer2() {
        return feedbackPlayer2;
    }

    /**
     * Sets the feedback for player2
     * @param feedbackPlayer2 the feedbacks for player2
     */

    public void setFeedbackPlayer2(List<int[]> feedbackPlayer2) {
        this.feedbackPlayer2 = feedbackPlayer2;
    }

    /**
     * Sets the secret codes for players 1 and 2.
     *
     * @param secretCodePlayer1 The secret code for player 1.
     * @param secretCodePlayer2 The secret code for player 2.
     */
    public void setSecretCodes(int[] secretCodePlayer1, int[] secretCodePlayer2)
    {
        this.secretCodePlayer1=secretCodePlayer1;
        this.secretCodePlayer2=secretCodePlayer2;
    }

    /**
     * Checks if the given guess is correct, for either player 1 or player 2.
     *
     * @param guess The guess to check.
     * @param forPlayer1 A boolean indicating whether the guess is for player 1.
     * @return true if the guess matches the secret code, false otherwise.
     */
    public boolean isCorrectGuess(int[] guess, boolean forPlayer1)
    {
        //check if the given guess matches the secret code, either for player1 or player2
        int[] secretCode = forPlayer1 ? secretCodePlayer1 : secretCodePlayer2;
        return Arrays.equals(guess,secretCode);
    }

    /**
     * Returns feedback for a given guess compared to the secret code.
     * Feedback consists of the number of pegs in the correct position and the number of pegs in the wrong position.
     *
     * @param guess The guess to check.
     * @param secretCode The secret code to compare the guess to.
     * @return An array with the number of pegs in the correct position and the number of pegs in the wrong position.
     */
    public int[] getFeedbackForGuess(int[] guess, int[] secretCode) {
        if (guess == null || secretCode == null) {
            throw new IllegalArgumentException("Guess and secretCode cannot be null.");
        }
        int correctPosition = 0;
        int incorrectPosition = 0;
        //to store counts of each peg in secret code and guesses
        int[] secretCodeCount = new int[this.numberOfPegs];
        int[] guessCount = new int[this.numberOfPegs];
        //iterating through 2 arrays to increment the counter for positions
        for(int i=0; i< secretCode.length; i++){
            if(guess[i] == secretCode[i]){
                correctPosition++;
            } else {
                if (secretCode[i] < 1 || secretCode[i] > numberOfPegs || guess[i] < 1 || guess[i] > numberOfPegs) {
                    throw new IllegalArgumentException("Values in guess and secretCode must be between 1 and " + numberOfPegs);
                }
                //to only count the number of pegs that appear in both the secret code and guess
                secretCodeCount[secretCode[i]-1]++;
                guessCount[guess[i] - 1]++;
            }
        }
        for(int i=0; i<numberOfPegs; i++){
            incorrectPosition += Math.min(secretCodeCount[i],guessCount[i]);
        }
        return new int[]{correctPosition, incorrectPosition};
    }

    /**
     * Provides feedback for a given guess, stores the guess and feedback in the appropriate lists,
     * and returns the feedback. The feedback consists of the number of pegs in the correct position
     * and the number of pegs in the wrong position.
     *
     * @param guess The guess to check.
     * @param forPlayer1 A boolean indicating whether the guess is for player 1.
     * @return An array with the number of pegs in the correct position and the number of pegs in the wrong position.
     */
    public int[] getFeedback(int[] guess, boolean forPlayer1)
    {
        int[] secretCode = forPlayer1 ? secretCodePlayer1 : secretCodePlayer2;
        int[] feedback = getFeedbackForGuess(guess, secretCode);

        if (forPlayer1) {
            guessesPlayer1.add(guess);
            feedbackPlayer1.add(feedback);
        } else {
            guessesPlayer2.add(guess);
            feedbackPlayer2.add(feedback);
        }
        return feedback;
    }


}
