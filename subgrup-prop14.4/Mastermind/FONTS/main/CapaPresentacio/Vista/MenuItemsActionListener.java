package main.CapaPresentacio.Vista;

import main.CapaPresentacio.Controllers.CtrlPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class handles the actions for the menu items in the application.
 */
public class MenuItemsActionListener implements ActionListener {

    private final CtrlPresentacion ctrl;
    private final vistaGame vG;

    private ActionListener aL;

    /**
     * Constructor for the MenuItemsActionListener.
     * @param ctrl - The controller object for handling the business logic.
     * @param vistaGameInstance - The instance of the game view.
     */
    public MenuItemsActionListener(CtrlPresentacion ctrl, vistaGame vistaGameInstance) {
        this.ctrl = ctrl;
        this.vG = vistaGameInstance;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command.equals("new")){
            vG.newGame();
        }
        else if(command.equals("open")){
            JFileChooser chooser = new JFileChooser();
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String filePath = chooser.getSelectedFile().getPath();
                try {
                    ctrl.load(filePath);
                    JOptionPane.showMessageDialog(null, "Game Loaded Successfully.");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Failed to load game. Please try again.");
                }
            }
        }

        else if(command.equals("save")){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    ctrl.save(filePath);
                    JOptionPane.showMessageDialog(null, "Game Saved Successfully.");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Failed to save game. Please try again.");
                }
            }
        }
        else if(command.equals("quit")){
            vG.dispose();
            MainWindow mainWindow = new MainWindow(ctrl);
            mainWindow.setVisible(true);
        }
    }

}
