package main.CapaDomini.Models;


import java.util.Scanner;

/**
 * It extends the class Player with the implementation of abstract methods mentioned in class Player.
 * It represents a human player and can act as both CodeMaker and CodeBreaker
 */
public class Human extends Player {

    //scanner obect to read player input
    private final Scanner scanner;

    /**
     * Constructs a new Human player with the given name, code length and number of pegs.
     * @param codeLength the length of the secret code
     * @param numberOfPegs the number of pegs used in the game
     * @param name the name of the player
     */
    public Human(int codeLength, int numberOfPegs, String name)
    {
        super(codeLength, numberOfPegs, name);
        scanner=new Scanner(System.in);
    }

    /**
     * To enter the secret code
     * @return the secret code entered by the player
     */
    @Override

    public int[]generateSecretCode()
    {
        System.out.println("Enter your secret code:");
        int[] code = new int[codeLength];

        for(int i=0; i< codeLength; i++){
            code[i]=scanner.nextInt();
        }
        return code;
    }

    /**
     * a prompt to make a guess
     * @return the guess made by the player
     */
    @Override
    public int[] makeGuess() {
        System.out.println("Enter your guess:");
        int[] guess = new int[codeLength];

        for(int i=0; i<codeLength; i++){
            guess[i]= scanner.nextInt();
        }
        return guess;
    }



}
