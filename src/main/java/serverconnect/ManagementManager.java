package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManagementManager extends StaffManager {
    private JTextField jobTitleTextField, departmentTextField;

    public ManagementManager() {
        setTitle("Add Management Staff to Database");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        panel.add(new JLabel("Job Title:"));
        jobTitleTextField = new JTextField();
        panel.add(jobTitleTextField);

        panel.add(new JLabel("Department:"));
        departmentTextField = new JTextField();
        panel.add(departmentTextField);

        JButton addButton = new JButton("Add Management Staff");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addManagementStaffToDatabase();
            }
        });
        panel.add(addButton);

        add(panel);

        setVisible(true);
    }

    private void addManagementStaffToDatabase() {
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
        String jobTitle = jobTitleTextField.getText();
        String department = departmentTextField.getText();

        // Database connection details
        final String ID = "root";
        final String PW = "COSC*ncm6n";
        final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(SERVER, ID, PW)) {
            String query = "INSERT INTO management (StaffId, Name, LName, SSN, Dob, Address, Number, Salary, Schedule, JobTitle, Department) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                statement.setString(10, jobTitle);
                statement.setString(11, department);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Management staff added to database successfully.");
                    clearFields(); // Clear fields after successful addition
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add management staff to database.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    // Overriding the clearFields method to include clearing management-specific fields
    @Override
    protected void clearFields() {
        super.clearFields(); // Invoke parent class method to clear common fields
        jobTitleTextField.setText("");
        departmentTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ManagementManager().setVisible(true);
            }
        });
    }
}

