package com.cooksys.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.dto.ProjectDto;
import com.cooksys.dto.ProjectManagerDto;
import com.cooksys.entity.Project;
import com.cooksys.entity.ProjectManager;
import com.cooksys.service.ProjectService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("project")
public class ProjectController {
	
	private ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@GetMapping("{endDate}")
	@ApiOperation(value = "", nickname = "getAllOverdueProjects")
	public List<ProjectDto> getOverDueProjects(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
		return projectService.getAll(endDate);
	}
	
	@GetMapping("/projectManager/{id}/project")
	@ApiOperation(value = "", nickname = "getProjectsByManagerId")
	public Set<ProjectDto> getAllProjects(@PathVariable(name = "id") Long id){
		return projectService.getProjectManager(id);
	}
	
	@PostMapping
	@ApiOperation(value = "", nickname = "postNewProject")
	public Long post(@RequestBody @Validated ProjectDto projectDto, HttpServletResponse httpResponse) {
		Long id = projectService.post(projectDto);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return id;
	}

	@PostMapping("{id}/manager/{managerId}")
	@Transactional
	public void attachManager(@PathVariable(name ="id") Project project, @PathVariable(name = "managerId") ProjectManager pm) {
		project.setManager(pm);
		pm.getProjects().add(project);
		pm.setSizeOfProjects(pm.getProjects().size());
	}
}
