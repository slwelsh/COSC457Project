package serverconnect;

import javax.swing.*;
import java.sql.*;

public class AddDiagnosis extends JFrame {
    private JTextField patientIdTextField;
    private JTextField diagnosisTextField;
    private String userID;

    public AddDiagnosis(String ID) {
        try {
            userID = ID;
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws SQLException {
        setTitle("Doctor Frame");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        patientIdTextField = new JTextField(10);
        diagnosisTextField = new JTextField(15);

        JButton treatButton = new JButton("Treat Diagnosis");
        treatButton.addActionListener(e -> treatDiagnosis());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Patient ID:"));
        panel.add(patientIdTextField);
        panel.add(new JLabel("Enter Diagnosis:"));
        panel.add(diagnosisTextField);
        panel.add(treatButton);

        add(panel);
        //add(new JScrollPane(medicalConditionTextArea));
        setSize(325, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void treatDiagnosis() {
        String patientId = patientIdTextField.getText();
        String diagnosis = diagnosisTextField.getText();

        // Database connection details
        final String username = "admin";
        final String password = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Insert diagnosis into diagnosis table
            String insertDiagnosisSQL = "INSERT INTO caretrackdb.Diagnosis (PSSN, DocId, CondName) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertDiagnosisSQL)) {
                preparedStatement.setString(1, patientId);
                preparedStatement.setString(2, userID);
                preparedStatement.setString(3, diagnosis);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Diagnosis added.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error treating diagnosis.");
        }
    }
}