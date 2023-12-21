package main.CapaDomini.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RankingTest {

    private Ranking ranking;
    private Player player1;
    private Player player2;
    @BeforeEach
    void setUp() {
        ranking = new Ranking();
        player1=new Player(4,6,"Aashish") {
            @Override
            public int[] generateSecretCode() {
                return new int[0];
            }

            @Override
            public int[] makeGuess() {
                return new int[0];
            }
        };

        player2= new Player(4,6,"Paolo") {
            @Override
            public int[] generateSecretCode() {
                return new int[0];
            }

            @Override
            public int[] makeGuess() {
                return new int[0];
            }
        };

        player1.incrementTurnsTaken();
        player1.incrementTurnsTaken();
        player1.incrementTurnsTaken();
        player1.incrementTurnsTaken();
        player1.incrementTurnsTaken();

        player2.incrementTurnsTaken();
        player2.incrementTurnsTaken();
        player2.incrementTurnsTaken();
        player2.incrementTurnsTaken();
        player2.incrementTurnsTaken();
        player2.incrementTurnsTaken();
        player2.incrementTurnsTaken();
        player2.incrementTurnsTaken();
    }


    @Test
    void getTopScores() {
        ranking.updatePlayerScore("Aashish", 8); //before was 5, now 8
        ranking.updatePlayerScore("Paolo", 5); //before was 8, now 5
        ranking.updatePlayerScore("Mario", 4); //to check topScores.size()
        List<Ranking.ScoreEntry> topScores = ranking.getTopScores(2);
        assertEquals(2, topScores.size());

        assertEquals("Aashish", topScores.get(0).getPlayerName());
        assertEquals("Paolo", topScores.get(1).getPlayerName());
    }

}