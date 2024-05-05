package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StaffManager extends JFrame {
    protected JTextField staffIdTextField, nameTextField, lNameTextField, ssnTextField, dobTextField, addressTextField, numberTextField, salaryTextField, scheduleTextField;

    public StaffManager() {
        setTitle("Add Staff to Database");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 2));

        panel.add(new JLabel("Staff ID:"));
        staffIdTextField = new JTextField();
        panel.add(staffIdTextField);

        panel.add(new JLabel("Name:"));
        nameTextField = new JTextField();
        panel.add(nameTextField);

        panel.add(new JLabel("Last Name:"));
        lNameTextField = new JTextField();
        panel.add(lNameTextField);

        panel.add(new JLabel("SSN:"));
        ssnTextField = new JTextField();
        panel.add(ssnTextField);

        panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        dobTextField = new JTextField();
        panel.add(dobTextField);

        panel.add(new JLabel("Address:"));
        addressTextField = new JTextField();
        panel.add(addressTextField);

        panel.add(new JLabel("Phone Number:"));
        numberTextField = new JTextField();
        panel.add(numberTextField);

        panel.add(new JLabel("Salary:"));
        salaryTextField = new JTextField();
        panel.add(salaryTextField);

        panel.add(new JLabel("Schedule:"));
        scheduleTextField = new JTextField();
        panel.add(scheduleTextField);

        JButton addButton = new JButton("Add Staff");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStaffToDatabase();
            }
        });
        panel.add(addButton);

        add(panel);

        setVisible(true);
    }

    protected void addStaffToDatabase() {
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

        // Database connection details
        final String ID = "root";
        final String PW = "COSC*ncm6n";
        final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(SERVER, ID, PW)) {
            String query = "INSERT INTO staff (StaffId, Name, LName, SSN, Dob, Address, Number, Salary, Schedule) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Staff added to database successfully.");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add staff to database.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    protected void clearFields() {
        staffIdTextField.setText("");
        nameTextField.setText("");
        lNameTextField.setText("");
        ssnTextField.setText("");
        dobTextField.setText("");
        addressTextField.setText("");
        numberTextField.setText("");
        salaryTextField.setText("");
        scheduleTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StaffManager().setVisible(true);
            }
        });
    }
}
