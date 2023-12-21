package main.CapaPresentacio.Vista;

import main.CapaPresentacio.Controllers.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the Assistance view in the UI.
 * It displays a hint for the secret code.
 */
public class vistaAssistance extends JFrame {
    private CtrlPresentacion ctrl;

    /**
     * Constructor for the Assistance view.
     * @param ctrl - the controller object for the view to interact with.
     */
    public vistaAssistance(CtrlPresentacion ctrl){
        this.ctrl = ctrl;
        initWindow();
        initButtons();
    }

    /**
     * Initializes window settings:  layout, size, location and visibility.
     */
    private void initWindow(){
        this.setLayout(new GridLayout(1, ctrl.getSecretCodePlayer2().length, 5, 5));
        Dimension resolution = ctrl.getCurrentResolution();
        this.setTitle("Here is a clue for the secret code!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(resolution.width*20/100, resolution.height*15/100);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Initializes the buttons on the window.
     * Displays the secret code as a set of buttons with colors corresponding to the code.
     * And we highlight one random button with the correct color.
     */
    private void initButtons(){
        int[] secretCode = ctrl.getSecretCodePlayer2();
        int codeLength = secretCode.length;

        //array of buttons equal to codeLength
        JButton[] codeButtons = new JButton[codeLength];

        //get hint
        String hint = ctrl.getNextCorrectPegAndPosition(true);

        //index of hint from the string returned by ctrl
        int hintIndex = Integer.parseInt(hint.split(" ")[1]) - 1;

        for (int i = 0; i < codeLength; i++) {
            //initialization of buttons
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(5, 5)); // adjust size of buttons
            button.setEnabled(false);

            //set colors
            if(i == hintIndex){
                button.setBackground(integerToColor(secretCode[i]));
            } else {
                button.setBackground(Color.GRAY);
            }
            codeButtons[i] = button;
            this.add(button);
        }
    }

    /**
     * Converts an integer to a corresponding color.
     * @param guess - the integer to be converted.
     * @return the corresponding Color object.
     */
    public static Color integerToColor(int guess) {
        switch (guess){
            case 1:
                return Color.RED;
            case 2:
                return Color.ORANGE;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.BLUE;
            case 6:
                return Color.CYAN;
            case 7:
                return Color.PINK;
            case 8:
                return Color.MAGENTA;
            case 9:
                return Color.BLACK;
            case 10:
                return Color.GRAY;
            case 11:
                return Color.LIGHT_GRAY;
            case 12:
                return Color.DARK_GRAY;
            default:
                return null;
        }
    }
}
