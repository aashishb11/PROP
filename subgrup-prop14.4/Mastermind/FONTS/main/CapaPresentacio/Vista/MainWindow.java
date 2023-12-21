package main.CapaPresentacio.Vista;

import main.CapaPresentacio.Controllers.CtrlPresentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Dimension;

/**
 * This is the vista principal of the application. Whenever we run the application
 * we will see the view designed here.
 * It represents a mainWindow with several actions to perform such as:
 * starting a game, loading a game, view records, manage settings, learn about the application,
 * know the rules of the game.
 */
public class MainWindow extends JFrame implements  ActionListener {
    private final CtrlPresentacion ctrl;

    private ActionListener as;


     //Buttons and their initializations
    private final JButton buttonNewGame = new JButton("New Game");

    private final JButton buttonLoadGame = new JButton("Load Game");

    private final JButton buttonSettings = new JButton("Settings");

    private final JButton buttonRecords = new JButton("Records");

    private final JButton buttonHelp = new JButton("Help");

    private final JButton buttonExit = new JButton("Exit");

    private final JButton buttonAbout = new JButton("About");

    //panel and the table initialization
    private final JPanel panelButtons = new JPanel();

    private JTable settingsTable;

    private DefaultTableModel tableModel;

    private Dimension resolution;

    //for the layout of various components in the frame
    //it's easy to arrange with it so
    private final GridBagConstraints c = new GridBagConstraints();


    /**
     * Constructor for the MainWindow class.
     * @param ctrl The controller for the presentation layer to communicate to the domain layer
     */
    public MainWindow(CtrlPresentacion ctrl) {
        super("MasterMind");
        this.ctrl=ctrl;
        initializeComponents();
        hacerVisible();
    }

    /**
     * Activate the mainWindow, make it visible.
     */
    public void activate() {
        this.setEnabled(true);
    }

    /**
     * Adjust dimensions, make it visible
     */
    public void hacerVisible() {
        this.pack();
        this.setVisible(true);
        activate();
    }

    /**
     * Initializes all the components we have in the main window
     */
    private void initializeComponents() {
        initPanelButtons();
        initSettingsTable();
        initMainFrame();
    }

    /**
     * To initialize or update the table where we can view the current settings of the game.
     */

    public void initSettingsTable() {
        String[] columnNames = {"Player1 Name", "Algorithm", "Difficulty", "Hints?", "Code Length", "no. of Colors","Total Rounds"};
        tableModel = new DefaultTableModel(columnNames, 0);
        settingsTable = new JTable(tableModel);
        settingsTable.setEnabled(false);
        if(ctrl.getAlgorithm()==null){ //Still with default settings
            tableModel.addRow(new Object[]{null, null, null, null, null, null, null});
        }
        else{ // User specified the settings
            String difficulty = ctrl.getDifficulty();
            String hints = (difficulty.equalsIgnoreCase("Medium") || difficulty.equals("Easy")) ? "Yes" : "No";
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{ctrl.getPlayer1Name(), ctrl.getAlgorithm(), difficulty, hints, ctrl.getCodeLength(), ctrl.getNoOfPegs(), ctrl.getTotalRounds()});
        }

    }


    /**
     * Initializes the welcome label
     */
    private void welcomeLabel(){
        JLabel labelWelcome = new JLabel("MasterMind", SwingConstants.CENTER); //New Label
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0; //shift left right
        c.weighty = 0.1; //shift up down
        c.fill = GridBagConstraints.HORIZONTAL;
        labelWelcome.setFont(new Font("Century Schoolbook", Font.BOLD, 80));
        labelWelcome.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.getContentPane().add(labelWelcome, c);
    }

    /**
     * Initializes the panel buttons
     */
    private void panelButtons(){
        c.gridy = 2;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.BOTH;
        this.getContentPane().add(panelButtons, c);
    }


    /**
     * Sets the dimension of the mainWindow,allows resizable,sets layout
     * calls various init functions to initialize the main window such as
     * panelButtons(), table(), welcomeLabel(),instruction().
     */
    private void initMainFrame(){
        this.resolution = ctrl.getCurrentResolution();
        this.setLayout(new GridBagLayout());
        this.setMinimumSize(new Dimension(resolution.width*25/100,resolution.height*40/100));
        this.setPreferredSize(this.getMinimumSize());
        this.setResizable(true);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        welcomeLabel();
        panelButtons();
    }

    /**
     * To align the buttons in the center of the mainWindow and rearranging them a bit down the y-axis
     */
    private void alignButtons(){
        buttonNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonLoadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonRecords.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonAbout.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonHelp.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonExit.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Initialization of All the buttons available in the mainWindow
     */
    private void initPanelButtons(){

        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
        alignButtons();
        addButtonWithListener(buttonNewGame);
        addButtonWithListener(buttonLoadGame);
        addButtonWithListener(buttonSettings);
        addButtonWithListener(buttonRecords);
        addButtonWithListener(buttonAbout);
        addButtonWithListener(buttonHelp);
        addButtonWithListener(buttonExit);

    }

    /**
     * handles the events performed from the buttons of the mainWindow
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("New Game")){
            new vistaNewGame(ctrl,this).setVisible(true);
        }
        else if(e.getActionCommand().equals("Load Game")){
            new vistaLoadGame(ctrl,this).setVisible(true);
        }
        else if(e.getActionCommand().equals("Settings")){
            vistaSettings settingsWindow = new vistaSettings(ctrl,as);
            settingsWindow.setVisible(true);
            settingsWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    initSettingsTable();
                }
            });
        }
        else if(e.getActionCommand().equals("Records")){
            vistaRecords vista = new vistaRecords(ctrl, this);
            vista.setSize(resolution.width*15/100, resolution.height*25/100);
            vista.setLocationRelativeTo(null);
            vista.setVisible(true);
        }
        else if(e.getActionCommand().equals("Help")){
            String rules = ctrl.getInfo();
            showRules(rules);
        }
        else if(e.getActionCommand().equals("Exit")){
            int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to exit the game?",
                    "Confirmation",JOptionPane.YES_NO_OPTION);
            if(response == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }
        else if(e.getActionCommand().equals("About")){
            String aboutText = "<html><body>" +
                    "This application, Mastermind, is a collaborative creation by:<br>" +
                    "<ul>" +
                    "<li><b>Aashish Bhusal</b> - <i>aashish.bhusal@estudiantat.upc.edu</i></li>" +
                    "<li><b>Paolo Milner</b> - <i>paolo.milner.segura@estudiantat.upc.edu</i></li>" +
                    "<li><b>Mario Wang</b> - <i>mario.wang@estudiantat.upc.edu</i></li>" +
                    "</ul>" +
                    "We are part of subgroup 14.4 in PROP (Programming Projects). The game of Mastermind was implemented using several classes such as Player (an abstract class), HumanPlayer, GeneticPlayer, FiveGuessPlayer, Assistance, Difficulty Level, Mastermind Board, Ranking, and Records.<br>" +
                    "The architecture of this game is divided into several layers: capa de domini, capa de presentation, and capa de persistence. Communication between each layer is facilitated through a controller class.<br>" +
                    "In the process of developing this game, we have made a concerted effort to minimize code coupling. For any issues related to the game, feel free to contact us at the email addresses provided.<br>" +
                    "</body></html>";

            JOptionPane aboutPane = new JOptionPane(aboutText, JOptionPane.PLAIN_MESSAGE);
            JDialog dialog = aboutPane.createDialog(this, "About");
            dialog.setSize(new Dimension(resolution.width*25/100, resolution.height*35/100));
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }

    }

    public JTable getSettingsTable(){return settingsTable;}


    public void showRules(String rules){
        JTextArea ta = new JTextArea(rules);
        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta);
        sp.setSize(resolution.width*25/100, resolution.height*50/100);
        JOptionPane.showMessageDialog(this,sp,"Rules",JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Add a button to the button panel and sets up an ActionListener for the button
     * @param button the button to be added to the panel
     */
    private void addButtonWithListener(JButton button) {
        button.addActionListener(this);
        panelButtons.add(button);
        panelButtons.add(Box.createRigidArea(new Dimension(0, 10)));
    }

}
