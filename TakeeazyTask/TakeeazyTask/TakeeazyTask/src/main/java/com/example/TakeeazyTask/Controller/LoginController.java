package com.example.TakeeazyTask.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.TakeeazyTask.Repository.StudentManagementRespositroy;
import com.example.TakeeazyTask.Repository.SubjectManagementRepository;


@Controller
@RequestMapping("/api/v1")
public class LoginController {


	@Autowired
	private StudentManagementRespositroy studentsRepository;
	
	@Autowired
	private SubjectManagementRepository subjectManagementRepository;
	@Autowired

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {

		long totalStudent = studentsRepository.count();
		model.addAttribute("totalStudent", totalStudent);
		
		
		long totalSubject = subjectManagementRepository.count();
		model.addAttribute("totalSubject", totalSubject);


		return "dashboard";
	}
}
