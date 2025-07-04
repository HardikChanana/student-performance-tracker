package com.example.studenttracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    @Test
    void testStudentCreation() {
        Student student = new Student(1, "Asha", 85);
        assertEquals(1, student.getId());
        assertEquals("Asha", student.getName());
        assertEquals(85, student.getMarks());
    }

    @Test
    void testSettersAndGetters() {
        Student student = new Student();
        student.setId(2);
        student.setName("Ravi");
        student.setMarks(92);

        assertEquals(2, student.getId());
        assertEquals("Ravi", student.getName());
        assertEquals(92, student.getMarks());
    }
}
