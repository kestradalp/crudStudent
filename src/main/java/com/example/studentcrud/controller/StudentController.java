package com.example.studentcrud.controller;

import com.example.studentcrud.model.Student;
import com.example.studentcrud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // Mostrar la lista de estudiantes
    @GetMapping
    public String getAllStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    // Mostrar el formulario para crear un nuevo estudiante
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "create_student";
    }

    // Crear un nuevo estudiante
    @PostMapping
    public String createStudent(@ModelAttribute("student") Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }

    // Mostrar el formulario para actualizar un estudiante existente
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        model.addAttribute("student", student);
        return "edit_student";
    }

    // Actualizar un estudiante existente
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student studentDetails) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        student.setEmail(studentDetails.getEmail());
        student.setName(studentDetails.getName());
        student.setPassword(studentDetails.getPassword());
        studentRepository.save(student);
        return "redirect:/students";
    }

    // Eliminar un estudiante
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        studentRepository.delete(student);
        return "redirect:/students";
    }
}
