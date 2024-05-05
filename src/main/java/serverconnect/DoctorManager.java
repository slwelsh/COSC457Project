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

    public DoctorManager() {
        setTitle("Add Doctor to Database");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Specialty:"));
        specialtyTextField = new JTextField();
        panel.add(specialtyTextField);

        JButton addButton = new JButton("Add Doctor");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDoctorToDatabase();
            }
        });
        panel.add(addButton);

        add(panel);

        setVisible(true);
    }

    private void addDoctorToDatabase() {
        // Get values from text fields
        String staffId = staffIdTextField.getText();
        String name = nameTextField.getText();
        String lName = lNameTextField.getText();
        String ssn = ssnTextField.getText();
        String dob = dobTextField.getText();
        String address = addressTextField.getText();
        String number = numberTextField.getText();
        String salary = salaryTextField.getText();
        String schedule = scheduleTextField.getText();
        String specialty = specialtyTextField.getText();

        // Database connection details
        final String ID = "root";
        final String PW = "COSC*ncm6n";
        final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(SERVER, ID, PW)) {
            String query = "INSERT INTO doctors (StaffId, Name, LName, SSN, Dob, Address, Number, Salary, Schedule, Specialty) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, staffId);
                statement.setString(2, name);
                statement.setString(3, lName);
                statement.setString(4, ssn);
                statement.setString(5, dob);
                statement.setString(6, address);
                statement.setString(7, number);
                statement.setString(8, salary);
                statement.setString(9, schedule);
                statement.setString(10, specialty);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Doctor added to database successfully.");
                    clearFields(); // Clear fields after successful addition
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add doctor to database.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    // Overriding the clearFields method to include clearing doctor-specific fields
    @Override
    protected void clearFields() {
        super.clearFields(); // Invoke parent class method to clear common fields
        specialtyTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DoctorManager().setVisible(true);
            }
        });
    }
}
