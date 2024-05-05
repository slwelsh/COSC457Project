package serverconnect;

import javax.swing.*;
import java.sql.*;

public class AllowFrame extends JFrame{

    private JTextField patientSSNTextField;

    public AllowFrame(){
        try {
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws SQLException {
        

        setTitle("Add Allowance");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        patientSSNTextField = new JTextField(10);
        JButton checkButton = new JButton("Submit");
        checkButton.addActionListener(e -> checkPatient());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Patient SSN:"));
        panel.add(patientSSNTextField);
        panel.add(checkButton);

        this.getContentPane().add(panel);
        this.setSize(300, 150);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        
    }

   

    private void checkPatient() {
        String SSN = patientSSNTextField.getText();

        final String username = "admin";
        final String password = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            try (Statement statement = connection.createStatement()) {
                String query = "SELECT * FROM caretrackdb.Patient WHERE SSN = '" + SSN + "'";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    showDetailsScreen(SSN);
                } else {
                    JOptionPane.showMessageDialog(this, "Patient not found.");
                }
            } 
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showDetailsScreen(String id) {
        this.getContentPane().removeAll(); 

        JTextField aIDTextField = new JTextField(10);
        JTextField amountTextField = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                submitDetails(aIDTextField.getText(), amountTextField.getText(), id);
            } catch (SQLException e1) {
              
                e1.printStackTrace();
            }
        });

    
        JPanel panel = new JPanel();
        panel.add(new JLabel("Allowance ID:"));
        panel.add(aIDTextField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountTextField);
        panel.add(submitButton);

        this.getContentPane().add(panel);
        this.revalidate();
        this.repaint();
    }

    private void submitDetails(String aID, String amount, String SSN) throws SQLException {

        final String username = "admin";
        final String password = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "insert into caretrackdb.Allowance values(?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, aID);
                preparedStatement.setString(2, SSN);
                preparedStatement.setString(3, amount);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(AllowFrame.this, 
                                "Allowance Added!\n" +
                                "Patients CANNOT use this money on;\n" +
                                "DRUGS, ALCOHOL, TOBACCO, or GAMBLING.");

                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Add Allowance.");
                }
            
            
            }

            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        
        System.out.println(ex);
        
        
        }

        this.getContentPane().removeAll();
        initialize();
    }
}