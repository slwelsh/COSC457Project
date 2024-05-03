package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SearchFrame extends JFrame {
    private JTextField IdField;
    private JTextArea resultTextArea;
    private JTextArea resultColsArea;

    public SearchFrame(String title) {
        super(title);
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        headerPanel.setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 3));

        headerPanel.add(new JLabel("Person ID:"));
        IdField = new JTextField(8);
        headerPanel.add(IdField);

        String[] personOptions = {"Staff", "Patient", "Guest"};
        JComboBox<String> jobComboBox = new JComboBox<>(personOptions);
        headerPanel.add(new JLabel("Person Type:"));
        headerPanel.add(jobComboBox);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = jobComboBox.getSelectedItem().toString();
                searchPerson(type);
            }
        });
        headerPanel.add(searchButton);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        resultColsArea = new JTextArea();
        resultColsArea.setEditable(false);

        contentPanel.add(resultColsArea);
        contentPanel.add(resultTextArea);

        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        
        setSize(500, 300);
        setVisible(true);
    }

    private void searchPerson(String type) {
        String Id = IdField.getText();

        if (Id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Staff Id, Patient SSN, or Guest Id");
            return;
        }

        final String user = "admin";
    	final String pass = "COSC*ncm6n";
    	final String url = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            String sql;
            if (type.equals("Staff"))
                sql = "SELECT * FROM caretrackdb.Staff WHERE StaffId = '" + Id + "'";
            else if (type.equals("Patient"))
                sql = "SELECT * FROM caretrackdb.Patient WHERE SSN = '" + Id + "'";
            else 
                sql = "SELECT * FROM caretrackdb.Guest WHERE GuestId = '" + Id + "'";
            System.out.println(sql);
            try (Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
                    StringBuilder resultCols = new StringBuilder("\n\nPerson Profile:\n\n");
                    StringBuilder resultText = new StringBuilder("\n\n\n\n");
                
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        resultCols.append(metaData.getColumnName(i)).append("\n");
                    }
                    resultCols.append("\n\n");

                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            resultText.append(resultSet.getString(i)).append("\n");
                        }
                        resultText.append("\n");
                    }
                    resultColsArea.setText(resultCols.toString());
                    resultTextArea.setText(resultText.toString());
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database");
        }
    }
}