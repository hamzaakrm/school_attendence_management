package Model;

/*
 * Class for an individual student
 * Encapsulates attendance-related data and calculations
 */
public class Student {

    private String name;
    private int sessionsAttended;
    private int totalSessions;

    public Student(String name) {
        this.name = name;
        this.sessionsAttended = 0;
        this.totalSessions = 0;
    }

    public String getName() {
        return name;
    }

    /*
     * Records present for a session by incrementing both attended sessions and total sessions
     */
    public void markPresent() {
        sessionsAttended++;
        totalSessions++;
    }

    /*
     * Records absent for a session by incrementing total sessions only
     */
    public void markAbsent() {
        totalSessions++;
    }

    /*
     * Calculate attendance percentage
     * Returns percentage of sessions attended (0.0 to 100.0)
     * Returns 0.0 if no sessions have been held
     */
    public double getAttendancePercentage() {
        if (totalSessions == 0) {
            return 0.0;
        }
        return (sessionsAttended * 100.0) / totalSessions;
    }

    /*
     * Calculate number of absences
     * Returns difference between total sessions and attended sessions
     */
    public int getAbsences() {
        return totalSessions - sessionsAttended;
    }

    /*
     * Determine attendance status based on percentage threshold
     * Returns "Good" if attendance >= 80%
     * Returns "Report to HAS-Specs" if attendance < 80%
     * Returns "No Record" if no sessions have been held
     */
    public String getAttendanceStatus() {
        if (totalSessions == 0) {
            return "No Record";
        }
        
        double percentage = getAttendancePercentage();
        
        if (percentage >= 80.0) {
            return "Good";
        } else {
            return "Report to HAS-Specs";
        }
    }
}