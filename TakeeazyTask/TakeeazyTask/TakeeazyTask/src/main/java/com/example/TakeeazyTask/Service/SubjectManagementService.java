package com.example.TakeeazyTask.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TakeeazyTask.Exception.UserNotFoundException;
import com.example.TakeeazyTask.Model.SubjectManagement;
import com.example.TakeeazyTask.Repository.SubjectManagementRepository;
import com.example.TakeeazyTask.RepositoryImpl.SubjectManagementRepositoryImpl;


@Service
public class SubjectManagementService implements SubjectManagementRepositoryImpl {

	@Autowired
	private SubjectManagementRepository subjectManagementRepository;

	@Override
	public SubjectManagement saveSubjectManagement(SubjectManagement subjectManagement) throws Exception {
		subjectManagement.setSubjectAddDateTime(LocalDate.now());
		return subjectManagementRepository.save(subjectManagement);
	}

	@Override
	public List<SubjectManagement> getAllSubjectManagement() {
		return this.subjectManagementRepository.findAll();
	}

	@Override
	public SubjectManagement getSubjectManagementById(Long subjectId) {
		try {
			return subjectManagementRepository.findById(subjectId).orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteSubjectManagementById(Long subjectId) {
		this.subjectManagementRepository.deleteById(subjectId);
	}

	@Override
	public SubjectManagement updatedSubjectManagement(SubjectManagement updatedSubject, Long subjectId)
			throws Exception {
		SubjectManagement existingManagement = subjectManagementRepository.findById(subjectId).orElse(null);
		if (existingManagement == null) {
			throw new UserNotFoundException("Subject not found");
		}
		existingManagement.setSubjectName(updatedSubject.getSubjectName());
		return subjectManagementRepository.save(existingManagement);
	}

}
