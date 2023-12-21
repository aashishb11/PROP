package main.CapaDomini.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssistanceTest {

        private MastermindBoard board;
        private Assistance assistance;
    @BeforeEach
    void setUp() {
        board = new MastermindBoard(4,6);

        board.setSecretCodes(new int[]{5, 6, 4, 3}, new int[]{5, 6, 4, 3});
        board.getGuessesPlayer1().add(new int[]{5, 6, 4, 3});
        board.getGuessesPlayer1().add(new int[]{3, 4, 6, 5});
        board.getGuessesPlayer2().add(new int[]{5, 6, 4, 3});
        board.getGuessesPlayer2().add(new int[]{3, 4, 6, 5});
        board.getFeedbackPlayer1().add(new int[]{0, 4});
        board.getFeedbackPlayer1().add(new int[]{0, 4});
        board.getFeedbackPlayer2().add(new int[]{0, 4});
        board.getFeedbackPlayer2().add(new int[]{0, 4});

        assistance = new Assistance(board);
    }

    @Test
    void getRightPegsInRightPosition() {
        String result = assistance.getRightPegsInRightPosition(true);
        assertEquals("There are 0 peg(s) in the right position.", result);
    }

    @Test
    void getRightPegsInWrongPosition() {
        String result = assistance.getRightPegsInWrongPosition(true);
        assertEquals("There are 4 peg(s) that are correct but in the wrong position.", result);
    }
}
