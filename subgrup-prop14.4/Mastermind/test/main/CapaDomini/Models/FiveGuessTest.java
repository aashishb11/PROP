package main.CapaDomini.Models;

import main.CapaDomini.Models.FiveGuess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

/**
 *!!!!!!!! THE TEST CASE FOR SOLVE METHOD FAILS !!!!!!!!
 */
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FiveGuessTest {
    @Test
    public void testSolveMultipleSecretCodes() throws Exception {
        int codeLength = 4;
        int numberOfPegs = 6;
        int maxSteps = 10;

        List<List<Integer>> secretCodesToTest = Arrays.asList(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(4, 3, 2, 1),
                Arrays.asList(1, 1, 1, 1),
                Arrays.asList(6, 6, 6, 6),
                Arrays.asList(2, 3, 5, 6),
                Arrays.asList(6, 5, 3, 2),
                Arrays.asList(3, 2, 6, 6),
                Arrays.asList(2, 2, 5, 5),
                Arrays.asList(4, 5, 6, 1),
                Arrays.asList(1, 6, 5, 4)
        );

        for (List<Integer> secretCode : secretCodesToTest) {
            FiveGuess computerPlayer = new FiveGuess(codeLength, numberOfPegs, maxSteps);
            List<List<Integer>> guesses = computerPlayer.solve(secretCode);

            System.out.println("Secret code: " + secretCode);
            System.out.println("Computer player's guesses:");
            guesses.forEach(System.out::println);
            System.out.println("-------------------");

            assertTrue(secretCode.equals(guesses.get(guesses.size() - 1)), "Computer player failed to guess the secret code " + secretCode + " within " + maxSteps + " attempts.");
        }
    }

}
