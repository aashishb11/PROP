package main.CapaDades;

import main.CapaDomini.Models.GameSave;
import main.CapaDomini.Models.Ranking;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class handles the loading and saving of game state and a records file that maintains the leaderboard.
 */
public class CtrlPersistencia {

    /**
     * Saves a game state to a file at the given path.
     * @param game The GameSave object representing the state of the game to be saved.
     * @param path The path to the file where the game state will be saved.
     */
    public static void save(GameSave game, String path){
        try{
            //to write to the specified file
            FileOutputStream fileOut = new FileOutputStream(path);
            //to serialize the object we passed of gameSave
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(game);
            out.close();
            fileOut.close();
        }
        catch(IOException i){
            i.printStackTrace();
        }
    }

    /**
     * Loads a game state from a file at the given path.
     * @param filePath The path to the file where the game state is stored.
     * @return The GameSave object representing the loaded game state.
     */
    public static GameSave load(String filePath) {
        GameSave game = null;
        try {
            //to read from file
            FileInputStream fileIn = new FileInputStream(filePath);
            //deserialize the object
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //read from the file
            game = (GameSave) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("GameSave class not found");
            c.printStackTrace();
            return null;
        }
        return game;
    }

    /**
     * Saves the game's ranking to a text file.
     * @param ranking The Ranking object to be saved to file.
     */
    public void saveRecordsToFile(Ranking ranking) {
        try {
            // To write to a file
            FileWriter writer = new FileWriter("ranking.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            Set<String> newPlayers = new HashSet<>(); // Track saved player names

            for (Ranking.ScoreEntry entry : ranking.getRankings()) {
                if (!newPlayers.contains(entry.getPlayerName())) {
                    String line = entry.getPlayerName() + "," + entry.getScore() + "\n";
                    bufferedWriter.write(line);
                   newPlayers.add(entry.getPlayerName());
                }
            }
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Loads the game's ranking from a text file.
     * @return The Ranking object representing the loaded rankings.
     */
    public Ranking loadRecordsFromFile() {
        Ranking ranking = new Ranking();
        try {
            //to read from the file
            FileReader reader = new FileReader( "ranking.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            //while there are still lines left
            while ((line = bufferedReader.readLine()) != null) {
                //split each line
                String[] parts = line.split(",");
                //first one is Name
                String playerName = parts[0];
                //second one is score
                int score = Integer.parseInt(parts[1]);
                // Update score using updatePlayerScore method
                ranking.updatePlayerScore(playerName, score);
            }
            bufferedReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ranking;
    }

}
