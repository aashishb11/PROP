package main.CapaDomini.Models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeneticComputerPlayerTest {

    @Test
    void testGenerateSecretCode() {
        GeneticComputerPlayer player = new GeneticComputerPlayer(4, 6, 10, 100, 0.5, 0.01);
        int[] secretCode = player.generateSecretCode();
        assertEquals(4, secretCode.length);
        for (int i : secretCode) {
            assertTrue(i >= 1 && i <= 6);
        }
    }

    @Test
    void testMakeGuess() {
        GeneticComputerPlayer player = new GeneticComputerPlayer(4, 6, 10, 100, 0.5, 0.01);
        int[] guess = player.makeGuess();
        assertEquals(4, guess.length);
        for (int i : guess) {
            assertTrue(i >= 1 && i <= 6);
        }
    }

    @Test
    void testSolve() {
        GeneticComputerPlayer player = new GeneticComputerPlayer(4, 6, 100, 100, 0.5, 0.05);
        List<Integer> secretCode = Arrays.asList(1, 2, 3, 4);
        List<List<Integer>> result = player.solve(secretCode);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(result.size() - 1).equals(secretCode));
    }

    @Test
    void testRepeatedGuesses() {
        GeneticComputerPlayer player = new GeneticComputerPlayer(4, 6, 10, 100, 0.5, 0.01);
        List<Integer> secretCode = Arrays.asList(1, 2, 3, 4);
        List<List<Integer>> result = player.solve(secretCode);
        for (int i = 0; i < result.size() - 1; i++) {
            assertFalse(result.get(i).equals(result.get(i + 1)));
        }
    }

}
