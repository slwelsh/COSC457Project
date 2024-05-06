package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Dashboard extends JFrame {
    private JTextArea resultColsArea;
    private JTextArea resultTextArea;
    private String userID;

    public Dashboard(String title, User user) {
        userID = user.getCurrentUser(); 

        setTitle(title);
        
        //Panels
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        headerPanel.setBackground(Color.LIGHT_GRAY);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 3));
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());

        JPanel typePanel = new JPanel();
        JPanel logPanel = new JPanel();

        //User profile info
        resultTextArea = new JTextArea();
        resultTextArea.setBounds(150,120,40,40);
        resultTextArea.setEditable(false);
        resultColsArea = new JTextArea();
        resultColsArea.setBounds(150,120,40,40);
        resultColsArea.setEditable(false);

        dashboardView(); 

        //Header info
        JLabel titleLabel = new JLabel("CareTrack");
        titleLabel.setBounds(20, 30, 500, 40);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 32));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton searchButton = new JButton("Search Database");
        searchButton.setBounds(400, 30, 175, 40);
        searchButton.addActionListener(e -> searchPerson());
        headerPanel.add(searchButton);

        JLabel personLabel = new JLabel("Person:");
        personLabel.setBounds(590, 30, 75, 40);
        personLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
        headerPanel.add(personLabel);

        JButton createButton = new JButton("Create");
        createButton.setBounds(650, 30, 75, 40);
        createButton.addActionListener(e -> createPerson());
        headerPanel.add(createButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(730, 30, 75, 40);
        deleteButton.addActionListener(e -> deletePerson());
        headerPanel.add(deleteButton);

        JButton updatePerson = new JButton("Update");
        updatePerson.setBounds(810, 30, 75, 40);
        updatePerson.addActionListener(e -> updatePerson());
        headerPanel.add(updatePerson);

        JButton logoutButton = new JButton("Log out");
        logoutButton.setBounds(400, 30, 175, 40);
        logoutButton.addActionListener(e -> logout(this));
        logPanel.add(logoutButton);

        //User Type Actions
        if(user.getCurrentUserType().equals("Management")) {
            JLabel manLabel = new JLabel("\n\nManagement Actions:");
            manLabel.setBounds(590, 30, 75, 40);
            manLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
            typePanel.add(manLabel);

            JButton visitsButton = new JButton("Add Visit");
            visitsButton.setBounds(400, 30, 175, 40);
            visitsButton.addActionListener(e -> addVisit());
            typePanel.add(visitsButton);

            JButton allowButton = new JButton("Add Allowance");
            allowButton.setBounds(400, 30, 175, 40);
            allowButton.addActionListener(e -> addAllow());
            typePanel.add(allowButton);

            JButton roomButton = new JButton("Edit Patient Room");
            roomButton.setBounds(400, 30, 175, 40);
            roomButton.addActionListener(e -> editRoom());
            typePanel.add(roomButton);

        } else if (user.getCurrentUserType().equals("Nurse")) {
            JLabel nurseLabel = new JLabel("\n\nNurse Actions:");
            nurseLabel.setBounds(590, 30, 75, 40);
            nurseLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
            typePanel.add(nurseLabel);

            JButton treatsButton = new JButton("Currently Treating");
            treatsButton.setBounds(400, 30, 175, 40);
            treatsButton.addActionListener(e -> addTreat(userID));
            typePanel.add(treatsButton);

        } else if(user.getCurrentUserType().equals("Doctor")) {
            JLabel docLabel = new JLabel("\n\nDoctor Actions:");
            docLabel.setBounds(590, 30, 75, 40);
            docLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
            typePanel.add(docLabel);
        }

        //GUI Formatting
        buttonsPanel.add(typePanel, BorderLayout.CENTER);
        buttonsPanel.add(logPanel, BorderLayout.SOUTH);
        contentPanel.add(resultColsArea, BorderLayout.WEST);
        contentPanel.add(resultTextArea, BorderLayout.CENTER);
        contentPanel.add(buttonsPanel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void dashboardView() {
        final String ID = "root";
		final String PW = "COSC*ncm6n";
		final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

		try (Connection con = DriverManager.getConnection(SERVER, ID, PW);){
			System.out.println("Connection successful");

			String sql = "SELECT * FROM caretrackdb.Staff WHERE StaffId = '" + userID + "'";
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                StringBuilder resultCols = new StringBuilder("\n\nStaff Profile:\n\n");
                StringBuilder resultText = new StringBuilder("\n\n\n\n");

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    resultCols.append(metaData.getColumnName(i)).append("\n\n");
                }
                resultCols.append("\n\n");

                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        resultText.append(resultSet.getString(i)).append("\n\n");
                    }
                    resultText.append("\n");
                }

                resultColsArea.setText(resultCols.toString());
                resultTextArea.setText(resultText.toString());
            } 
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private static void logout(Dashboard c) {
        System.out.println("Logging Out...");
        new loginGUI().setVisible(true);
        c.dispose();
    }

    private static void updatePerson() {
        System.out.println("Update Person loading...");
        new UpdatePerson();
    }

    private static void searchPerson() {
        System.out.println("Searching Person...");
        JFrame temp = new SearchFrame("Search Person");
        temp.setVisible(true);
    }

    private static void deletePerson() {
        System.out.println("Deleting Person...");
        JFrame temp = new  DeletePerson("Delete Person");
        temp.setVisible(true);
    }

    private static void createPerson() {
        //JFrame temp = new EmployeeRegistrationFrame("Employee Registration");
        //temp.setVisible(true);
      System.out.println("Creating Person...");
    }

    //Management Actions
    private static void addVisit() {
        System.out.println("Adding Visit...");
        JFrame temp = new VisitFrame();
        temp.setVisible(true);
    }
    private static void addAllow() {
        System.out.println("Adding Allowance...");
        JFrame temp = new AllowFrame();
        temp.setVisible(true);
    }
    private static void editRoom() {
        System.out.println("Editing Room...");
        JFrame temp = new RoomFrame();
        temp.setVisible(true);
    }

    //Nurse Actions
    private static void addTreat(String ID) {
        System.out.println("Adding Treating...");
        JFrame temp = new NurseFrame(ID);
        temp.setVisible(true);
    }


    //Doctor Actions

}