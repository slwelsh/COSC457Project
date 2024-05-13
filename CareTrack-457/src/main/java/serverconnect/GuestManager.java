package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GuestManager extends JFrame {
    private JTextField guestIdTextField, nameTextField, relationshipTextField, phoneTextField, emailTextField, insuranceTextField, addressTextField, patientSSNTextField;

    public GuestManager() {
        setTitle("Add Guest to Database");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));

        panel.add(new JLabel("Guest ID:"));
        guestIdTextField = new JTextField();
        panel.add(guestIdTextField);

        panel.add(new JLabel("Name:"));
        nameTextField = new JTextField();
        panel.add(nameTextField);

        panel.add(new JLabel("Relationship to Patient:"));
        relationshipTextField = new JTextField();
        panel.add(relationshipTextField);

        panel.add(new JLabel("Phone Number:"));
        phoneTextField = new JTextField();
        panel.add(phoneTextField);

        panel.add(new JLabel("Email:"));
        emailTextField = new JTextField();
        panel.add(emailTextField);

        panel.add(new JLabel("Insurance:"));
        insuranceTextField = new JTextField();
        panel.add(insuranceTextField);

        panel.add(new JLabel("Address:"));
        addressTextField = new JTextField();
        panel.add(addressTextField);

        panel.add(new JLabel("Patient's SSN:"));
        patientSSNTextField = new JTextField();
        panel.add(patientSSNTextField);

        JButton addButton = new JButton("Add Guest");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGuestToDatabase();
            }
        });
        panel.add(addButton);

        add(panel);

        setVisible(true);
    }

    private void addGuestToDatabase() {
        String guestId = guestIdTextField.getText();
        String name = nameTextField.getText();
        String relationship = relationshipTextField.getText();
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();
        String insurance = insuranceTextField.getText();
        String address = addressTextField.getText();
        String patientSSN = patientSSNTextField.getText();

        final String ID = "root";
        final String PW = "COSC*ncm6n";
        final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(SERVER, ID, PW)) {
            String query = "INSERT INTO caretrackdb.Guest (GuestId, Name, RelationshipToPatient, PhoneNumber, Email, Insurance, Address, PSSN) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, guestId);
                statement.setString(2, name);
                statement.setString(3, relationship);
                statement.setString(4, phone);
                statement.setString(5, email);
                statement.setString(6, insurance);
                statement.setString(7, address);
                statement.setString(8, patientSSN);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Guest added to database successfully.");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add guest to database.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    private void clearFields() {
        guestIdTextField.setText("");
        nameTextField.setText("");
        relationshipTextField.setText("");
        phoneTextField.setText("");
        emailTextField.setText("");
        insuranceTextField.setText("");
        addressTextField.setText("");
        patientSSNTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuestManager().setVisible(true);
            }
        });
    }
}