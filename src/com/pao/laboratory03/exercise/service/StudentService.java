package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;

import java.util.*;

public class StudentService {

    private static StudentService instance;
    private List<Student> students;

    private StudentService() {
        students = new ArrayList<>();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(String name, int age) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                throw new RuntimeException("Studentul există deja: " + name);
            }
        }
        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new StudentNotFoundException("Studentul nu a fost găsit: " + name);
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        Student s = findByName(studentName);
        s.addGrade(subject, grade);
    }

    public void printAllStudents() {
        for (Student s : students) {
            System.out.println(s + " -> " + s.getGrades());
        }
    }

    public void printTopStudents() {
        students.stream()
                .sorted((a, b) -> Double.compare(b.getAverage(), a.getAverage()))
                .forEach(System.out::println);
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, List<Double>> temp = new HashMap<>();
        for (Student s : students) {
            for (Map.Entry<Subject, Double> e : s.getGrades().entrySet()) {
                temp.computeIfAbsent(e.getKey(), k -> new ArrayList<>()).add(e.getValue());
            }
        }
        Map<Subject, Double> result = new HashMap<>();
        for (Map.Entry<Subject, List<Double>> e : temp.entrySet()) {
            double sum = 0;
            for (double g : e.getValue()) {
                sum += g;
            }
            result.put(e.getKey(), sum / e.getValue().size());
        }
        return result;
    }
}