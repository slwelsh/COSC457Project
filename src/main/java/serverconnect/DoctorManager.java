package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorManager extends StaffManager {
    private JTextField specialtyTextField;

    public DoctorManager(JPanel panel) {
        super(panel); 
        setTitle("Add Doctor to Database");

        panel.setLayout(new GridLayout(12, 2)); 

        panel.add(new JLabel("Specialty:"));
        specialtyTextField = new JTextField();
        panel.add(specialtyTextField);

        JButton addDoctorButton = new JButton("Add Doctor");
        addDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDoctorToDatabase();
            }
        });
        panel.add(addDoctorButton);

        setVisible(true);
    }

    private void addDoctorToDatabase() {
        String staffId = staffIdTextField.getText();
        String specialty = specialtyTextField.getText();

        
        final String ID = "root";
        final String PW = "COSC*ncm6n";
        final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(SERVER, ID, PW)) {
            String query = "INSERT INTO caretrackdb.Doctor (StaffId, Specialty) VALUES (?, ?)";

            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, staffId);
                statement.setString(2, specialty);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Doctor added to database successfully.");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add doctor to database.");
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
        specialtyTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel panel = new JPanel(); 
                new DoctorManager(panel).setVisible(true); 
            }
        });
    }
}

