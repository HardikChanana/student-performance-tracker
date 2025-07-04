package com.example.studenttracker;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
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
        if (student.getMarks() >= 90) {
            student.setGrade("A");
        } else if (student.getMarks() >= 75) {
            student.setGrade("B");
        } else if (student.getMarks() >= 60) {
            student.setGrade("C");
        } else {
            student.setGrade("D");
        }

        student.setStatus(student.getMarks() >= 40 ? "Pass" : "Fail");
        studentList.add(student);
        return student;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        boolean removed = studentList.removeIf(s -> s.getId() == id);
        return removed ? "Student removed" : "Student not found";
    }

    @GetMapping("/")
    public String home() {
        return "Student Tracker Application is running!";
    }

    @GetMapping("/analytics/class/{course}")
    public Map<String, Object> getClassAnalytics(@PathVariable String course) {
        List<Student> classStudents = studentList.stream()
                .filter(s -> course.equalsIgnoreCase(s.getCourse()))
                .toList();

        if (classStudents.isEmpty()) {
            return Map.of("error", "No students found in class: " + course);
        }

        double average = classStudents.stream().mapToInt(Student::getMarks).average().orElse(0.0);
        long passCount = classStudents.stream().filter(s -> "Pass".equalsIgnoreCase(s.getStatus())).count();
        long failCount = classStudents.size() - passCount;
        Student topper = classStudents.stream().max(Comparator.comparingInt(Student::getMarks)).orElse(null);

        Map<String, Object> stats = new HashMap<>();
        stats.put("class", course);
        stats.put("totalStudents", classStudents.size());
        stats.put("averageMarks", average);
        stats.put("passCount", passCount);
        stats.put("failCount", failCount);
        stats.put("topper", topper);
        return stats;
    }

    @GetMapping("/analytics/student/{id}/class")
    public Map<String, Object> getStudentClassAnalytics(@PathVariable int id) {
        Optional<Student> optStudent = studentList.stream().filter(s -> s.getId() == id).findFirst();
        if (optStudent.isEmpty()) return Map.of("error", "Student not found");

        Student student = optStudent.get();
        String course = student.getCourse();

        List<Student> classStudents = studentList.stream()
                .filter(s -> course.equalsIgnoreCase(s.getCourse()))
                .sorted(Comparator.comparingInt(Student::getMarks).reversed())
                .toList();

        int rank = 1;
        for (Student s : classStudents) {
            if (s.getId() == id) break;
            rank++;
        }

        double classAverage = classStudents.stream().mapToInt(Student::getMarks).average().orElse(0.0);

        Map<String, Object> stats = new HashMap<>();
        stats.put("studentId", student.getId());
        stats.put("studentName", student.getName());
        stats.put("course", course);
        stats.put("marks", student.getMarks());
        stats.put("classAverage", classAverage);
        stats.put("rankInClass", rank);
        stats.put("totalStudentsInClass", classStudents.size());
        return stats;
    }
}
