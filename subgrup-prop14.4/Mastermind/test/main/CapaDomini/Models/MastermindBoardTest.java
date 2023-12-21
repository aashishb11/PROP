package main.CapaDomini.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MastermindBoardTest {

    private MastermindBoard board;

    @BeforeEach
    void setUp() {
        board = new MastermindBoard(4, 6);
        board.setSecretCodes(new int[]{1,3,2,5}, new int[]{4,3,2,1});
    }

    @Test
    void GetCodeLength() {
        assertEquals(4, board.getCodeLength());
    }

    @Test
    void GetNumberOfPegs() {
        assertEquals(6, board.getNumberOfPegs());
    }

    @Test
    void GetSecretCodePlayer1() {
        assertArrayEquals(new int[]{1,3,2,5}, board.getSecretCodePlayer1());
    }

    @Test
    void GetSecretCodePlayer2() {
        assertArrayEquals(new int[]{4, 3, 2, 1}, board.getSecretCodePlayer2());
    }

    @Test
    void IsCorrectGuess() {
        assertTrue(board.isCorrectGuess(new int[]{1,3,2,5}, true));
        assertTrue(board.isCorrectGuess(new int[]{4,3,2,1}, false));
        assertFalse(board.isCorrectGuess(new int[]{4,3,2,1}, true));
        assertFalse(board.isCorrectGuess(new int[]{1,3,2,5}, false));
    }

    @Test
    void GetFeedbackForGuess() {
        assertArrayEquals(new int[]{4, 0}, board.getFeedbackForGuess(new int[]{1,3,2,5}, new int[]{1,3,2,5}));
        assertArrayEquals(new int[]{2, 2}, board.getFeedbackForGuess(new int[]{1,2,3,5}, new int[]{1,3,2,5}));
        assertArrayEquals(new int[]{0, 0}, board.getFeedbackForGuess(new int[]{6,6,6,6}, new int[]{1,3,2,5}));
    }

    @Test
    void GetFeedback() {
        assertArrayEquals(new int[]{2,2}, board.getFeedback(new int[]{1,2,3,5}, true));
        assertArrayEquals(new int[]{2,2}, board.getFeedback(new int[]{4,2,3,1}, false));

        List<int[]> guessesPlayer1 = board.getGuessesPlayer1();
        assertArrayEquals(new int[]{1,2,3,5}, guessesPlayer1.get(0));

        List<int[]> guessesPlayer2 = board.getGuessesPlayer2();
        assertArrayEquals(new int[]{4,2,3,1}, guessesPlayer2.get(0));

        List<int[]> feedbackPlayer1 = board.getFeedbackPlayer1();
        assertArrayEquals(new int[]{2,2}, feedbackPlayer1.get(0));

        List<int[]> feedbackPlayer2 = board.getFeedbackPlayer2();
        assertArrayEquals(new int[]{2,2}, feedbackPlayer2.get(0));
    }

}

