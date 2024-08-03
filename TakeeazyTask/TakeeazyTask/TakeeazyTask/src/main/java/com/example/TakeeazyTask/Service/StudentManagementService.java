package com.example.TakeeazyTask.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.TakeeazyTask.Exception.UserNotFoundException;
import com.example.TakeeazyTask.Model.StudentManagement;
import com.example.TakeeazyTask.Model.SubjectManagement;
import com.example.TakeeazyTask.Repository.StudentManagementRespositroy;
import com.example.TakeeazyTask.Repository.SubjectManagementRepository;
import com.example.TakeeazyTask.RepositoryImpl.StudentManagementRespositroyImpl;

@Service
public class StudentManagementService implements StudentManagementRespositroyImpl {

	@Autowired
	private StudentManagementRespositroy studentManagementRepository;

	@Autowired
	private SubjectManagementRepository subjectManagementRepository;

	@Override
	public StudentManagement saveStudentManagement(StudentManagement studentManagement) throws Exception {
	    // Validate SubjectIds are provided
	    Set<SubjectManagement> subjects = new HashSet<>();
	    for (SubjectManagement subject : studentManagement.getSubjectManagements()) {
	        SubjectManagement subjectEntity = subjectManagementRepository.findById(subject.getSubjectId())
	                .orElseThrow(() -> new Exception("Subject not found"));
	        subjects.add(subjectEntity);
	    }

	    // Check if email already exists
	    if (studentManagementRepository.existsByEmail(studentManagement.getEmail())) {
	        throw new DataIntegrityViolationException("Email address already exists");
	    }

	    // Set subjects and current date
	    studentManagement.setSubjectManagements(subjects);
	    studentManagement.setStudentRegDateTime(LocalDate.now());

	    StudentManagement savedStudent = studentManagementRepository.save(studentManagement);

	    // Debugging: Print the saved student and their subjects
	    System.out.println("Saved Student: " + savedStudent.getStudentId());
	    savedStudent.getSubjectManagements()
	            .forEach(subject -> System.out.println("Subject ID: " + subject.getSubjectId()));

	    return savedStudent;
	}

	@Override
	public StudentManagement updatedStudentManagement(StudentManagement updatedStudent, Long studentId)
			throws Exception {
		StudentManagement existingStudent = studentManagementRepository.findById(studentId).orElse(null);
		if (existingStudent == null) {
			throw new UserNotFoundException("Student not found");
		}

		if (updatedStudent.getEmail() == null || updatedStudent.getEmail().isEmpty()) {
			throw new IllegalArgumentException("Email cannot be empty");
		}

		// Update student properties
		existingStudent.setStudentName(updatedStudent.getStudentName());
		existingStudent.setEmail(updatedStudent.getEmail());
		existingStudent.setNumber(updatedStudent.getNumber());
		existingStudent.setAddress(updatedStudent.getAddress());

		// Update subjects
		Set<SubjectManagement> subjects = new HashSet<>();
		for (SubjectManagement subject : updatedStudent.getSubjectManagements()) {
			SubjectManagement subjectEntity = subjectManagementRepository.findById(subject.getSubjectId())
					.orElseThrow(() -> new Exception("Subject not found"));
			subjects.add(subjectEntity);
		}
		existingStudent.setSubjectManagements(subjects);

		return studentManagementRepository.save(existingStudent);
	}

	@Override
	public List<StudentManagement> getAllStudentManagement() {
		return studentManagementRepository.findAll();
	}

	@Override
	public StudentManagement getStudentManagementById(Long studentId) {
		try {
			return studentManagementRepository.findById(studentId).orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteStudentManagementById(Long studentId) {
		studentManagementRepository.deleteById(studentId);
	}

}
