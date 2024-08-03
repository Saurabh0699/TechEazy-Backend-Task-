package com.example.TakeeazyTask.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "subjectManagement")
public class SubjectManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subjectId;

	@Column(name = "subject_add_date_time")
	private LocalDate subjectAddDateTime;

	@Column(name = "subject_name")
	private String subjectName;

    @ManyToMany(mappedBy = "subjectManagements", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<StudentManagement> studentManagements = new HashSet<>();

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public LocalDate getSubjectAddDateTime() {
		return subjectAddDateTime;
	}

	public void setSubjectAddDateTime(LocalDate subjectAddDateTime) {
		this.subjectAddDateTime = subjectAddDateTime;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Set<StudentManagement> getStudentManagements() {
		return studentManagements;
	}

	public void setStudentManagements(Set<StudentManagement> studentManagements) {
		this.studentManagements = studentManagements;
	}

}
