package main.CapaPresentacio.Vista;

import main.CapaPresentacio.Controllers.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class vistaSecretCode extends JDialog implements ActionListener {
    private CtrlPresentacion ctrl;

    private boolean[] painted;


    private Color selectedColor;

    private JPanel guessGridPanel;

    private JPanel colorButtonWrapperPanel;

    private JPanel submitButtonPanel;

    private boolean isCodeMade;

    private int[] SecretCode;

    private int numColors;

    private int CodeLength;


    public vistaSecretCode(CtrlPresentacion cp){
        this.ctrl=cp;
        this.numColors = ctrl.getNoOfPegs();
        this.CodeLength = ctrl.getCodeLength();
        this.isCodeMade = false;
        this.painted = new boolean[CodeLength];
        this.SecretCode = new int[CodeLength];
        initWindow();
        initComponents();
    }

    private void initWindow(){
        this.setTitle("Pick a secret code for the AI to guess");
        this.setLayout(new GridLayout(1, ctrl.getCodeLength(), 5, 5));
        Dimension resolution = ctrl.getCurrentResolution();
        this.setSize(resolution.width*16/100, resolution.height*20/100);
        this.setLocationRelativeTo(null);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
    }


    private void initColorsButtons(){
        // Assuming maximum 12 colors available for 12 pegs
        Color[] allColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.PINK, Color.MAGENTA, Color.BLACK, Color.GRAY, Color.LIGHT_GRAY, Color.DARK_GRAY};
        Color[] colors = Arrays.copyOfRange(allColors, 0, numColors);
        JButton[] colorButtons = new JButton[colors.length];
        JPanel colorButtonPanel = new JPanel();
        colorButtonPanel.setLayout(new FlowLayout());

        for (int i = 0; i < colors.length; i++) {
            colorButtons[i] = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    g.setColor(getBackground());
                    g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
                    super.paintComponent(g);
                }
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(50, 50);
                }
                @Override
                protected void paintBorder(Graphics g) {
                    g.setColor(getForeground());
                    g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
                }
            };
            colorButtons[i].setOpaque(false);
            colorButtons[i].setContentAreaFilled(false);
            colorButtons[i].setBackground(colors[i]);
            colorButtons[i].setBorderPainted(false);

            int auxI = i;
            colorButtons[i].addActionListener(e -> {
                selectedColor = colors[auxI];
            });
            colorButtonPanel.add(colorButtons[i]);
        }
        //to move the colorButtonPanel down on the y-axis
        colorButtonWrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints ColorButton = new GridBagConstraints();
        ColorButton.gridwidth = GridBagConstraints.REMAINDER;
        ColorButton.weighty = 1.0;
        colorButtonWrapperPanel.add(colorButtonPanel, ColorButton);
    }

    void initSubmitButton(){
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isCodeMade) {
                    ctrl.setSecretCodes(SecretCode,SecretCode);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please complete the secret code before submitting");
                }
            }
        });
        submitButtonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc= new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(20, 0, 5, 0);
        submitButtonPanel.add(submitButton,gbc);
    }



    private void initSecretCodeButtons(){
        JButton guessButtons[] = new JButton[CodeLength];

        guessGridPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 10, 10);

        for (int i = 0; i < CodeLength; i++) {
            c.gridx = i;
            guessButtons[i] = new JButton();
            guessButtons[i].setOpaque(true);
            guessButtons[i].setBorderPainted(true);
            guessButtons[i].setBackground(Color.GRAY);
            guessButtons[i].setPreferredSize(new Dimension(40, 40));
            int auxI = i;
            guessButtons[i].addActionListener(e -> {
                if (selectedColor != null) {
                    guessButtons[auxI].setBackground(selectedColor);
                    SecretCode[auxI] = ctrl.colorToInteger(selectedColor);
                    painted[auxI] = true;
                    checkCode();
                }
            });
            guessGridPanel.add(guessButtons[i], c);
        }
    }


    void initComponents(){
        initSecretCodeButtons();
        initColorsButtons();
        initSubmitButton();

        JPanel gridsPanel = new JPanel();
        gridsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.insets = new Insets(0, 0, 0, 0);

        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;


        gridsPanel.add(guessGridPanel,gbcPanel);

        gbcPanel.gridy=1;

        gridsPanel.add(colorButtonWrapperPanel,gbcPanel);

        gbcPanel.gridy=2;

        gridsPanel.add(submitButtonPanel,gbcPanel);

        // Add the container panel to the frame
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(gridsPanel, BorderLayout.NORTH);
        this.add(containerPanel);
    }

    private void checkCode(){
        for(int i=0;i<CodeLength;++i){  //The Secret Code hasn't been completed yet
            if(!painted[i])return;
        }
        //If the Secret Code was already made
        this.isCodeMade = true;
    }

    public String getSecretCode() {
        this.dispose();
        return SecretCode.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
