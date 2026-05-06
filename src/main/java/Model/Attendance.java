package Model;
/*
Class tracks students and sessions
Provide calculations and summaries for individual and group attendance
Provide PropertyChangeSupport to notify registered listeners when the model’s data changes
*/

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Attendance {

    private ArrayList<Student> students;
    private PropertyChangeSupport pcs;
    private int currentSession;

    public Attendance() {
        students = new ArrayList<>();
        pcs = new PropertyChangeSupport(this);
        currentSession = 1;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void addStudent(String name) {
        students.add(new Student(name));
        pcs.firePropertyChange("students", null, students.size());
    }

    public void markPresent(int index) {
        students.get(index).markPresent();
        pcs.firePropertyChange("attendance", null, index);
    }

    public void markAbsent(int index) {
        students.get(index).markAbsent();
        pcs.firePropertyChange("attendance", null, index);
    }

    public void nextSession() {
        currentSession++;
        pcs.firePropertyChange("session", null, currentSession);
    }

    public int getTotalSessionsHeld() {
        return currentSession;
    }

    /*
     *Used by AttendanceGUI to refresh the student drop-down list
     *when the model reports a student list change
     */
    public String[] getStudentNames() {
        String[] names = new String[students.size()];
        for (int i = 0; i < students.size(); i++) {
            names[i] = students.get(i).getName();
        }
        return names;
    } 

    /*
    build String sb that contains summary for each student
    */
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total teaching sessions held for this class group: ").append(getTotalSessionsHeld()-1).append("\n\n");

        for (Student s : students) {
            sb.append(s.getName())
                    .append(" | Attendance: ").append(String.format("%.1f", s.getAttendancePercentage()))
                    .append("% | Absent: ").append(s.getAbsences())
                    .append(" | Status: ").append(s.getAttendanceStatus())
                    .append("\n");
        }     
        return sb.toString();        
    }
}
