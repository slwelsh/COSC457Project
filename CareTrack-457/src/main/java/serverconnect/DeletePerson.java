package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


class DeletePerson extends JFrame {
    private JTextField IdField;
    private JLabel resultLabel;
    
    public DeletePerson(String title) {
        //Frame layout
        super(title);
        setLayout(new GridLayout(4, 2, 5, 5));
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JLabel("Person ID:"));
        add(new JLabel("Person Type:"));
        IdField = new JTextField();
        add(IdField);

        //Type Dropdown
        String[] personOptions = {"", "Staff", "Patient", "Guest"};
        JComboBox<String> jobComboBox = new JComboBox<>(personOptions);
        add(jobComboBox);

        //Search button action
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = jobComboBox.getSelectedItem().toString();
                if(type.equals("Staff"))
                    searchStaff();
                else if(type.equals("Patient"))
                    searchPatient();
                else if(type.equals("Guest"))
                    searchGuest();
            }
        });
        add(new JLabel("Result:"));
        add(searchButton);

        resultLabel = new JLabel();
        add(resultLabel);

        //Delete button action
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = jobComboBox.getSelectedItem().toString();
                if(type.equals("Staff"))
                    deleteStaff();
                else if(type.equals("Patient"))
                    deletePatient();
                else if(type.equals("Guest"))
                    deleteGuest();
            }
        });
        add(deleteButton);
        setLocationRelativeTo(null);
    }

    //Search types
    private void searchStaff() {
        String Id = IdField.getText().trim();

        if (Id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Staff ID");
            return;
        }

        final String username = "admin";
    	final String password = "COSC*ncm6n";
    	final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM caretrackdb.Staff WHERE StaffId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String fname = resultSet.getString("FName");
                        String lname = resultSet.getString("LName");
                        resultLabel.setText("Staff Found: " + lname + ", " + fname);
                    } else {
                       
                        resultLabel.setText("Staff Not Found");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        }
    }

    private void searchPatient() {
        String Id = IdField.getText().trim();

        if (Id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Patient SSN");
            return;
        }

        final String username = "admin";
    	final String password = "COSC*ncm6n";
    	final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM caretrackdb.Patient WHERE SSN = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String fname = resultSet.getString("FName");
                        String lname = resultSet.getString("LName");
                        resultLabel.setText("Patient Found: " + lname + ", " + fname);
                    } else {
                       
                        resultLabel.setText("Patient Not Found");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        }
    }

    private void searchGuest() {
        String Id = IdField.getText().trim();

        if (Id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Guest ID");
            return;
        }

        final String username = "admin";
    	final String password = "COSC*ncm6n";
    	final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM caretrackdb.Guest WHERE GuestId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("Name");
                        resultLabel.setText("Guest Found: " + name);
                    } else {
                       
                        resultLabel.setText("Guest Not Found");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        }
    }

    //Delete types
    private void deleteStaff() {
        String Id = IdField.getText().trim();

        if (Id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Staff ID");
            return;
        }

        final String username = "admin";
    	final String password = "COSC*ncm6n";
    	final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "DELETE FROM caretrackdb.Staff WHERE StaffId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Id);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Staff Deleted Successfully");
                    resultLabel.setText(""); 
                } else {
                    JOptionPane.showMessageDialog(this, "Staff Not Found");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        }
        dispose();
    }

    private void deletePatient() {
        String Id = IdField.getText().trim();

        if (Id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Patient SSN");
            return;
        }

        final String username = "admin";
    	final String password = "COSC*ncm6n";
    	final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "DELETE FROM caretrackdb.Patient WHERE SSN = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Id);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Patient Deleted Successfully");
                    resultLabel.setText(""); 
                } else {
                    JOptionPane.showMessageDialog(this, "Patient Not Found");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        }
        dispose();
    }

    private void deleteGuest() {
        String Id = IdField.getText().trim();

        if (Id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Guest ID");
            return;
        }

        final String username = "admin";
    	final String password = "COSC*ncm6n";
    	final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "DELETE FROM caretrackdb.Guest WHERE GuestId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Id);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Guest Deleted Successfully");
                    resultLabel.setText(""); 
                } else {
                    JOptionPane.showMessageDialog(this, "Guest Not Found");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        }
        dispose();
    }
}