package com.example.TakeeazyTask.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "studentManagement")
public class StudentManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentId;

	@Column(name = "student_req_date_time")
	private LocalDate studentRegDateTime;

	@Column(name = "student_name")
	private String studentName;

	@Column(unique = true)
	@NotEmpty(message = "Email must not be empty")
	private String email;

	@Column(name = "number")
	private String number;

	@Column(name = "address")
	private String address;

	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private String role;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "student_subject", joinColumns = @JoinColumn(name = "studentId"), inverseJoinColumns = @JoinColumn(name = "subjectId"))
	private Set<SubjectManagement> subjectManagements = new HashSet<>();

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public LocalDate getStudentRegDateTime() {
		return studentRegDateTime;
	}

	public void setStudentRegDateTime(LocalDate studentRegDateTime) {
		this.studentRegDateTime = studentRegDateTime;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<SubjectManagement> getSubjectManagements() {
		return subjectManagements;
	}

	public void setSubjectManagements(Set<SubjectManagement> subjectManagements) {
		this.subjectManagements = subjectManagements;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}