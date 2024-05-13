package serverconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePerson extends JFrame {
    private JComboBox<String> personTypeComboBox;
    private JPanel panel;

    public CreatePerson() {
        setTitle("Create Person");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(3, 2));

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
                switch (personType) {
                    case "Patient":
                        createPatient();
                        break;
                    case "Guest":
                        createGuest();
                        break;
                    case "Nurse":
                        createNurse();
                        break;
                    case "Doctor":
                        createDoctor();
                        break;
                    case "Management":
                        createManagement();
                        break;
                    default:
                        JOptionPane.showMessageDialog(CreatePerson.this, "Please select a person type.");
                }
            }
        });
    }

    private void createPatient() {
        JFrame patientManagerFrame = new JFrame("Add Patient");
        PatientManager patientManager = new PatientManager();
        patientManagerFrame.add(patientManager);
        configureFrame(patientManagerFrame);
    }

    private void createGuest() {
        JFrame guestManagerFrame = new JFrame("Add Guest");
        GuestManager guestManager = new GuestManager();
        guestManagerFrame.add(guestManager);
        configureFrame(guestManagerFrame);
    }

    private void createNurse() {
        JFrame nurseManagerFrame = new JFrame("Add Nurse");
        NurseManager nurseManager = new NurseManager(panel);
        nurseManagerFrame.add(nurseManager);
        configureFrame(nurseManagerFrame);
    }

    private void createDoctor() {
        JFrame doctorManagerFrame = new JFrame("Add Doctor");
        DoctorManager doctorManager = new DoctorManager(panel);
        doctorManagerFrame.add(doctorManager);
        configureFrame(doctorManagerFrame);
    }

    private void createManagement() {
        JFrame managementManagerFrame = new JFrame("Add Management Staff");
        ManagementManager managementManager = new ManagementManager(panel);
        managementManagerFrame.add(managementManager);
        configureFrame(managementManagerFrame);
    }

    private void configureFrame(JFrame frame) {
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CreatePerson().setVisible(true);
            }
        });
    }
}