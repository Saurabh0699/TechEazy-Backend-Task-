package com.example.TakeeazyTask.RepositoryImpl;

import java.util.List;

import com.example.TakeeazyTask.Model.SubjectManagement;

public interface SubjectManagementRepositoryImpl {

	SubjectManagement saveSubjectManagement(SubjectManagement subjectManagement) throws Exception;

	List<SubjectManagement> getAllSubjectManagement();

	SubjectManagement getSubjectManagementById(Long subjectId);

	void deleteSubjectManagementById(Long subjectId);

	SubjectManagement updatedSubjectManagement(SubjectManagement updatedSubject, Long subjectId) throws Exception;

}
