package com.example.TakeeazyTask.RestController;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.TakeeazyTask.Dto.ResponseMessageDto;
import com.example.TakeeazyTask.Exception.UserNotFoundException;
import com.example.TakeeazyTask.Model.SubjectManagement;
import com.example.TakeeazyTask.Repository.SubjectManagementRepository;
import com.example.TakeeazyTask.RepositoryImpl.SubjectManagementRepositoryImpl;


@RestController
@RequestMapping("/api/v1/subject")
public class SubjectManagementRestController {
	
	@Autowired
	private SubjectManagementRepositoryImpl subjectManagementRepositoryImpl;
	
	@Autowired
	private SubjectManagementRepository subjectManagementRepository;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageDto> createSubjectManagement(
			@RequestBody SubjectManagement subjectManagement) {
		try {
			SubjectManagement savedSubjectManagement = subjectManagementRepositoryImpl.saveSubjectManagement(subjectManagement);
			ResponseMessageDto responseMessage = new ResponseMessageDto("success", "Subject saved successfully", null,
					new Date(),true);
			return ResponseEntity.ok(responseMessage);
		} catch (Exception e) {
			ResponseMessageDto responseMessage = new ResponseMessageDto("error", "Failed to save subject",
					e.getMessage(), new Date(),false);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
		}
	}
	
	@RequestMapping(value = "/update/{subjectId}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageDto> updateSubjectManagement(@PathVariable Long subjectId,
			@RequestBody SubjectManagement updatedSubject) {
		try {
			SubjectManagement updatedSubjectManagement = subjectManagementRepositoryImpl
					.updatedSubjectManagement(updatedSubject, subjectId);
			ResponseMessageDto responseMessage = new ResponseMessageDto("success", "Classes updated successfully", null,
					new Date(),true);
			return ResponseEntity.ok(responseMessage);
		} catch (UserNotFoundException e) {
			ResponseMessageDto responseMessage = new ResponseMessageDto("error", "Classes not found", e.getMessage(),
					new Date(),false);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
		} catch (Exception e) {
			ResponseMessageDto responseMessage = new ResponseMessageDto("error", "Failed to update Classes",
					e.getMessage(), new Date(),false);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
		}
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllSubjectManagement() {
		List<SubjectManagement> subjectManagements = subjectManagementRepositoryImpl.getAllSubjectManagement();
		if (subjectManagements.isEmpty()) {
			ResponseMessageDto responseMessage = new ResponseMessageDto("error", "No Subject found",
					"Subject list is empty", new Date(),false);
			return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
		} else {
			return ResponseEntity.ok(subjectManagements);
		}
	}
	
	@RequestMapping(value = "/details/{subjectId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getSubjectId(@PathVariable Long subjectId) {
		try {
			SubjectManagement subjectManagement = subjectManagementRepositoryImpl.getSubjectManagementById(subjectId);

			if (subjectManagement == null) {
				throw new RuntimeException("Subject details not found");
			}

			return ResponseEntity.ok(subjectManagement);
		} catch (RuntimeException e) {
			ResponseMessageDto responseMessage = new ResponseMessageDto("error", "Subject details not found",
					e.getMessage(), new Date(),false);
			return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/delete/{subjectId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageDto> deleteSubjectManagement(@PathVariable Long subjectId) {
		try {
			subjectManagementRepositoryImpl.deleteSubjectManagementById(subjectId);
			ResponseMessageDto responseMessage = new ResponseMessageDto("success", "Subject successfully deleted", null,
					new Date(),true);
			return ResponseEntity.ok(responseMessage);
		} catch (Exception e) {
			ResponseMessageDto responseMessage = new ResponseMessageDto("error", "Failed to delete subject",
					e.getMessage(), new Date(),false);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
		}
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public long getTotalSubjectCount() {
		long totalSubject = subjectManagementRepository.count();
		return totalSubject;
	}

	
}
