package service;

import entity.Student;

import java.util.List;

public interface StudentService {

    int registerStudents(int n);
    List<Student> findAll();
    List<Student> updateByName(String name);
    List<Student> deleteById(Long id);
}
