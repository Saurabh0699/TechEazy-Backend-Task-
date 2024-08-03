package com.example.TakeeazyTask.RestController;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.TakeeazyTask.Config.JwtUtil;
import com.example.TakeeazyTask.Dto.ResponseMessageDto;
import com.example.TakeeazyTask.Dto.StudentManagementDto;
import com.example.TakeeazyTask.Model.StudentManagement;
import com.example.TakeeazyTask.Repository.StudentManagementRespositroy;
import com.example.TakeeazyTask.RepositoryImpl.StudentManagementRespositroyImpl;

@RestController
@RequestMapping("/api/v1/student")
public class StudentManagementRestController {

	@Autowired
	private StudentManagementRespositroyImpl studentManagementRespositroyImpl;

	@Autowired 
	private StudentManagementRespositroy studentManagementRespositroy;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	 private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageDto> createStudentManagement(@RequestBody StudentManagement studentManagement) {
	    try {
	        StudentManagement savedStudent = studentManagementRespositroyImpl.saveStudentManagement(studentManagement);
	        ResponseMessageDto responseMessage = new ResponseMessageDto("success", "Student details saved successfully",
	                null, new Date(), true);
	        return ResponseEntity.ok(responseMessage);
	    } catch (DataIntegrityViolationException e) {
	        ResponseMessageDto responseMessage = new ResponseMessageDto("error", "Email address already exists",
	                e.getMessage(), new Date(), false);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
	    } catch (Exception e) {
	        ResponseMessageDto responseMessage = new ResponseMessageDto("error", "Failed to save Student details",
	                e.getMessage(), new Date(), false);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
	    }
	}


	@RequestMapping(value = "/update/{studentId}", method = RequestMethod.POST)
	 public ResponseEntity<ResponseMessageDto> updateStudentManagement(@PathVariable Long studentId, @RequestBody StudentManagement studentManagement) {
        try {
            StudentManagement updatedStudent = studentManagementRespositroyImpl.updatedStudentManagement(studentManagement, studentId);
            ResponseMessageDto responseMessage = new ResponseMessageDto("success", "Student details updated successfully",
                    null, new Date(), true);
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            ResponseMessageDto responseMessage = new ResponseMessageDto("error", "Failed to update Student details",
                    e.getMessage(), new Date(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

	@RequestMapping(value = "/details", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStudentManagement() {
        List<StudentManagement> studentManagements = studentManagementRespositroyImpl.getAllStudentManagement();
        if (studentManagements.isEmpty()) {
            ResponseMessageDto responseMessage = new ResponseMessageDto("error", "No Student found",
                    "Student list is empty", new Date(), false);
            return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(studentManagements);
        }
    }
	
	@RequestMapping(value = "/details/{studentId}", method = RequestMethod.GET)
	public ResponseEntity<?> getStudentManagementById(@PathVariable Long studentId) {
		try {
			StudentManagement studentManagement = studentManagementRespositroyImpl.getStudentManagementById(studentId);

			if (studentManagement == null) {
				throw new RuntimeException("Student details not found");
			}

			return ResponseEntity.ok(studentManagement);
		} catch (RuntimeException e) {
			ResponseMessageDto responseMessage = new ResponseMessageDto("error", "Student details not found",
					e.getMessage(), new Date(),false);
			return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete/{studentId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageDto> deleteStudentManagement(@PathVariable Long studentId) {
		try {
			studentManagementRespositroyImpl.deleteStudentManagementById(studentId);
			ResponseMessageDto responseMessage = new ResponseMessageDto("success", "Student successfully deleted", null,
					new Date(),true);
			return ResponseEntity.ok(responseMessage);
		} catch (Exception e) {
			ResponseMessageDto responseMessage = new ResponseMessageDto("error", "Failed to delete Student",
					e.getMessage(), new Date(),false);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> loginUser(@RequestBody StudentManagement loginRequest) {
	    String email = loginRequest.getEmail();
	    String password = loginRequest.getPassword();

	    StudentManagement studentManagement = studentManagementRespositroy.findByEmail(email);

	    if (studentManagement == null || !studentManagement.getPassword().equals(studentManagement.getPassword())) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(new ResponseMessageDto("error", "Invalid email or password", "Unauthorized", new Date(), false));
	    }

	    // Generate JWT token
	    String token = jwtUtil.generateToken(email);

	    // Create login response DTO
	    StudentManagementDto loginResponseDto = new StudentManagementDto();
	    loginResponseDto.setStudentId(studentManagement.getStudentId());
	    loginResponseDto.setStudentName(studentManagement.getStudentName());
	    loginResponseDto.setEmail(studentManagement.getEmail());
	    loginResponseDto.setRole(studentManagement.getRole());
	    loginResponseDto.setToken(token);

	    return ResponseEntity.ok(loginResponseDto);
	}

}
