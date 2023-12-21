package main.CapaDomini.Models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ALL TEST CASES PASSED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

import static org.junit.jupiter.api.Assertions.*;

class HumanTest {

    private Human human;

    @BeforeEach
    void setUp() {
        human = new Human(4,6,"Aashish");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetName() {
        assertEquals("Aashish", human.getName());
    }

    @Test
    void testSetName() {
        human.setName("Mario");
        assertEquals("Mario", human.getName());
    }

    @Test
    void testGetTurnsTaken() {
        assertEquals(0,human.getTurnsTaken());
    }

    @Test
    void testIncrementTurnsTaken() {
        human.incrementTurnsTaken();
        assertEquals(1,human.getTurnsTaken());
    }

    @Test
    void testGenerateRandomCode() {
        int[] randomCode = human.generateRandomCode();
        for(int i : randomCode){
            assertTrue(i >=1 && i <= 6);
        }
        assertEquals(4,randomCode.length);
    }

    /**
     * No clue how to implement test for these two because it requires us to give input
    @Test
    void testGenerateSecretCode() {
    }

    @Test
    void testMakeGuess() {
    }
    */

}