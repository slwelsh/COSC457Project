package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdatePerson extends JFrame{

    private JTextField IdTextField;
    private JTextField NameTextField;
    private JTextField Name2TextField;
    private JTextField AddressTextField;
    private JTextField NumTextField;
    private JTextField SalTextField;
    private JTextField SchedTextField;
    private JTextField GenderTextField;
    private JTextField EmailTextField;
    private JTextField InsuranceTextField;
    private String type;

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

        String[] personOptions = {"", "Staff", "Patient", "Guest"};
        JComboBox<String> jobComboBox = new JComboBox<>(personOptions);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Person Type:"));
        panel.add(jobComboBox);
        panel.add(new JLabel("Enter Person ID:"));
        IdTextField = new JTextField(10);
        panel.add(IdTextField);

        

        JButton checkButton = new JButton("Submit");
        panel.add(checkButton);

        checkButton.addActionListener(e -> checkStaff(jobComboBox.getSelectedItem().toString()));


        this.getContentPane().add(panel);
        this.setSize(250, 150);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

   

    private void checkStaff(String t) {
        String personId = IdTextField.getText();
        type = t;

        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection con = DriverManager.getConnection(url, user, pass)) {

            try (Statement statement = con.createStatement()) {
                String query = "";
                if(type.equals("Staff"))
                    query = "SELECT * FROM caretrackdb.Staff WHERE StaffId = '" + personId +"'";
                else if(type.equals("Patient"))
                    query = "SELECT * FROM caretrackdb.Patient WHERE SSN = '" + personId +"'";
                else if(type.equals("Guest"))
                    query = "SELECT * FROM caretrackdb.Guest WHERE GuestId = '" + personId +"'";
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

        JButton submitButton = new JButton("Submit");
        NameTextField = new JTextField(10);
        Name2TextField = new JTextField(10);
        NumTextField = new JTextField(10);
        AddressTextField = new JTextField(10);
        SalTextField = new JTextField(10);
        SchedTextField= new JTextField(10);
        GenderTextField = new JTextField(10);
        EmailTextField = new JTextField(10);
        InsuranceTextField = new JTextField(10);
        
        submitButton.addActionListener(e -> {
            try {
                if(type.equals("Staff")) {
                    submitStaffDetails(NameTextField.getText(), Name2TextField.getText(), AddressTextField.getText(), 
                                    NumTextField.getText(), SalTextField.getText(), SchedTextField.getText(), id);
                } else if(type.equals("Patient")) {
                    submitPatientDetails(NameTextField.getText(), Name2TextField.getText(), GenderTextField.getText(), id);
                } else {
                    submitGuestDetails(NameTextField.getText(), NumTextField.getText(), EmailTextField.getText(), 
                                    InsuranceTextField.getText(), AddressTextField.getText(), id);
                }
                
            } catch (SQLException e1) {
              
                e1.printStackTrace();
            }
        });

    
        JPanel panel = new JPanel();
        
        //Adding type specific text fields
        if(type.equals("Staff")){
            staffPanel(panel);
            panel.setLayout(new GridLayout(7,2, 5, 5));
            setSize(250,250);
        } else if(type.equals("Patient")) {
            patientPanel(panel);
            panel.setLayout(new GridLayout(4,2, 5, 5));
            setSize(250,175);
        } else if(type.equals("Guest")) {
            guestPanel(panel);
            panel.setLayout(new GridLayout(6,2,5, 5));
            setSize(250,200);
        } else {
            System.out.println("error, type = " + type);
        }
        panel.add(submitButton);
        

        this.getContentPane().add(panel);
        
        this.revalidate();
        this.repaint();
    }

    //Submit Guest Update
    private void submitGuestDetails(String name, String num, String email, String insur, String add, String ID) throws SQLException {

        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            String sql = "UPDATE caretrackdb.Guest " +
                    " SET Name = ? , Number = ? , Email = ? , Insurance = ? , Address = ? WHERE GuestId = ?;";
            
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, num);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, insur);
                preparedStatement.setString(5, add);
                preparedStatement.setString(6, ID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(UpdatePerson.this, "Person Updated!");
                } else {
                    JOptionPane.showMessageDialog(this, "Guest not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.getContentPane().removeAll();
        dispose();
    }


    //Submit Staff Update
    private void submitStaffDetails(String fname,String lname,String address, String num, String sal, String sched, String ID) throws SQLException {

        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            String sql = "UPDATE caretrackdb.Staff " +
                    " SET FName = ? , LName = ? , Address = ? , Number = ? , Salary = ? , Schedule = ? WHERE StaffId = ?;";
            
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, fname);
                preparedStatement.setString(2, lname);
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, num);
                preparedStatement.setString(5, sal);
                preparedStatement.setString(6, sched);
                preparedStatement.setString(7, ID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(UpdatePerson.this, "Person Updated!");
                } else {
                    JOptionPane.showMessageDialog(this, "Guest not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.getContentPane().removeAll();
        dispose();
    }


    //Submit Patient Update
    private void submitPatientDetails(String fname, String lname, String gender, String ID) throws SQLException {

        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            String sql = "UPDATE caretrackdb.Patient " +
                    " SET FName = ? , LName = ? , Gender = ? WHERE SSN = ?;";
            
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, fname);
                preparedStatement.setString(2, lname);
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, ID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(UpdatePerson.this, "Person Updated!");
                } else {
                    JOptionPane.showMessageDialog(this, "Guest not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.getContentPane().removeAll();
        dispose();
    }

    private void staffPanel(JPanel panel) {
        panel.add(new JLabel("First Name:"));
        panel.add(NameTextField);
        panel.add(new JLabel("Last Name:"));
        panel.add(Name2TextField);
        panel.add(new JLabel("Address:"));
        panel.add(AddressTextField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(NumTextField);
        panel.add(new JLabel("Salary:"));
        panel.add(SalTextField);
        panel.add(new JLabel("Schedule:"));
        panel.add(SchedTextField);
    }
    private void patientPanel(JPanel panel) {
        panel.add(new JLabel("First Name:"));
        panel.add(NameTextField);
        panel.add(new JLabel("Last Name:"));
        panel.add(Name2TextField);
        panel.add(new JLabel("Gender:"));
        panel.add(GenderTextField);
}
    private void guestPanel(JPanel panel) {
        panel.add(new JLabel("Name:"));
        panel.add(NameTextField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(NumTextField);
        panel.add(new JLabel("Email:"));
        panel.add(EmailTextField);
        panel.add(new JLabel("Address:"));
        panel.add(AddressTextField);
        panel.add(new JLabel("Insurance:"));
        panel.add(InsuranceTextField);
    }
}