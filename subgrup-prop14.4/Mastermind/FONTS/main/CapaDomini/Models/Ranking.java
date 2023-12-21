package main.CapaDomini.Models;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents a Ranking system for players in the game.
 */
public class Ranking{

    private static final int SIZE_RECORDS= 10;

    // List to store the scores of all players
    private final List<ScoreEntry> scores;

    /**
     * Constructor to initialize the list of scores
     */
    public Ranking() {
        this.scores = new ArrayList<>();
    }

    /**
     * Returns the list of scores in the ranking.
     * @return The list of score entries
     */
    public List<ScoreEntry>getRankings(){
        return scores;
    }


    /**
     * It Adds the score to the ranking system.
     * The score is added if it's either in the top 10 scores or the ranking has fewer than 10 scores.
     * If the ranking has more than 10 scores after adding the new score, the lowest score is removed.
     * @param playerName The name of the player
     * @param score The score to add
     */
    public void addScore(String playerName, int score) {
        if (scores.size() < SIZE_RECORDS || score > Collections.min(scores).getScore()) {
            ScoreEntry newEntry = new ScoreEntry(playerName, score);
            scores.add(newEntry);
            Collections.sort(scores);
            if (scores.size() > SIZE_RECORDS) {
                scores.remove(SIZE_RECORDS - 1);
            }
        }
    }

    /**
     * Returns the top scores from the ranking.
     * @param numberOfTopScores Number of top scores to return
     * @return List of top scores
     */
    public List<ScoreEntry> getTopScores(int numberOfTopScores) {
        return scores.subList(0, Math.min(numberOfTopScores, scores.size()));
    }

    /**
     * Sorts the scores in the scores list in descending order.
     */
    private void sortScores() {
        scores.sort(Comparator.comparing(ScoreEntry::getScore).reversed());
    }

    /**
     * Inner class to represent an entry in the score list.
     * Contains the player's name and their score.
     */
    public static class ScoreEntry implements Serializable, Comparable<ScoreEntry> {
        private final String playerName;
        private int score;

        /**
         * Constructor to initialize the score entry with player name and score.
         * @param playerName Name of the player
         * @param score Score of the player
         */
        public ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        /**
         * Gets the name of the player for this score entry.
         * @return Name of the player
         */
        public String getPlayerName() {
            return playerName;
        }

        /**
         * Gets the score of the player for this score entry.
         * @return Score of the player
         */
        public int getScore() {
            return score;
        }
        /**
         * Sets the score for this score entry.
         * @param score Score to set
         */
        public void setScore(int score) {
            this.score = score;
        }

        /**
         * Compares this score entry with another score entry based on the score value.
         * @param other The other score entry to compare to
         * @return The result of the comparison
         */
        @Override
        public int compareTo(ScoreEntry other) {
            return Integer.compare(other.score, this.score);
        }
    }

    /**
     * Updates the player's score in the ranking system.
     * If the player is found in the ranking, it updates their score.
     * If the player is not found, it creates a new entry for the player.
     * @param playerName Name of the player
     * @param score New score of the player
     */
    public void updatePlayerScore(String playerName, int score) {
        // Check if the player exists in the ranking
        for (ScoreEntry entry : scores) {  //If the player is found , it updates his score
            if (entry.getPlayerName().equals(playerName)) {
                if(score > entry.getScore()) {
                    entry.setScore(score);
                    sortScores();
                }
                return;
            }
        }
        addScore(playerName, score); //If not , it creates a new entry for player
    }
}

