package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientManager extends JFrame {
    private JTextField ssnTextField, fNameTextField, lNameTextField, dobTextField, genderTextField, startDateTextField, roomNoTextField, nurseIdTextField;

    public PatientManager() {
        setTitle("Add Patient to Database");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));

        panel.add(new JLabel("SSN:"));
        ssnTextField = new JTextField();
        panel.add(ssnTextField);

        panel.add(new JLabel("First Name:"));
        fNameTextField = new JTextField();
        panel.add(fNameTextField);

        panel.add(new JLabel("Last Name:"));
        lNameTextField = new JTextField();
        panel.add(lNameTextField);

        panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        dobTextField = new JTextField();
        panel.add(dobTextField);

        panel.add(new JLabel("Gender:"));
        genderTextField = new JTextField();
        panel.add(genderTextField);

        panel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        startDateTextField = new JTextField();
        panel.add(startDateTextField);

        panel.add(new JLabel("Room Number:"));
        roomNoTextField = new JTextField();
        panel.add(roomNoTextField);

        panel.add(new JLabel("Nurse ID:"));
        nurseIdTextField = new JTextField();
        panel.add(nurseIdTextField);

        JButton addButton = new JButton("Add Patient");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatientToDatabase();
            }
        });
        panel.add(addButton);

        add(panel);

        setVisible(true);
    }

    private void addPatientToDatabase() {
        String ssn = ssnTextField.getText();
        String fName = fNameTextField.getText();
        String lName = lNameTextField.getText();
        String dob = dobTextField.getText();
        String gender = genderTextField.getText();
        String startDate = startDateTextField.getText();
        String roomNo = roomNoTextField.getText();
        String nurseId = nurseIdTextField.getText();

        final String ID = "root";
        final String PW = "COSC*ncm6n";
        final String SERVER = "jdbc:mysql://34.123.199.211:3306/caretrackdb?serverTimezone=EST";

        try (Connection con = DriverManager.getConnection(SERVER, ID, PW)) {
            String query = "INSERT INTO patients (SSN, FName, LName, Dob, Gender, StartDate, RoomNo, NurseId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, ssn);
                statement.setString(2, fName);
                statement.setString(3, lName);
                statement.setString(4, dob);
                statement.setString(5, gender);
                statement.setString(6, startDate);
                statement.setString(7, roomNo);
                statement.setString(8, nurseId);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Patient added to database successfully.");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add patient to database.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    private void clearFields() {
        ssnTextField.setText("");
        fNameTextField.setText("");
        lNameTextField.setText("");
        dobTextField.setText("");
        genderTextField.setText("");
        startDateTextField.setText("");
        roomNoTextField.setText("");
        nurseIdTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PatientManager().setVisible(true);
            }
        });
    }
}
