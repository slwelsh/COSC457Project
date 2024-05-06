package serverconnect;

import javax.swing.*;
import java.sql.*;

public class NurseFrame extends JFrame {
    private JTextField patientIdTextField;
    private String Id;

    public NurseFrame(String nurseId) {
        try {
            Id = nurseId;
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws SQLException {
        setTitle("Currently Treating");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        patientIdTextField = new JTextField(10);

        JButton addButton = new JButton("Update");
        addButton.addActionListener(e -> addNurseId());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Patient SSN:"));
        panel.add(patientIdTextField);
        panel.add(addButton);

        getContentPane().add(panel);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addNurseId() {
        String patientId = patientIdTextField.getText();

        // Database connection details
        final String username = "admin";
        final String password = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE caretrackdb.Patient SET NurseId = ? WHERE SSN = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Id);
                preparedStatement.setString(2, patientId);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Nurse ID added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add Nurse ID.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding Nurse ID.");
        }
        dispose();
    }

}