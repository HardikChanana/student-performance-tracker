package com.example.studenttracker;

public class Student {
    private int id;
    private String name;
    private int marks;
    private String email;
    private String course;
    private String grade;
    private int attendance;
    private String status;
    private String dateOfBirth;

    public Student() {}

    // 3-parameter constructor for older test cases
    public Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    // Full constructor
    public Student(int id, String name, int marks, String email, String course,
                   String grade, int attendance, String status, String dateOfBirth) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.email = email;
        this.course = course;
        this.grade = grade;
        this.attendance = attendance;
        this.status = status;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
       return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
