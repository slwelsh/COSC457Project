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
    private JTextField licenseNumberTextField, patientLoadTextField;

    public NurseManager() {
        setTitle("Add Nurse to Database");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));

        panel.add(new JLabel("License Number:"));
        licenseNumberTextField = new JTextField();
        panel.add(licenseNumberTextField);

        panel.add(new JLabel("Patient Load:"));
        patientLoadTextField = new JTextField();
        panel.add(patientLoadTextField);

        JButton addButton = new JButton("Add Nurse");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNurseToDatabase();
            }
        });
        panel.add(addButton);

        add(panel);

        setVisible(true);
    }

    private void addNurseToDatabase() {
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
        String licenseNumber = licenseNumberTextField.getText();
        String patientLoad = patientLoadTextField.getText();

        // Database connection details
        final String ID = "root";
        final String PW = "COSC*ncm6n";
        final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(SERVER, ID, PW)) {
            String query = "INSERT INTO nurses (StaffId, Name, LName, SSN, Dob, Address, Number, Salary, Schedule, LicenseNumber, PatientLoad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                statement.setString(10, licenseNumber);
                statement.setString(11, patientLoad);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Nurse added to database successfully.");
                    clearFields(); // Clear fields after successful addition
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
        patientLoadTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NurseManager().setVisible(true);
            }
        });
    }
}
