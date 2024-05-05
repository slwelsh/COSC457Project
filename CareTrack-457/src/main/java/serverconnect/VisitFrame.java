package serverconnect;

import javax.swing.*;
import java.sql.*;

public class VisitFrame extends JFrame{

    private JTextField guestIdTextField;

    public VisitFrame(){
        try {
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws SQLException {
        

        setTitle("Add Visit");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        guestIdTextField = new JTextField(10);
        JButton checkButton = new JButton("Submit");
        checkButton.addActionListener(e -> checkGuest());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Guest ID:"));
        panel.add(guestIdTextField);
        panel.add(checkButton);

        this.getContentPane().add(panel);
        this.setSize(300, 150);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        
    }

   

    private void checkGuest() {
        String guestId = guestIdTextField.getText();

        final String username = "admin";
        final String password = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            try (Statement statement = connection.createStatement()) {
                String query = "SELECT * FROM caretrackdb.Guest WHERE GuestId = '" + guestId + "'";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    showDetailsScreen(guestId);
                } else {
                    JOptionPane.showMessageDialog(this, "Guest not found.");
                }
            } 
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showDetailsScreen(String id) {
        this.getContentPane().removeAll(); 

        JTextField dateTextField = new JTextField(10);
        JTextField reasonTextField = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                submitDetails(dateTextField.getText(), reasonTextField.getText(), id);
            } catch (SQLException e1) {
              
                e1.printStackTrace();
            }
        });

    
        JPanel panel = new JPanel();
        panel.add(new JLabel("Date:"));
        panel.add(dateTextField);
        panel.add(new JLabel("Reason:"));
        panel.add(reasonTextField);
        panel.add(submitButton);

        this.getContentPane().add(panel);
        this.revalidate();
        this.repaint();
    }

    private void submitDetails(String date, String reason, String ID) throws SQLException {

        final String username = "admin";
        final String password = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "insert into caretrackdb.Visits values(?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, ID);
                preparedStatement.setString(3, reason);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(VisitFrame.this, "Visit Added!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Add Visit.");
                }
            
            
            }

            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        
        System.out.println(ex);
        
        
        }

        this.getContentPane().removeAll();
        dispose();
    }
}