package main.CapaDomini.Models;

import java.util.*;
import java.util.stream.Collectors;

/**
 Implementation of Genetic algorithm to solve the Mastermind game
 */

public class GeneticComputerPlayer extends Player implements Maquina {

    //Maximum number of attempts
    private final int maxSteps;
    //size of population of possible solutions
    private final int populationSize;
    //probability of crossover between two selected individuals
    private final double crossoverRate;
    //probability of mutation
    private final double mutationRate;
    private final List<int[]> guessHistory = new ArrayList<>();

    /**
     * It Constructs a GeneticComputerPlayer object with the given parameters.
     */
    public GeneticComputerPlayer(int codeLength, int numberOfPegs, int maxSteps, int populationSize, double crossoverRate, double mutationRate) {
        super(codeLength, numberOfPegs, "Genetic AI");
        this.maxSteps = maxSteps;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }
    /**
     * To generate a random secret code
     * @return the generated secret code
     */
    @Override
    public int[] generateSecretCode() {
        return generateRandomCode();
    }
    /**
     * generates a random guess
     * @return the random generated guess
     */
    @Override
    public int[] makeGuess() {
        // Implement genetic algorithm-based guessing logic here
        return generateRandomCode(); // For now, return a random code
    }
    /**
     * Solves the Mastermind game using the genetic algorithm.
     * @param solution The secret code to be guessed.
     * @return A list of guesses made by the algorithm in order.
     */
    @Override
    public List<List<Integer>> solve(List<Integer> solution) {
        System.out.println("I enter here with solution; " + solution);
        List<List<Integer>> codeList = new ArrayList<>();
        int[] secretCode = solution.stream().mapToInt(Integer::intValue).toArray();
        MastermindBoard board = new MastermindBoard(codeLength, numberOfPegs);
        board.setSecretCodes(secretCode, secretCode);
        //generate initial population
        List<int[]> population = generateInitialPopulation();
        for (int step = 0; step < maxSteps; step++) {
            //best guess from the current population
            int[] guess = getBestGuess(population, secretCode);
            System.out.println("The guess made in step: " + step + "is: " + Arrays.toString(guess));
            //add the guess to the list of guesses
            codeList.add(Arrays.stream(guess).boxed().collect(Collectors.toList()));
            //check if the guess is correct
            if (board.isCorrectGuess(guess, false)) {
                break;
            }
            //PerformSelection, performCrossover, PerformMutation to generate next generation
            population = performSelection(population, secretCode);
            population = performCrossover(population);
            population = performMutation(population);
        }
        return codeList;
    }
    /**
     * The first step, generate the initial population
     * List of random codes, each of size equal to populationSize
     */
    private List<int[]> generateInitialPopulation() {
        List<int[]> population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            population.add(generateRandomCode());
        }
        return population;
    }
    /**
     * Second step, Calculate the fitness of a guess comparing it to secretCode
     * Lower the fitness better the guess, later selection is performed based on lower fitness
     * @param guess The guess to calculate the fitness for.
     * @param secretCode The secret code to compare against.
     * @return The fitness value of the guess.
     */
    private int getFitness(int[] guess, int[] secretCode) {
        MastermindBoard board = new MastermindBoard(codeLength, numberOfPegs);
        int[] feedback = board.getFeedbackForGuess(guess, secretCode);
        // Total codeLength * 2 subtracting blackPegs (pegs in correct position) subtracting whitePegs(Correct color wrong position)
        // *2 to blackPegs to give more priority as they are important than whitePegs
        return (codeLength * 2) - (feedback[0] * 2) - feedback[1];
    }
    /**
     * BestGuess from the population based on the lowest fitness value
     * @param population The current population.
     * @param secretCode The secret code to be guessed.
     * @return The best guess from the population.
     */
    private int[] getBestGuess(List<int[]> population, int[] secretCode) {
        Optional<int[]> bestGuess = population.stream()
                .filter(guess -> !guessHistory.contains(guess))
                .min(Comparator.comparingInt(code -> getFitness(code, secretCode)));

        if (bestGuess.isPresent()) {
            guessHistory.add(bestGuess.get());
            return bestGuess.get();
        } else {
            throw new IllegalStateException("No new best guess found");
        }
    }
    /**
     * To choose top 50% of the population based on fitness, sorted ascending ly to select candidates with lower fitness
     * @param population The current population.
     * @param secretCode The secret code to be guessed.
     * @return The selected population.
     */
    private List<int[]> performSelection(List<int[]> population, int[] secretCode) {
        // Select top 50% of the population based on fitness
        return population.stream()
                .sorted(Comparator.comparingInt(code -> getFitness(code, secretCode)))
                .limit(populationSize / 2)
                .collect(Collectors.toList());
    }
    /**
     * CrossOver to generate offspring from selected individuals in the population
     * random selection of parent solutions and crossover with crossoverRate
     * Cross over point chosen randomly
     * @param population The current population.
     * @return The new population after crossover.
     */
    private List<int[]> performCrossover(List<int[]> population) {
        List<int[]> newPopulation = new ArrayList<>(population);
        Random random = new Random();
        //only perform crossover when the new population size is less than the initial population size
        while (newPopulation.size() < populationSize) {
            int parent1Index = random.nextInt(population.size());
            int parent2Index = random.nextInt(population.size());
            //if the random double is less than the crossover rate
            if (random.nextDouble() < crossoverRate) {
                int crossoverPoint = random.nextInt(codeLength);
                int[] child1 = new int[codeLength];
                int[] child2 = new int[codeLength];
                //cut the genes of parents in random place and copy to children
                System.arraycopy(population.get(parent1Index), 0, child1, 0, crossoverPoint);
                System.arraycopy(population.get(parent2Index), crossoverPoint, child1, crossoverPoint, codeLength - crossoverPoint);
                System.arraycopy(population.get(parent2Index), 0, child2, 0, crossoverPoint);
                System.arraycopy(population.get(parent1Index), crossoverPoint, child2, crossoverPoint, codeLength - crossoverPoint);
                //Add new children(next generation) to the new population
                newPopulation.add(child1);
                newPopulation.add(child2);
            } else {
                //if condition does not meet, add parents to the new population
                newPopulation.add(population.get(parent1Index));
                newPopulation.add(population.get(parent2Index));
            }
        }
        return newPopulation;
    }
    /**
     * Mutation of the population
     * @param population The current population.
     * @return The new population after mutation.
     */
    private List<int[]> performMutation(List<int[]> population) {
        Random random = new Random();
        return population.stream()
                .map(code -> {
                    // if(random double is less than mutation rate)
                    //if a value between 0 and 1 is less than the mutationRate given
                    //The below if condition necessary to control the probability of mutation
                    // same goes for crossover... A higher these rates means more diverse solutions
                    if (random.nextDouble() < mutationRate) {
                        int mutationIndex = random.nextInt(codeLength);
                        int[] mutatedCode = Arrays.copyOf(code, codeLength);
                        //mutate a random gene
                        mutatedCode[mutationIndex] = random.nextInt(numberOfPegs) + 1;
                        return mutatedCode;
                    } else {
                        return code;
                    }
                })
                .collect(Collectors.toList());
    }
}

