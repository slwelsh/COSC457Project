package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NurseManager extends StaffManager {
    private JTextField licenseNumberTextField;

    public NurseManager(JPanel panel) {
        super(panel); 
        setTitle("Add Nurse to Database");

        panel.setLayout(new GridLayout(12, 2)); // Adjust the grid layout

        panel.add(new JLabel("License Number:"));
        licenseNumberTextField = new JTextField();
        panel.add(licenseNumberTextField);

        JButton addNurseButton = new JButton("Add Nurse");
        addNurseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNurseToDatabase();
            }
        });
        panel.add(addNurseButton);

        setVisible(true);
    }

    private void addNurseToDatabase() {
        String staffId = staffIdTextField.getText();
        String licenseNumber = licenseNumberTextField.getText();
        String schedule = scheduleTextField.getText(); // Get schedule from text field

        final String ID = "root";
        final String PW = "COSC*ncm6n";
        final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(SERVER, ID, PW)) {
            String query = "INSERT INTO caretrackdb.Nurse (StaffId, LicenseNumber, Schedule) VALUES (?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, staffId);
                statement.setString(2, licenseNumber);
                statement.setString(3, schedule);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Nurse added to database successfully.");
                    clearFields(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add nurse to database.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    @Override
    protected void clearFields() {
        super.clearFields(); 
        licenseNumberTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel panel = new JPanel(); 
                new NurseManager(panel).setVisible(true); 
            }
        });
    }
}