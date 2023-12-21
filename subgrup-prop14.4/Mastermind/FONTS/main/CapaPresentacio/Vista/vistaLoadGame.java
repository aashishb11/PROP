package main.CapaPresentacio.Vista;

import main.CapaPresentacio.Controllers.CtrlPresentacion;

import javax.swing.*;

/**
 * It represents the Load Game view in the UI.
 * It allows the user to select and load a previous game from a saved file.
 */
public class vistaLoadGame extends JFrame{

    private CtrlPresentacion ctrl;
    private MainWindow mainWindow;

    /**
     * Constructor for the Load Game view. Sets the control and main window.
     * @param ctrl - the controller object for the view to interact with.
     * @param mainWindow - the main window of the application.
     */
    public vistaLoadGame(CtrlPresentacion ctrl, MainWindow mainWindow){
        this.ctrl = ctrl;
        this.mainWindow = mainWindow;
        loadGame();
    }

    /**
     * It Loads a game from a saved file.
     * If the game is loaded successfully, the game window is opened and the main window is disposed.
     * If the game fails to load, an error message is shown.
     */
    public void loadGame() {
        //to choose a file
        JFileChooser chooser = new JFileChooser();
        //whether we choose and open or we cancel
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = chooser.getSelectedFile().getPath();
            try {
                //load the data from the selected file
                ctrl.load(filePath);
                ctrl.isLoadedGame();
                vistaGame gameWindow = new vistaGame(ctrl, mainWindow);
                //open the game view after loading
                gameWindow.setVisible(true);
                mainWindow.dispose(); // dispose the MainWindow after loading
                dispose();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Failed to load game. Please try again. Error: " + exception.getMessage());
                exception.printStackTrace();
                dispose();
                mainWindow.setVisible(true);
            }
        }
    }
}
