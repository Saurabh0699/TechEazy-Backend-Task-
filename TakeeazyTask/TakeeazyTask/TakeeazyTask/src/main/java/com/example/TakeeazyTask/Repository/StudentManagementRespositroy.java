package com.example.TakeeazyTask.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TakeeazyTask.Model.StudentManagement;

@Repository
public interface StudentManagementRespositroy extends JpaRepository<StudentManagement, Long>{
	long count();

	boolean existsByEmail(String email);

	StudentManagement findByEmail(String email);
	
}
