package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Student.
 * @author ***put your student number/s here***
 */
public class StudentTest {
    
    private Student student;
    
    public StudentTest() {
    }
    
    @BeforeEach
    public void setUp() {
        student = new Student("Test Student");
    }

    /**
     * Test of getAttendancePercentage method, of class Student.
     */
    @Test
    public void testGetAttendancePercentage() {
        // Test no sessions - should return 0.0
        assertEquals(0.0, student.getAttendancePercentage(), 0.01);
        
        // Test 100% attendance (2 present out of 2)
        student.markPresent();
        student.markPresent();
        assertEquals(100.0, student.getAttendancePercentage(), 0.01);
        
        // Test 50% attendance (2 present out of 4)
        Student student2 = new Student("Another Student");
        student2.markPresent();
        student2.markPresent();
        student2.markAbsent();
        student2.markAbsent();
        assertEquals(50.0, student2.getAttendancePercentage(), 0.01);
        
        // Test 75% attendance (3 present out of 4)
        Student student3 = new Student("Third Student");
        student3.markPresent();
        student3.markPresent();
        student3.markPresent();
        student3.markAbsent();
        assertEquals(75.0, student3.getAttendancePercentage(), 0.01);
    }

    /**
     * Test of getAbsences method, of class Student.
     */
    @Test
    public void testGetAbsences() {
        // Test no sessions
        assertEquals(0, student.getAbsences());
        
        // Test with only present marks
        student.markPresent();
        student.markPresent();
        assertEquals(0, student.getAbsences());
        
        // Test with mix of present and absent
        Student student2 = new Student("Another Student");
        student2.markPresent();
        student2.markAbsent();
        student2.markAbsent();
        student2.markPresent();
        assertEquals(2, student2.getAbsences());
        
        // Test with only absent marks
        Student student3 = new Student("Third Student");
        student3.markAbsent();
        student3.markAbsent();
        student3.markAbsent();
        assertEquals(3, student3.getAbsences());
    }

    /**
     * Test of getAttendanceStatus method, of class Student.
     */
    @Test
    public void testGetAttendanceStatus() {
        // Test no sessions
        assertEquals("No Record", student.getAttendanceStatus());
        
        // Test Good status (100%)
        Student goodStudent = new Student("Good Student");
        goodStudent.markPresent();
        goodStudent.markPresent();
        goodStudent.markPresent();
        goodStudent.markPresent();
        goodStudent.markPresent(); // 5/5 = 100%
        assertEquals("Good", goodStudent.getAttendanceStatus());
        
        // Test Good status (exactly 80%)
        Student borderlineGood = new Student("Borderline Good");
        borderlineGood.markPresent(); // 1
        borderlineGood.markPresent(); // 2
        borderlineGood.markPresent(); // 3
        borderlineGood.markPresent(); // 4
        borderlineGood.markAbsent();  // 5 - 4/5 = 80%
        assertEquals("Good", borderlineGood.getAttendanceStatus());
        
        // Test Report to HAS-Specs status (75%)
        Student reportStudent = new Student("Report Student");
        reportStudent.markPresent(); // 1
        reportStudent.markPresent(); // 2
        reportStudent.markPresent(); // 3
        reportStudent.markAbsent();  // 4 - 3/4 = 75%
        assertEquals("Report to HAS-Specs", reportStudent.getAttendanceStatus());
        
        // Test Report to HAS-Specs status (0%)
        Student zeroStudent = new Student("Zero Student");
        zeroStudent.markAbsent();
        zeroStudent.markAbsent();
        zeroStudent.markAbsent();
        assertEquals("Report to HAS-Specs", zeroStudent.getAttendanceStatus());
    }
}