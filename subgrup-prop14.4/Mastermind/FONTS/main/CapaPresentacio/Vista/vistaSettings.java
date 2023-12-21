package main.CapaPresentacio.Vista;

import main.CapaPresentacio.Controllers.CtrlPresentacion;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * This is the settings class used to set the game parameters.
 */
public class vistaSettings extends JFrame implements ActionListener {

    private CtrlPresentacion ctrl;

    private ActionListener SettingsUpdated;
    private JTextField nameHuman;

    private JTextField AIname;

    private JFormattedTextField codeLength;

    private JFormattedTextField noOfPegs;

    private JFormattedTextField totalRounds;

    private JComboBox<String> Algorithm;

    private JComboBox<String> Difficulty;

    private JButton Done;

    private GridBagConstraints c = new GridBagConstraints();

    String selectedCodeLength;
    String selectedNoOfPegs ;


    /**
     * Constructor of the vistaSettings initiates the settings view by initializing all UI components and their action listeners.
     * @param ctrl - the controller object for the settings view to interact with.
     * @param SettingsUpdated - action listener to handle actions when settings are updated.
     */
    public vistaSettings(CtrlPresentacion ctrl, ActionListener SettingsUpdated){
        this.ctrl = ctrl;
        this.SettingsUpdated=SettingsUpdated;
        setTitle("Settings");
        Dimension resolution = ctrl.getCurrentResolution();
        setSize(resolution.width*15/100, resolution.height*28/100);
        setLayout(new GridBagLayout());

        initNameTextField();
        initAINameTextField();
        initCodeLengthField();
        initNoOfPegsField();
        initAlgorithmComboBox();
        initDifficultyComboBox();
        initTotalRoundsField();
        initButton();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * initializes the text field for the human player name.
     */
    private void initNameTextField() {
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 70;
        c.insets = new Insets(4, 4,4 ,10);
        JPanel kText = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Player Name:");
        kText.add(label, BorderLayout.WEST);
        nameHuman = new JTextField(6);
        kText.add(nameHuman, BorderLayout.CENTER);
        this.add(kText, c);
    }

    /**
     * initializes the text field for the AI player name.
     */
    private void initAINameTextField(){
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 70;
        c.insets = new Insets(4, 4,4 ,10);
        JPanel kText = new JPanel(new BorderLayout());
        JLabel label = new JLabel("AIPlayer:                  ");
        kText.add(label, BorderLayout.WEST);
        AIname = new JTextField(6);
        AIname.setEditable(false);
        kText.add(AIname, BorderLayout.CENTER);
        this.add(kText, c);
    }

    /**
     *initializes the text field for the code length.
     */
    private void initCodeLengthField(){
        c.gridx = 0;
        c.gridy = 3;
        c.ipadx = 70;
        c.insets = new Insets(4, 4,4 ,10);
        JPanel kText = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Code Length:          ");
        kText.add(label, BorderLayout.WEST);
        codeLength = new JFormattedTextField(NumberFormat.getNumberInstance());
        codeLength.setColumns(6);
        kText.add(codeLength, BorderLayout.CENTER);
        this.add(kText, c);
    }

    /**
     *initializes the text field for the number of pegs.
     */
    private void initNoOfPegsField(){
        c.gridx = 0;
        c.gridy = 4;
        c.ipadx = 70;
        c.insets = new Insets(4, 4,4 ,10);
        JPanel kText = new JPanel(new BorderLayout());
        JLabel label = new JLabel("No. of Colors:           ");
        kText.add(label, BorderLayout.WEST);
        noOfPegs = new JFormattedTextField(NumberFormat.getNumberInstance());
        noOfPegs.setColumns(6);
        kText.add(noOfPegs, BorderLayout.CENTER);
        this.add(kText, c);
    }

    /**
     *initializes the text field for the total rounds of play.
     */
    private void initTotalRoundsField(){
        c.gridx = 0;
        c.gridy = 6;
        c.ipadx = 70;
        c.insets = new Insets(4, 4,4 ,10);
        JPanel kText = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Total Rounds:           ");
        kText.add(label, BorderLayout.WEST);
        totalRounds = new JFormattedTextField(NumberFormat.getNumberInstance());
        totalRounds.setColumns(6);
        kText.add(totalRounds, BorderLayout.CENTER);
        this.add(kText, c);
    }

    /**
     *initializes the combo box for selecting the AI's algorithm.
     */
    private void initAlgorithmComboBox() {
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 0;
        c.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        JPanel algorithmPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Algorithm:");
        algorithmPanel.add(label, BorderLayout.WEST);

        this.Algorithm = new JComboBox<>(new String[]{"","Genetic algorithm", "Five Guess algorithm"});
        this.Algorithm.setPreferredSize(new Dimension(200,20));
        this.Algorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Algorithm.getSelectedItem().equals("")) {
                    return;
                }
                String selectedAlgorithm = (String)Algorithm.getSelectedItem();
                ctrl.selectAlgorithm(selectedAlgorithm);
                AIname.setText(selectedAlgorithm);
                if (Algorithm.getItemAt(0).equals("")) {
                    Algorithm.removeItemAt(0);
                }

                //To disable/Enable fields based on the algorithm
                if (selectedAlgorithm.equals("Five Guess algorithm")) {
                    codeLength.setText("4");
                    noOfPegs.setText("6");
                    codeLength.setEnabled(false);
                    noOfPegs.setEnabled(false);
                } else if (selectedAlgorithm.equals("Genetic algorithm")) {
                    codeLength.setText("4");
                    noOfPegs.setText("6");
                    codeLength.setEnabled(true);
                    noOfPegs.setEnabled(true);
                }
            }
        });

        algorithmPanel.add(Algorithm, BorderLayout.CENTER);
        this.add(algorithmPanel, c);
    }

    /**
     *initializes the combo box for selecting the game difficulty level.
     */
    private void initDifficultyComboBox() {
        c.gridx = 0;
        c.gridy = 5;
        c.ipadx = 0;
        c.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        JPanel difficultyPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Difficulty:");
        difficultyPanel.add(label, BorderLayout.WEST);

        this.Difficulty = new JComboBox<>(new String[]{"","Easy", "Medium", "Hard"});
        this.Difficulty.setPreferredSize(new Dimension(200,20));
        this.Difficulty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Difficulty.getSelectedItem().equals("")) {
                    return;
                }
                String selectedDifficulty = (String)Difficulty.getSelectedItem();
                ctrl.selectDifficultyLevel(selectedDifficulty);
                if (Difficulty.getItemAt(0).equals("")) {
                    Difficulty.removeItemAt(0);
                }
            }
        });

        difficultyPanel.add(Difficulty, BorderLayout.CENTER);
        this.add(difficultyPanel, c);
    }

    /**
     *initializes the done button. It verifies and validates the inputs and passes it to the controller.
     */
    private void initButton() {
        c.gridx = 0;
        c.gridy = 7;
        c.insets = new Insets(8, 0, 8, 0);
        Done = new JButton("Done");
        Done.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameHuman.getText();
                String selectedAlgorithm = (String)Algorithm.getSelectedItem();
                String selectedDifficulty = (String)Difficulty.getSelectedItem();
                String totalRoundsGame = totalRounds.getText();

                if (playerName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid player name.");
                    return;
                }

                if (selectedAlgorithm == null || selectedAlgorithm.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please select an algorithm.");
                    throw new IllegalArgumentException("No algorithm selected");
                }

                if (selectedDifficulty == null || selectedDifficulty.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please select a difficulty level.");
                    throw new IllegalArgumentException("No difficulty level selected");
                }
                if (totalRoundsGame.trim().isEmpty() || Integer.parseInt(totalRoundsGame) < 1 || Integer.parseInt(totalRoundsGame) > 10) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid total number of rounds.");
                    return;
                }
                if (selectedAlgorithm.equals("Genetic algorithm")) {
                    selectedCodeLength = codeLength.getText();
                    selectedNoOfPegs = noOfPegs.getText();

                    if (!((selectedCodeLength.equals("4") && selectedNoOfPegs.equals("6")) ||
                            (selectedCodeLength.equals("6") && selectedNoOfPegs.equals("8")) ||
                            (selectedCodeLength.equals("8") && selectedNoOfPegs.equals("10")))) {
                        JOptionPane.showMessageDialog(null, "Please select valid CodeLength and No. of Colors values for the selected algorithm.");
                        return;
                    }
                }
                else if (selectedAlgorithm.equals("Five Guess algorithm") && (!codeLength.getText().equals("4") || !noOfPegs.getText().equals("6"))) {
                    JOptionPane.showMessageDialog(null, "For the Five Guess algorithm, CodeLength must be 4 and No. of Pegs must be 6.");
                    return;
                }
                ctrl.setTotalRounds(Integer.parseInt(totalRoundsGame));
                ctrl.setPlayer1Name(playerName);
                ctrl.setCodeLength(Integer.parseInt(codeLength.getText()));
                ctrl.setNoOfPegs(Integer.parseInt(noOfPegs.getText()));
                ctrl.setPlayer2Name(selectedAlgorithm);
                ctrl.selectAlgorithm(selectedAlgorithm);
                ctrl.selectDifficultyLevel(selectedDifficulty);
                dispose();

                if (SettingsUpdated != null) {
                    SettingsUpdated.actionPerformed(new ActionEvent(vistaSettings.this, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });
        this.add(Done, c);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
