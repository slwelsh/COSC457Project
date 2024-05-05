package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePerson extends JFrame {
    private JComboBox<String> personTypeComboBox;

    public CreatePerson() {
        setTitle("Create Person");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel personTypeLabel = new JLabel("Person Type:");
        String[] personTypes = {"", "Patient", "Guest", "Nurse", "Doctor", "Management"};
        personTypeComboBox = new JComboBox<>(personTypes);
        JButton createButton = new JButton("Create");

        panel.add(personTypeLabel);
        panel.add(personTypeComboBox);
        panel.add(new JLabel());
        panel.add(createButton);

        add(panel);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String personType = (String) personTypeComboBox.getSelectedItem();
                if ("Patient".equals(personType)) {
                    createPatient();
                } else if ("Guest".equals(personType)) {
                    createGuest();
                } else if ("Nurse".equals(personType) || "Doctor".equals(personType) || "Management".equals(personType)) {
                    createStaff(personType);
                }
            }
        });
    }

    private void createPatient() {
        JFrame patientManagerFrame = new JFrame("Add Patient");
        PatientManager patientManager = new PatientManager();
        patientManagerFrame.add(patientManager);
        patientManagerFrame.setSize(400, 400);
        patientManagerFrame.setLocationRelativeTo(null);
        patientManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        patientManagerFrame.setVisible(true);
    }

    private void createGuest() {
        JFrame guestManagerFrame = new JFrame("Add Guest");
        GuestManager guestManager = new GuestManager();
        guestManagerFrame.add(guestManager);
        guestManagerFrame.setSize(400, 400);
        guestManagerFrame.setLocationRelativeTo(null);
        guestManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        guestManagerFrame.setVisible(true);
    }

    // Update the createStaff method in CreatePerson class
    private void createStaff(String staffType) {
        JFrame staffManagerFrame = new JFrame("Add " + staffType);
        StaffManager staffManager = new StaffManager();
        staffManagerFrame.add(staffManager);
        staffManagerFrame.setSize(400, 500);
        staffManagerFrame.setLocationRelativeTo(null);
        staffManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        staffManagerFrame.setVisible(true);
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CreatePerson().setVisible(true);
            }
        });
    }
}
