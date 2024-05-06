package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public loginGUI() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"","Doctor", "Nurse", "Management"};
        roleComboBox = new JComboBox<>(roles);
        JButton loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(roleLabel);
        panel.add(roleComboBox);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();
                User user = authenticate(username, password, role);
                if (user != null) {
                    JOptionPane.showMessageDialog(loginGUI.this, "Login successful");
                    Dashboard dashboard = new Dashboard("Dashboard", user);
                    dashboard.setVisible(true);
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(loginGUI.this, "Invalid username, password, or role", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private User authenticate(String username, String password, String role) {
        final String ID = "admin";
        final String PW = "COSC*ncm6n";
        final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        try (Connection con = DriverManager.getConnection(SERVER, ID, PW)) {
            try (Statement statement = con.createStatement()) {
                    String query = "SELECT * FROM caretrackdb.Login WHERE User= '"+username+"' AND Pass= '"+password+"'";
                    ResultSet resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        String userID = resultSet.getString("StaffId");
                        String userType = roleComboBox.getSelectedItem().toString();
                        return new User(userID, userType);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        } 

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new loginGUI().setVisible(true);
            }
        });
    }
}