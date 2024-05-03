package serverconnect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdatePerson extends JFrame{

    private JTextField IdTextField;

    public UpdatePerson(){
        try {
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws SQLException {
        

        setTitle("Update");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] personOptions = {"Staff", "Patient", "Guest"};
        JComboBox<String> jobComboBox = new JComboBox<>(personOptions);

        IdTextField = new JTextField(10);
        JButton checkButton = new JButton("Check Person");
        checkButton.addActionListener(e -> checkEmployee(jobComboBox.getSelectedItem().toString()));

        JPanel panel = new JPanel();
        panel.add(new JLabel("Person Type:"));
        panel.add(jobComboBox);
        panel.add(new JLabel("Enter Person ID:"));
        panel.add(IdTextField);
        panel.add(checkButton);


        this.getContentPane().add(panel);
        this.setSize(300, 150);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        
    }

   

    private void checkEmployee(String type) {
        String personId = IdTextField.getText();

        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection con = DriverManager.getConnection(url, user, pass)) {

            try (Statement statement = con.createStatement()) {
                String query = "SELECT * FROM caretrackdb."+type+" WHERE StaffId = '" + personId +"'";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    showDetailsScreen(personId);
                } else {
                    JOptionPane.showMessageDialog(this, type+" not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showDetailsScreen(String id) {
        this.getContentPane().removeAll(); 

        JTextField IDTextField = new JTextField(10);
        JTextField salaryTextField = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                submitPersonDetails(salaryTextField.getText(), id);
            } catch (SQLException e1) {
              
                e1.printStackTrace();
            }
        });

    
        JPanel panel = new JPanel();
        panel.add(new JLabel("Hours:"));
        panel.add(hoursTextField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryTextField);
        panel.add(submitButton);

        this.getContentPane().add(panel);
        this.revalidate();
        this.repaint();
    }

    private void submitPersonDetails(String salary, String ID) throws SQLException {

        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            String sql = "UPDATE Worker " +
                    " SET Salary = ? , HoursWorked = ? , StaffType = ?" +
                    " WHERE employee_id = ?;";
            String temp1 =""+(Math.floor(Math.random() * (100 - 10) + 10) / 100), temp2=""+(Math.floor(Math.random() * (30 - 1) + 1) / 100);
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, salary);
                preparedStatement.setString(2, hours);
                preparedStatement.setString(3, jobTitle);
                preparedStatement.setString(4, ID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(UpdatePerson.this, "Person Updated!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Update Person.");
                }
            
            
            }

            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        
        System.out.println(ex);
        
        
        }
        JOptionPane.showMessageDialog(this, "Employee details submitted.");

        this.getContentPane().removeAll();
        initialize();
    }
}