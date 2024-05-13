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
    private JTextField StaffTypeTextField;
    private JTextField StaffType2TextField;
    private String type;
    private String staffType;

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

   
    //Check Staff exists
    private void checkStaff(String t) {
        String personId = IdTextField.getText();
        type = t;

        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection con = DriverManager.getConnection(url, user, pass)) {

            try (Statement statement = con.createStatement()) {
                String query = "";
                if(type.equals("Staff")) {
                    query = "SELECT * FROM caretrackdb.Staff WHERE StaffId = '" + personId +"'";

                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        staffTypeCheck(personId);
                        showDetailsScreen(personId);
                    } else {
                        JOptionPane.showMessageDialog(this, type+" not found.");
                    }

                } else {
                    if(type.equals("Patient"))
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Check staff type exists
    private void staffTypeCheck(String ID) {
        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";
        
        try (Connection con = DriverManager.getConnection(url, user, pass)) {

            try (Statement statement = con.createStatement()) {
                String query = "SELECT * FROM caretrackdb.Nurse WHERE StaffId = '" + ID +"'";

                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) 
                    staffType = "Nurse";
                else {
                    query = "SELECT * FROM caretrackdb.Doctor WHERE StaffId = '" + ID +"'";
                    resultSet = statement.executeQuery(query);
                    if(resultSet.next()) 
                        staffType = "Doctor";
                    else {
                        query = "SELECT * FROM caretrackdb.Management WHERE StaffId = '" + ID +"'";
                        resultSet = statement.executeQuery(query);
                        if(resultSet.next()) 
                            staffType = "Management";
                    }
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
        StaffTypeTextField = new JTextField(10);
        StaffType2TextField = new JTextField(10);
        
        submitButton.addActionListener(e -> {
            try {
                if(type.equals("Staff")) {
                    if(staffType.equals("Nurse")) {
                        submitNurseDetails(NameTextField.getText(), Name2TextField.getText(), AddressTextField.getText(), 
                                    NumTextField.getText(), SalTextField.getText(), SchedTextField.getText(), StaffTypeTextField.getText(), id);
                    } else if(staffType.equals("Doctor")) {
                        submitDoctorDetails(NameTextField.getText(), Name2TextField.getText(), AddressTextField.getText(), 
                                    NumTextField.getText(), SalTextField.getText(), SchedTextField.getText(), StaffTypeTextField.getText(), id);
                    } if(staffType.equals("Management")) {
                        submitManageDetails(NameTextField.getText(), Name2TextField.getText(), AddressTextField.getText(), 
                                    NumTextField.getText(), SalTextField.getText(), SchedTextField.getText(), StaffTypeTextField.getText(), StaffType2TextField.getText(), id);
                    }
                } else if(type.equals("Patient")) {
                    submitPatientDetails(NameTextField.getText(), Name2TextField.getText(), GenderTextField.getText(), id);
                } else if(type.equals("Guest")){
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
            panel.setLayout(new GridLayout(9,2, 5, 5));
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
                    JOptionPane.showMessageDialog(UpdatePerson.this, "Guest Updated!");
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


    //Submit Nurse Update
    private void submitNurseDetails(String fname,String lname,String address, String num, String sal, String sched, String license, String ID) throws SQLException {

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
                    String sql2 = "UPDATE caretrackdb.Nurse SET LicenseNo = ? WHERE StaffId = ?";
                    try (PreparedStatement preparedStatement2 = con.prepareStatement(sql2)) {
                        preparedStatement2.setString(1, license);
                        preparedStatement2.setString(2, ID);

                        int rows = preparedStatement2.executeUpdate();
                        if (rows > 0)
                            JOptionPane.showMessageDialog(UpdatePerson.this, "Staff Updated!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Staff not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.getContentPane().removeAll();
        dispose();
    }

    //Submit Doctor Update
    private void submitDoctorDetails(String fname,String lname,String address, String num, String sal, String sched, String special, String ID) throws SQLException {

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
                    String sql2 = "UPDATE caretrackdb.Doctor SET Speciality = ? WHERE StaffId = ?";
                    try (PreparedStatement preparedStatement2 = con.prepareStatement(sql2)) {
                        preparedStatement2.setString(1, special);
                        preparedStatement2.setString(2, ID);

                        int rows = preparedStatement2.executeUpdate();
                        if (rows > 0)
                            JOptionPane.showMessageDialog(UpdatePerson.this, "Staff Updated!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Staff not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.getContentPane().removeAll();
        dispose();
    }

    //Submit Management Update
    private void submitManageDetails(String fname,String lname,String address, String num, String sal, String sched, String job, String depart, String ID) throws SQLException {

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
                    String sql2 = "UPDATE caretrackdb.Management SET JobTitle = ? , Department = ? WHERE StaffId = ?";
                    try (PreparedStatement preparedStatement2 = con.prepareStatement(sql2)) {
                        preparedStatement2.setString(1, job);
                        preparedStatement2.setString(2, depart);
                        preparedStatement2.setString(3, ID);

                        int rows = preparedStatement2.executeUpdate();
                        if (rows > 0)
                            JOptionPane.showMessageDialog(UpdatePerson.this, "Staff Updated!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Staff not found.");
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
                    JOptionPane.showMessageDialog(UpdatePerson.this, "Patient Updated!");
                } else {
                    JOptionPane.showMessageDialog(this, "Patient not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.getContentPane().removeAll();
        dispose();
    }

    //User type panels
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
        if(staffType.equals("Nurse")) {
            panel.add(new JLabel("License Number:"));
            panel.add(StaffTypeTextField);
        } else if(staffType.equals("Doctor")) {
            panel.add(new JLabel("Speciality:"));
            panel.add(StaffTypeTextField);
        } else if(staffType.equals("Management")) {
            panel.add(new JLabel("Job Title:"));
            panel.add(StaffTypeTextField);
            panel.add(new JLabel("Department:"));
            panel.add(StaffType2TextField);
        }
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