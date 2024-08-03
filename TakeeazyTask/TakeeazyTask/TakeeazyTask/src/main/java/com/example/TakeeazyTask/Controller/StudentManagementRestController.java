package com.example.TakeeazyTask.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.TakeeazyTask.Model.StudentManagement;
import com.example.TakeeazyTask.RepositoryImpl.StudentManagementRespositroyImpl;

@Controller
@RequestMapping("/api/v1")
public class StudentManagementRestController {

	@Autowired
	private StudentManagementRespositroyImpl studentManagementRespositroyImpl;

	protected static final String PARAMS = "params";

	@RequestMapping(value = "/studentdetails", method = RequestMethod.GET)
	public String userdetails(Model model) {
		List<StudentManagement> studentList;
		studentList = studentManagementRespositroyImpl.getAllStudentManagement();
		model.addAttribute("studentList", studentList);

		return "student_details";
	}

	@RequestMapping(value = "/studentReg", method = RequestMethod.GET)
	public String showNewUserForm(Model model) {
		model.addAttribute(PARAMS, new StudentManagement());
		return "add_student";
	}

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("students") StudentManagement students) throws Exception {
		students.setStudentRegDateTime(LocalDate.now());
		studentManagementRespositroyImpl.saveStudentManagement(students);
		return "redirect:/studentdetails";
	}

	@RequestMapping(value = "/studentdetails/{studentId}", method = RequestMethod.GET)
	public String getUsers(@PathVariable(value = "studentId") Long studentId, Model model) {
		StudentManagement students = studentManagementRespositroyImpl.getStudentManagementById(studentId);
		model.addAttribute("students", students);
		return "update_student";
	}

	@RequestMapping(value = "/studentdetails/delete/{studentId}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable(value = "studentId") Long studentId) {
		this.studentManagementRespositroyImpl.deleteStudentManagementById(studentId);
		return "redirect:/studentdetails";
	}
}
