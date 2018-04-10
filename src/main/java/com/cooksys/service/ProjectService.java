package com.cooksys.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.dto.ProjectDto;
import com.cooksys.dto.ProjectManagerDto;
import com.cooksys.entity.Project;
import com.cooksys.entity.ProjectManager;
import com.cooksys.exception.ReferencedEntityNotFoundException;
import com.cooksys.mapper.ProjectManagerMapper;
import com.cooksys.mapper.ProjectMapper;
import com.cooksys.repository.JpaProjectRepository;
import com.cooksys.repository.ProjectManagerRepository;

@Service
public class ProjectService {

	private JpaProjectRepository projectRepo;
	private ProjectMapper mapper;
	private ProjectManagerRepository projManageRepo;
	private ProjectManagerMapper manageMapper;

	public ProjectService(JpaProjectRepository projectRepo, ProjectManagerRepository projManageRepo, ProjectMapper mapper, ProjectManagerMapper manageMapper) {
		this.projectRepo = projectRepo;
		this.mapper = mapper;
		this.projManageRepo = projManageRepo;
		this.manageMapper = manageMapper;
	}
	
	public boolean has(Long id) {
		return projectRepo.existsById(id);
	}

	public List<ProjectDto> getAll(Date date) {
		return projectRepo.findAllByEndDateLessThan(date).stream().map(mapper::toDto).collect(Collectors.toList());
	}
	
	public Long post(ProjectDto projectDto) {
		projectDto.setId(null);
		return projectRepo.save(mapper.toEntity(projectDto)).getId();
	}

	public Set<ProjectDto> getProjectManager(Long id) {
		return projectRepo.findProjectManagerByManagerId(id).stream().map(mapper::toDto).collect(Collectors.toSet());
	}

}
