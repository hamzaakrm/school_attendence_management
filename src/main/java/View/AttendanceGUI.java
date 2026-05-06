package View;

import Model.Attendance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AttendanceGUI extends JFrame implements ActionListener, PropertyChangeListener {

    private Attendance attendance;
    private JTextField nameField;
    private JComboBox<String> studentBox;
    private JTextArea outputArea;
    private JLabel sessionLabel;
    private JButton addButton, presentButton, absentButton, nextSessionButton;

    public AttendanceGUI() {
        attendance = new Attendance();
        attendance.addPropertyChangeListener(this);

        setTitle("Student Attendance Manager ver 1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        /*
         ***Create addPanel at top of gui
         */
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        addPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));

        nameField = new JTextField(15);
        addButton = new JButton("Add Student");
        addButton.addActionListener(this);

        addPanel.add(new JLabel("Name:"));
        addPanel.add(nameField);
        addPanel.add(addButton);

        /*
         ***Create actionPanel in middle of gui
         */
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Record attendance at end of each teaching session"));

        /*
         ***Student dropdown panel
         */
        studentBox = new JComboBox<>();
        studentBox.setMaximumRowCount(10);
        studentBox.setPreferredSize(new Dimension(300, 30));
        studentBox.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel studentSelectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        studentSelectPanel.add(new JLabel("Select Student:"));
        studentSelectPanel.add(studentBox);

        /*
         ***Present and absent panels
         */
        presentButton = new JButton("Mark Present");
        absentButton = new JButton("Mark Absent");

        Dimension buttonSize = new Dimension(120, 30);
        presentButton.setPreferredSize(buttonSize);
        absentButton.setPreferredSize(buttonSize);

        presentButton.addActionListener(this);
        absentButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonPanel.add(presentButton);
        buttonPanel.add(absentButton);

        /*
         ***next session panel
         */
        nextSessionButton = new JButton("Next Session");
        nextSessionButton.addActionListener(this);

        JPanel nextSessionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        nextSessionPanel.add(nextSessionButton);

        /*
         ***Add panels to ActionPanel
         */
        actionPanel.add(studentSelectPanel);
        actionPanel.add(buttonPanel);
        actionPanel.add(nextSessionPanel);

        /*
         ***create summaryPanel at bottom of gui
         */
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Attendance Summary"));

        sessionLabel = new JLabel("Session Number: 1");
        JPanel sessionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        sessionPanel.add(sessionLabel);
        actionPanel.add(sessionPanel, 0);

        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        summaryPanel.add(scrollPane, BorderLayout.CENTER);

        /*
        ***add all panels to the gui
         */
        add(addPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);
        add(summaryPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
     ***Handle user interactions by responding to button clicks
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index;
        if (e.getSource() == addButton) {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                attendance.addStudent(name);
                nameField.setText("");
            }
        }
        if (e.getSource() == presentButton) {
            index = studentBox.getSelectedIndex();
            if (index >= 0) attendance.markPresent(index);
        }
        if (e.getSource() == absentButton) {
            index = studentBox.getSelectedIndex();
            if (index >= 0) attendance.markAbsent(index);
        }
        if (e.getSource() == nextSessionButton) {
            attendance.nextSession();
        }
    }

    /*
    ***Update gui depending on model property changes
    */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "students":
                studentBox.removeAllItems();
                for (String name : attendance.getStudentNames()) {
                    studentBox.addItem(name);
                }
                break;

            case "session":
                sessionLabel.setText("Session number: " + attendance.getTotalSessionsHeld());
                outputArea.setText(attendance.getSummary());
                break;
        }
    }

    public static void main(String[] args) {
        new AttendanceGUI();
    }
}
