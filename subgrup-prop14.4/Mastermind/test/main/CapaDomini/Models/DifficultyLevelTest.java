package main.CapaDomini.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyLevelTest {

    private DifficultyLevel Easy;
    private DifficultyLevel Medium;
    private DifficultyLevel Hard;

    @BeforeEach
    void setUp() {
        Easy = new DifficultyLevel("Easy", 15, true);
        Medium = new DifficultyLevel("Medium", 12, false);
        Hard = new DifficultyLevel("Hard", 10, false);
    }

    @Test
    void getName() {
        assertEquals("Easy", Easy.getName());
        assertEquals("Medium", Medium.getName());
        assertEquals("Hard", Hard.getName());
    }

    @Test
    void getNumGuesses() {
        assertEquals(15, Easy.getNumGuesses());
        assertEquals(12, Medium.getNumGuesses());
        assertEquals(10, Hard.getNumGuesses());
    }


}