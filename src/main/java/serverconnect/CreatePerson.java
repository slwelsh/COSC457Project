package serverconnect;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class CreatePerson extends JFrame {
//     private JComboBox<String> personTypeComboBox;
//     private JPanel panel;

//     public CreatePerson() {
//         setTitle("Create Person");
//         setSize(300, 150);
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         setLocationRelativeTo(null);

//         panel = new JPanel(new GridLayout(3, 2));

//         JLabel personTypeLabel = new JLabel("Person Type:");
//         String[] personTypes = {"", "Patient", "Guest", "Nurse", "Doctor", "Management"};
//         personTypeComboBox = new JComboBox<>(personTypes);
//         JButton createButton = new JButton("Create");

//         panel.add(personTypeLabel);
//         panel.add(personTypeComboBox);
//         panel.add(new JLabel());
//         panel.add(createButton);

//         add(panel);

//         createButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 String personType = (String) personTypeComboBox.getSelectedItem();
//                 if ("Patient".equals(personType)) {
//                     createPatient();
//                 } else if ("Guest".equals(personType)) {
//                     createGuest();
//                 } else if ("Nurse".equals(personType)) {
//                     createNurse();
//                 } else if ("Doctor".equals(personType)) {
//                     createDoctor();
//                 } else if ("Management".equals(personType)) {
//                     createManagement();
//                 }
//             }
//         });
//     }

//     private void createPatient() {
//         JFrame patientManagerFrame = new JFrame("Add Patient");
//         PatientManager patientManager = new PatientManager();
//         patientManagerFrame.add(patientManager);
//         patientManagerFrame.setSize(400, 400);
//         patientManagerFrame.setLocationRelativeTo(null);
//         patientManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         patientManagerFrame.setVisible(true);
//     }

//     private void createGuest() {
//         JFrame guestManagerFrame = new JFrame("Add Guest");
//         GuestManager guestManager = new GuestManager();
//         guestManagerFrame.add(guestManager);
//         guestManagerFrame.setSize(400, 400);
//         guestManagerFrame.setLocationRelativeTo(null);
//         guestManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         guestManagerFrame.setVisible(true);
//     }

//     private void createNurse() {
//         JFrame nurseManagerFrame = new JFrame("Add Nurse");
//         NurseManager nurseManager = new NurseManager(panel);
//         nurseManagerFrame.add(nurseManager);
//         nurseManagerFrame.setSize(400, 500);
//         nurseManagerFrame.setLocationRelativeTo(null);
//         nurseManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         nurseManagerFrame.setVisible(true);
//     }

//     private void createDoctor() {
//         JFrame doctorManagerFrame = new JFrame("Add Doctor");
//         DoctorManager doctorManager = new DoctorManager(panel);
//         doctorManagerFrame.add(doctorManager);
//         doctorManagerFrame.setSize(400, 500);
//         doctorManagerFrame.setLocationRelativeTo(null);
//         doctorManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         doctorManagerFrame.setVisible(true);
//     }

//     private void createManagement() {
//         JFrame managementManagerFrame = new JFrame("Add Management Staff");
//         ManagementManager managementManager = new ManagementManager(panel);
//         managementManagerFrame.add(managementManager);
//         managementManagerFrame.setSize(400, 500);
//         managementManagerFrame.setLocationRelativeTo(null);
//         managementManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         managementManagerFrame.setVisible(true);
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             public void run() {
//                 new CreatePerson().setVisible(true);
//             }
//         });
//     }
// }

// // import javax.swing.*;
// // import java.awt.*;
// // import java.awt.event.ActionEvent;
// // import java.awt.event.ActionListener;

// // public class CreatePerson extends JFrame {
// //     private JComboBox<String> personTypeComboBox;

// //     public CreatePerson() {
// //         setTitle("Create Person");
// //         setSize(300, 200);
// //         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
// //         setLocationRelativeTo(null);

// //         JPanel panel = new JPanel(new GridLayout(4, 2));

// //         JLabel personTypeLabel = new JLabel("Person Type:");
// //         String[] personTypes = {"", "Patient", "Guest", "Nurse", "Doctor", "Management"};
// //         personTypeComboBox = new JComboBox<>(personTypes);
// //         JButton createButton = new JButton("Create");

// //         panel.add(personTypeLabel);
// //         panel.add(personTypeComboBox);
// //         panel.add(new JLabel());
// //         panel.add(new JLabel());
// //         panel.add(new JLabel());
// //         panel.add(createButton);

// //         add(panel);

// //         createButton.addActionListener(new ActionListener() {
// //             @Override
// //             public void actionPerformed(ActionEvent e) {
// //                 String personType = (String) personTypeComboBox.getSelectedItem();
// //                 if ("Patient".equals(personType) || "Guest".equals(personType)) {
// //                     createPatientOrGuest(personType);
// //                 } else if ("Nurse".equals(personType) || "Doctor".equals(personType) || "Management".equals(personType)) {
// //                     createStaff(personType);
// //                 }
// //             }
// //         });
// //     }

// //     private void createPatientOrGuest(String personType) {
// //         // Handle creating Patient or Guest if needed
// //         JOptionPane.showMessageDialog(this, "Creating " + personType);
// //     }

// //     private void createStaff(String staffType) {
// //         JFrame staffManagerFrame = new JFrame("Add " + staffType);
// //         CombinedManager staffManager = new CombinedManager();
// //         staffManagerFrame.add(staffManager);
// //         staffManagerFrame.setSize(400, 500);
// //         staffManagerFrame.setLocationRelativeTo(null);
// //         staffManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
// //         staffManagerFrame.setVisible(true);
// //     }

// //     public static void main(String[] args) {
// //         SwingUtilities.invokeLater(new Runnable() {
// //             public void run() {
// //                 new CreatePerson().setVisible(true);
// //             }
// //         });
// //     }
// // }


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
