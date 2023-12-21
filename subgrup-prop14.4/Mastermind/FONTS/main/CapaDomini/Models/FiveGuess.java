package main.CapaDomini.Models;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class extends the "Player" class and implements the "Maquina" interface
 * It is designed to implement the five-guess algorithm to play the Mastermind Game
 */
public class FiveGuess extends Player implements Maquina{

    //max number of steps that the algorithm will take to find a solution
    private final int maxSteps;

    //All possible combination of pegs
    private final List<int[]> allPossibleCodes;

    //The set of codes that have not been eliminated by the feedback yet
    private List<int[]> remainingCodes;

    //The current guess of the algorithm
    private int[] currentGuess;

    /**
     * Constructor to initialize the FiveGuess player with a given codeLength,
     * no. of pegs and maximum steps
     * @param codeLength Length of the secret code to be guessed.
     * @param numberOfPegs Number of possible pegs that can be used in the code.
     * @param maxSteps Maximum number of steps that the algorithm will take to find a solution.
     */

    public FiveGuess(int codeLength, int numberOfPegs, int maxSteps) {
        super(codeLength, numberOfPegs, "Five Guess AI");
        this.maxSteps = maxSteps;
        this.allPossibleCodes = generateAllPossibleCodes();
        this.remainingCodes = new ArrayList<>(allPossibleCodes);
        this.currentGuess = new int[] {1,1,2,2}; // no.of pegs >= 2
    }

    /**
     * Generates all possible code combinations for the current game parameters
     * @return List of all possible codes.
     */

    private List<int[]> generateAllPossibleCodes() {
        int[] code = new int[codeLength];
        List<int[]> codes = new ArrayList<>();
        generateAllPossibleCodesHelper(code, codes, 0);
        return codes;
    }

    /**
     * Helper function to fill the list with all possible codes recursively.
     *
     * @param code Current code being constructed.
     * @param codes List of codes being filled.
     * @param index Current index in the code.
     */
    private void generateAllPossibleCodesHelper(int[] code, List<int[]> codes, int index) {
        //if filled, add to the list of codes
        if (index == codeLength) {
            codes.add(code.clone());
            return;
        }
        //try all possible peg for current position
        for (int i = 1; i <= numberOfPegs; i++) {
            code[index] = i;
            generateAllPossibleCodesHelper(code, codes, index + 1);
        }
    }
    /**
     * Reinitialized the values of the remaining possible solutions and the current guess once the solution is found
     */
    private void reinitialize(){
        this.remainingCodes = new ArrayList<>(allPossibleCodes);
        this.currentGuess = new int[] {1,1,2,2}; // no.of pegs >= 2
    }

    /**
     * Solves the game for provided solution using FiveGuess Algorithm
     * @param solution The secret code that needs to be guessed.
     * @return List of codes guessed by the algorithm in order.
     * @throws Exception If the provided solution is not valid.
     */
    @Override
    public List<List<Integer>> solve(List<Integer> solution) throws Exception
    {
        System.out.println("remainingCodes: " + remainingCodes);
        System.out.println("currentGuess: " + Arrays.toString(currentGuess));

        if(!isValidSolution(solution)){
            throw new Exception("the solution is not consistent with the game parameters");
        }
        List<List<Integer>> codeList = new ArrayList<>();
        //to convert solution(list<integer>) to int[].
        //create stream from list, map each int object to its primitive int
        //save result to an array
        int[] secretCode = solution.stream().mapToInt(Integer::intValue).toArray();
        MastermindBoard board = new MastermindBoard(codeLength, numberOfPegs);
        for (int step = 0; step < maxSteps; step++) {
            int[] guess = currentGuess;
            //add new guess(int[]) to a list of guesses.
            //create stream from int[], box each int to its corresponding int object
            //collect result into a list<int> then add to codeList
            codeList.add(Arrays.stream(guess).boxed().collect(Collectors.toList()));
            int[] feedback = board.getFeedbackForGuess(guess, secretCode);
            if(feedback[0] == codeLength){
                reinitialize();
                break;
            }
            //remove remaining codes that wouldn't give the same feedback if the current guess would be the secret code
            remainingCodes.removeIf(code -> !Arrays.equals(feedback, board.getFeedbackForGuess(code, guess)));
            if(!remainingCodes.isEmpty()){
                minmax(board);
            } else{
                break;
            }
        }
        return codeList;
    }

    /**
     * Check if the solution is valid with the game parameters
     * @param solution The solution to be checked.
     * @return True if the solution is valid, false otherwise.
     */
    private boolean isValidSolution(List<Integer> solution) {
        if(solution == null || solution.size() != codeLength) return false;
        for(int peg : solution){
            if(peg < 1 || peg > numberOfPegs) return false;
        }
        return true;
    }

    /**
     * Generate a random secret code.
     * @return The generated secret code.
     */
    @Override
    public int[] generateSecretCode() {
        return generateRandomCode();
    }
    /**
     * Makes a guess by choosing a code from the middle of all possible codes.
     * @return The guess made by the algorithm.
     */
    @Override
    public int[] makeGuess() {
        int[] guess = allPossibleCodes.get(allPossibleCodes.size() / 2);
        allPossibleCodes.remove(guess);
        return guess;
    }
    /**
     * Implements the MinMax part of the Five Guess algorithm.
     * Chooses the guess that minimizes the maximum possible remaining codes in the worst case.
     * @param board Current game board.
     */
    private void minmax(MastermindBoard board){
        HashMap<int[],Integer> possible_scores = new HashMap<>();
        HashMap<int[],Integer> max_possible_scores = new HashMap<>();
        ArrayList<int[]> possible_next_guess = new ArrayList<>();
        //calculate scores for all possible guesses
        for(int[] untried : remainingCodes) {
            for(int[] possible_solution : remainingCodes) {
                int[] feedback = board.getFeedbackForGuess(untried,possible_solution);
                if(possible_scores.containsKey(feedback)) possible_scores.replace(feedback,possible_scores.get(feedback)+1);
                else possible_scores.put(feedback,1);
            }
            int maxScore = (Collections.max(possible_scores.values()));
            max_possible_scores.put(untried,maxScore);
            possible_scores.clear();
        }
        //minimum score
        int min = (Collections.min(max_possible_scores.values()));
        max_possible_scores.entrySet().stream().filter((entry) -> (entry.getValue() == min)).forEachOrdered((entry) -> {
            possible_next_guess.add((entry.getKey()));
        });
        //get best next guess
        boolean found = false;
        for(int[] code : possible_next_guess){
            for(int[] candidate: remainingCodes){
                if (Arrays.equals(candidate, code)) {
                    currentGuess = code;
                    found = true;
                    break;
                }
            }
        }
        //if no best guess in remaining codes then find in all possible codes
        if(!found){
            for(int[] code: possible_next_guess){
                for(int[] any: allPossibleCodes){
                    if (Arrays.equals(any, code)) {
                        currentGuess = code;
                        break;
                    }
                }
            }
        }
    }
}