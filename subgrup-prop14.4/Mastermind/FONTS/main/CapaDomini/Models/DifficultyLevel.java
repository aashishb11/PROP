package main.CapaDomini.Models;


/**
 * This class represents a difficulty level for the game.
 * Each difficulty level has a name, a number of guesses and an indicator if help is available.
 */
public class DifficultyLevel {

    // The name of the difficulty level
    private String name;

    // The number of guesses allowed in this difficulty level
    private int numGuesses;

    // if help is available in this difficulty level
    private boolean canGetHelp;
    /**
     * Constructor to create a DifficultyLevel with a given name, number of guesses,
     * and an indicator if help is available.
     * @param name The name of the difficulty level.
     * @param numGuesses The number of guesses allowed in this difficulty level.
     * @param canGetHelp A boolean indicating if help can be obtained in this difficulty level.
     */
    public DifficultyLevel(String name, int numGuesses, boolean canGetHelp) {
        this.name = name;
        this.numGuesses = numGuesses;
        this.canGetHelp = canGetHelp;
    }

    /**
     * Gets the name of the difficulty level.
     * @return The name of the difficulty level.
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the number of guesses allowed in this difficulty level.
     * @return The number of guesses allowed in this difficulty level.
     */
    public int getNumGuesses() {
        return numGuesses;
    }

    /**
     * sets the number of guesses allowed for the player.
     * @param numGuesses is the number of guesses allowed in this difficulty level.
     */
    public void setNumGuesses(int numGuesses) {
        this.numGuesses = numGuesses;
    }
    /**
     * Checks if help can be obtained in this difficulty level.
     *
     * @return A boolean indicating if help can be obtained in this difficulty level.
     */
    public boolean isCanGetHelp() {
        return canGetHelp;
    }
}
