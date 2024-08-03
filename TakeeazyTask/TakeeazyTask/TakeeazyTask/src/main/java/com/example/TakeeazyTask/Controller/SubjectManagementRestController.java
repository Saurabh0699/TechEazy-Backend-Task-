package com.example.TakeeazyTask.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.TakeeazyTask.Model.SubjectManagement;
import com.example.TakeeazyTask.RepositoryImpl.SubjectManagementRepositoryImpl;


@RestController
@RequestMapping("/api/v1")
public class SubjectManagementRestController {
	
	@Autowired
	private SubjectManagementRepositoryImpl subjectManagementRepositoryImpl;
	
	protected static final String PARAMS = "params";

	@RequestMapping(value = "/subjectdetails", method = RequestMethod.GET)
	public String subjectManagementdetails(Model model) {
		List<SubjectManagement> subjectManagementList;
		subjectManagementList = subjectManagementRepositoryImpl.getAllSubjectManagement();
		model.addAttribute("subjectManagementList", subjectManagementList);

		return "subject_details";
	}

	@RequestMapping(value = "/subjectadd", method = RequestMethod.GET)
	public String showNewSubjectForm(Model model) {
		model.addAttribute(PARAMS, new SubjectManagement());
		return "add_subject";
	}

	@RequestMapping(value = "/addsubject", method = RequestMethod.POST)
	public String saveSubjectManagement(@ModelAttribute("subjectManagement") SubjectManagement subjectManagement) throws Exception {
		subjectManagement.setSubjectAddDateTime(LocalDate.now());
		subjectManagementRepositoryImpl.saveSubjectManagement(subjectManagement);
		return "redirect:/subjectdetails";
	}

	@RequestMapping(value = "/subjectdetails/{subjectId}", method = RequestMethod.GET)
	public String getSubjectManagement(@PathVariable(value = "subjectId") Long subjectId, Model model) {
		SubjectManagement subjectManagement = subjectManagementRepositoryImpl.getSubjectManagementById(subjectId);
		model.addAttribute("subjectManagement", subjectManagement);
		return "update_subject";
	}

	@RequestMapping(value = "/subjectdetails/delete/{subjectId}", method = RequestMethod.GET)
	public String deleteSubjectManagement(@PathVariable(value = "subjectId") Long subjectId) {
		this.subjectManagementRepositoryImpl.deleteSubjectManagementById(subjectId);
		return "redirect:/subjectdetails";
	}
	
}
