package com.example.studenttracker;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final List<Student> studentList = new ArrayList<>();

    @GetMapping
    public List<Student> getAllStudents() {
        return studentList;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        // Auto-calculate grade
        if (student.getMarks() >= 90) {
            student.setGrade("A");
        } else if (student.getMarks() >= 75) {
            student.setGrade("B");
        } else if (student.getMarks() >= 60) {
            student.setGrade("C");
        } else {
            student.setGrade("D");
        }

        // Auto-assign pass/fail status
        student.setStatus(student.getMarks() >= 40 ? "Pass" : "Fail");

        studentList.add(student);

        // Send metrics to Graphite
        sendMetricToGraphite("marks." + formatKey(student.getName()), student.getMarks());
        sendMetricToGraphite("attendance." + formatKey(student.getName()), student.getAttendance());

        // Optional: send class average
        int avgMarks = studentList.stream().mapToInt(Student::getMarks).sum() / studentList.size();
        sendMetricToGraphite("class.avg_marks", avgMarks);

        return student;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        boolean removed = studentList.removeIf(s -> s.getId() == id);
        return removed ? "Student removed" : "Student not found";
    }

    @GetMapping("/")
    public String home() {
        return "📚 Student Tracker Application is running!";
    }

    // Utility: Send metrics to Graphite
    private void sendMetricToGraphite(String metricName, int value) {
        try (Socket socket = new Socket("localhost", 2003);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            long timestamp = System.currentTimeMillis() / 1000;
            String fullMetric = String.format("studenttracker.%s %d %d%n", metricName, value, timestamp);
            writer.print(fullMetric);
            writer.flush();
            System.out.println("✅ Sent metric to Graphite: " + fullMetric);
        } catch (Exception e) {
            System.err.println("❌ Failed to send metric to Graphite: " + e.getMessage());
        }
    }

    // Utility: Format name into a valid Graphite key (e.g., replace spaces)
    private String formatKey(String input) {
        return input.trim().replaceAll("\\s+", "_").toLowerCase();
    }
}
