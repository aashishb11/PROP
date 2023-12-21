package main.CapaPresentacio.Vista;

import main.CapaPresentacio.Controllers.CtrlPresentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class vistaRecords extends JFrame {

    private CtrlPresentacion ctrl;

    private MainWindow mainWindow;
    public vistaRecords(CtrlPresentacion ctrl, MainWindow mainWindow){
        this.ctrl = ctrl;
        this.mainWindow = mainWindow;
        showRecords();
    }

    /**
     * It shows the top 10 records of the game.
     * The records should be a table with 3 fields.. Player name, Score, Ranks
     */
    public void showRecords() {
        List<String> records = ctrl.getTopRecords();
        String[] columnNames = {"Rank","Player Name", "Score"};

        Set<String> uniqueNames = new HashSet<>();
        List<Object[]> uniqueRecords = new ArrayList<>();
        for (String record : records) {
            String[] parts = record.split(", ");
            String playerName = parts[0];
            if (!uniqueNames.contains(playerName)) {
                Object[] recordData = new Object[3];
                recordData[0] = uniqueRecords.size() + 1;
                recordData[1] = playerName;
                recordData[2] = Integer.parseInt(parts[1]);

                uniqueRecords.add(recordData);
                uniqueNames.add(playerName);
            }
        }
        Object[][] data = uniqueRecords.toArray(new Object[0][0]);
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Helvetica",Font.BOLD,16));
        ((DefaultTableCellRenderer)table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        pack();
        setTitle("LeaderBoard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }


}
