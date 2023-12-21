package main.CapaDomini.Models;


import java.util.Random;

/**
 * Represents the abstract class player.
 * It has two subclasses Human player and computer-player.
 * Object of class player cannot be created, it only contains abstract methods
 * Reason to be abstract : Two possible players human and computer with their own specific methods.
 */
public abstract class Player {
    private String name; //Name of the player

    private int turnsTaken; //The number of turns taken by the player

    protected int numberOfPegs; //The number of pegs in the game

    protected int codeLength; //The length of the secret code

    /**
     * Constructor of class Player
     */
    public Player(int codeLength, int numberOfPegs, String name)
    {
        this.name=name;
        this.codeLength=codeLength;
        this.turnsTaken=0;
        this.numberOfPegs=numberOfPegs;
    }

    /**
     * Method to get the player's name
     */

    public String getName() {
        return name;
    }

    /**
     * Method to set the player's name
     * @param name The name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *  Method to get the number of turns taken by the player
     * @return the number of turns taken by the player
     */
    public int getTurnsTaken() {
        return turnsTaken;
    }

    /**
     * Method to increment the number of turns taken by the player
     */
    public void incrementTurnsTaken()
    {
        turnsTaken++;
    }

    /**
     *  Abstract method to generate the secret code.
     * @return the secret code
     */

    public abstract int[]generateSecretCode();
    //int[] -> we know the size of secret code

    /**
     *  Abstract method to make a guess.
     * @return the guess made.
     */
    public abstract int[] makeGuess();

    /**
     *  Method to generate a random secret code for the game
     * @return the random code.
     */

    //random code the codebreaker has to guess until the implementation of AI
    protected int[] generateRandomCode()
    {
        Random random=new Random();
        int[] code=new int[codeLength];

        for(int i=0; i<codeLength; i++){
            code[i]=random.nextInt(numberOfPegs)+1;
        }
        return code;
    }


}
