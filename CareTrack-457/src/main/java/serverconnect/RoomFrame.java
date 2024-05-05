package serverconnect;

import javax.swing.*;
import java.sql.*;

public class RoomFrame extends JFrame{

    private JTextField patientSSNTextField;

    public RoomFrame(){
        try {
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws SQLException {
        

        setTitle("Edit Room");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        patientSSNTextField = new JTextField(10);
        JButton checkButton = new JButton("Submit");
        checkButton.addActionListener(e -> checkGuest());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Patient SSN:"));
        panel.add(patientSSNTextField);
        panel.add(checkButton);

        this.getContentPane().add(panel);
        this.setSize(300, 150);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void checkGuest() {
        String patientSSN = patientSSNTextField.getText();

        final String username = "admin";
        final String password = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            try (Statement statement = connection.createStatement()) {
                String query = "SELECT * FROM caretrackdb.Patient WHERE SSN = '" + patientSSN + "'";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    showDetailsScreen(patientSSN);
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

        JTextField roomTextField = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                submitDetails(roomTextField.getText(), id);
                dispose();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

    
        JPanel panel = new JPanel();
        panel.add(new JLabel("New Room Number:"));
        panel.add(roomTextField);
        panel.add(submitButton);

        this.getContentPane().add(panel);
        this.revalidate();
        this.repaint();
    }

    private void submitDetails(String room, String ID) throws SQLException {

        final String username = "admin";
        final String password = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE caretrackdb.Patient SET RoomNo = ? WHERE SSN = " + ID;
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, room);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(RoomFrame.this, "Room Updated!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Update Room.");
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