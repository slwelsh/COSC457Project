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

    public ManagementManager(JPanel panel) {
        super(panel); // Invoke parent constructor
        setTitle("Add Management Staff to Database");

        panel.setLayout(new GridLayout(12, 2)); // Increase rows for the additional fields

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

        setVisible(true);
    }

    private void addManagementStaffToDatabase() {
        // Get values from text fields
        String staffId = staffIdTextField.getText();
        String jobTitle = jobTitleTextField.getText();
        String department = departmentTextField.getText();

        // Database connection details
        final String ID = "root";
        final String PW = "COSC*ncm6n";
        final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(SERVER, ID, PW)) {
            String query = "INSERT INTO management (StaffId, JobTitle, Department) VALUES (?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, staffId);
                statement.setString(2, jobTitle);
                statement.setString(3, department);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Management staff added to database successfully.");
                    clearFields(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add management staff to database.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    // Override clearFields method to include the additional fields
    @Override
    protected void clearFields() {
        super.clearFields(); 
        jobTitleTextField.setText("");
        departmentTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel panel = new JPanel(); // Create a panel
                new ManagementManager(panel).setVisible(true); // Pass the panel to the constructor
            }
        });
    }
}

