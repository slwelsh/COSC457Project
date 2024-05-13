package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

class SearchFrame extends JFrame {
    private JTextField IdField;

    private JTable profileTable;
    private JTable specificsTable;

    public SearchFrame(String title) {
        super(title);
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        headerPanel.setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1));

        headerPanel.add(new JLabel("Person ID:"));
        IdField = new JTextField(8);
        headerPanel.add(IdField);

        String[] personOptions = { "", "Staff", "Patient", "Guest" };
        JComboBox<String> jobComboBox = new JComboBox<>(personOptions);
        headerPanel.add(new JLabel("Person Type:"));
        headerPanel.add(jobComboBox);

        DefaultTableModel model = new DefaultTableModel();
        DefaultTableModel model2 = new DefaultTableModel();

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = jobComboBox.getSelectedItem().toString();
                model.setColumnCount(0);

                model.addColumn("Person ID");
                model.addColumn("Name");
                if (type.equals("Staff")) {
                    model.addColumn("SSN");
                    model.addColumn("DOB");
                    model.addColumn("Address");
                    model.addColumn("Phone #");
                    model.addColumn("Salary");
                    model.addColumn("Schedule");

                    addStaffDetails(model2);
                } else if (type.equals("Patient")) {
                    model.addColumn("DOB");
                    model.addColumn("Gender");
                    model.addColumn("Start Date");
                    model.addColumn("Room #");
                    model.addColumn("Nurse ID");

                    addPatientDetails(model2);
                } else {
                    model.addColumn("Relationship");
                    model.addColumn("Phone #");
                    model.addColumn("Email");
                    model.addColumn("Insurance");
                    model.addColumn("Address");
                    model.addColumn("Patient ID");

                    addGuestDetails(model2);
                }

                searchPerson(type);
            }
        });
        headerPanel.add(searchButton);

        profileTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(profileTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        specificsTable = new JTable(model2);
        JScrollPane scrollPane2 = new JScrollPane(specificsTable);
        contentPanel.add(scrollPane2, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        setSize(700, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void searchPerson(String type) {
        String Id = IdField.getText();
        DefaultTableModel model = (DefaultTableModel) profileTable.getModel();
        model.setRowCount(0);

        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            String sql;
            if (type.equals("Staff")) {
                sql = "SELECT * FROM caretrackdb.Staff";
                if (!Id.equals(""))
                    sql += " WHERE StaffId = '" + Id + "'";
            } else if (type.equals("Patient")) {
                sql = "SELECT * FROM caretrackdb.Patient";
                if (!Id.equals(""))
                    sql += " WHERE SSN = '" + Id + "'";
            } else {
                sql = "SELECT * FROM caretrackdb.Guest";
                if (!Id.equals(""))
                    sql += " WHERE GuestId = '" + Id + "'";
            }

            try (Statement statement = con.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    if (type.equals("Staff")) {
                        String pid = resultSet.getString("StaffId");
                        String name = resultSet.getString("FName") + " " + resultSet.getString("LName");
                        String ssn = resultSet.getString("SSN");
                        String dob = resultSet.getString("DOB");
                        String add = resultSet.getString("Address");
                        String num = resultSet.getString("Number");
                        String sal = resultSet.getString("Salary");
                        String sched = resultSet.getString("Schedule");

                        model.addRow(new Object[] { pid, name, ssn, dob, add, num, sal, sched });
                    } else if (type.equals("Patient")) {
                        String pid = resultSet.getString("SSN");
                        String name = resultSet.getString("FName") + " " + resultSet.getString("LName");
                        String dob = resultSet.getString("DOB");
                        String gend = resultSet.getString("Gender");
                        String start = resultSet.getString("StartDate");
                        String room = resultSet.getString("RoomNo");
                        String nurse = resultSet.getString("NurseId");

                        model.addRow(new Object[] { pid, name, dob, gend, start, room, nurse });
                    } else {
                        String pid = resultSet.getString("GuestId");
                        String name = resultSet.getString("Name");
                        String rel = resultSet.getString("Relationship");
                        String num = resultSet.getString("Number");
                        String email = resultSet.getString("Email");
                        String insur = resultSet.getString("Insurance");
                        String add = resultSet.getString("Address");
                        String patient = resultSet.getString("PSSN");

                        model.addRow(new Object[] { pid, name, rel, num, email, insur, add, patient });

                    }

                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        }
    }

    private void addStaffDetails(DefaultTableModel model) {
        String ID = IdField.getText();
        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(url, user, pass)) {

            try (Statement statement = con.createStatement()) {
                String query = "SELECT * FROM caretrackdb.Nurse WHERE StaffId = '" + ID + "'";

                ResultSet resultSet = statement.executeQuery(query);
                model.setColumnCount(0);
                model.setRowCount(0);

                // check if nurse
                if (resultSet.next()) {
                    model.addColumn("Patient ID");
                    model.addColumn("Patient Name");

                    query = "SELECT * FROM caretrackdb.Patient WHERE NurseId = '" + ID + "'";
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        String pid = resultSet.getString("SSN");
                        String name = resultSet.getString("FName") + " " + resultSet.getString("LName");
                        model.addRow(new Object[] { pid, name });
                    }
                } else {
                    // check if doctor
                    query = "SELECT * FROM caretrackdb.Doctor WHERE StaffId = '" + ID + "'";
                    resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        model.addColumn("Patient ID");
                        model.addColumn("Patient Diagnosis");

                        if (ID.equals(""))
                            query = "SELECT * FROM caretrackdb.Diagnosis";
                        else
                            query = "SELECT * FROM caretrackdb.Diagnosis WHERE DocId = '" + ID + "'";
                        resultSet = statement.executeQuery(query);
                        while (resultSet.next()) {
                            String pid = resultSet.getString("PSSN");
                            String condname = resultSet.getString("CondName");

                            model.addRow(new Object[] { pid, condname });
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addPatientDetails(DefaultTableModel model) {
        String ID = IdField.getText();
        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        model.setColumnCount(0);
        model.setRowCount(0);

        model.addColumn("Patient ID");
        model.addColumn("Doctor ID");
        model.addColumn("Diagnosis");
        model.addColumn("Medication");

        try (Connection con = DriverManager.getConnection(url, user, pass)) {

            try (Statement statement = con.createStatement()) {
                String query;
                if (ID.equals(""))
                    query = "SELECT * FROM caretrackdb.Diagnosis";
                else
                    query = "SELECT * FROM caretrackdb.Diagnosis WHERE PSSN = '" + ID + "'";

                ResultSet resultSet = statement.executeQuery(query);

                String pid = "";
                String doc = "";
                String condname = "";
                while (resultSet.next()) {
                    pid = resultSet.getString("PSSN");
                    doc = resultSet.getString("DocId");
                    condname = resultSet.getString("CondName");

                    try (Statement statement2 = con.createStatement()) {
                        String query2 = "SELECT * FROM caretrackdb.MedCondition WHERE Name = '" + condname + "'";
                        ResultSet resultSet2 = statement2.executeQuery(query2);

                        while (resultSet2.next()) {
                            String med = resultSet2.getString("Medication");
                            model.addRow(new Object[] { pid, doc, condname, med });
                        }
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addGuestDetails(DefaultTableModel model) {
        String ID = IdField.getText();
        final String user = "admin";
        final String pass = "COSC*ncm6n";
        final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        model.setColumnCount(0);
        model.setRowCount(0);

        model.addColumn("Date");
        model.addColumn("Guest ID");
        model.addColumn("Reason");

        try (Connection con = DriverManager.getConnection(url, user, pass)) {

            try (Statement statement = con.createStatement()) {
                String query;
                if (ID.equals(""))
                    query = "SELECT * FROM caretrackdb.Visits";
                else
                    query = "SELECT * FROM caretrackdb.Visits WHERE GId = '" + ID + "'";
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String date = resultSet.getString("Date");
                    String gid = resultSet.getString("GId");
                    String reason = resultSet.getString("Reason");
                    model.addRow(new Object[] { date, gid, reason });
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}