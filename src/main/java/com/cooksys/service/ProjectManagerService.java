package com.cooksys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.dto.ProjectManagerDto;
import com.cooksys.entity.ProjectManager;
import com.cooksys.exception.ReferencedEntityNotFoundException;
import com.cooksys.mapper.ProjectManagerMapper;
import com.cooksys.mapper.ProjectMapper;
import com.cooksys.repository.JpaProjectRepository;
import com.cooksys.repository.ProjectManagerRepository;

@Service
public class ProjectManagerService {

	private ProjectManagerRepository repo;
	private ProjectManagerMapper mapper;
	private JpaProjectRepository jpaRepo;
	private ProjectMapper projMapper;
	private ProjectService projService;

	public ProjectManagerService(ProjectManagerRepository repo, ProjectManagerMapper mapper, JpaProjectRepository jpaRepo, ProjectMapper projMapper, ProjectService projService) {
		this.repo = repo;
		this.mapper = mapper;
		this.jpaRepo = jpaRepo;
		this.projMapper = projMapper;
		this.projService = projService;
	}
	
	public List<ProjectManagerDto> getAll() {
		return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public boolean has(Long id) {
		return repo.existsById(id);
	}

	public ProjectManagerDto get(Long id) {
		mustExist(id);
		return mapper.toDto(repo.findAllById(id));
	}

	public Long post(ProjectManagerDto projectManagerDto) {
		projectManagerDto.setId(null);
		return repo.save(mapper.toEntity(projectManagerDto)).getId();
	}

	public void put(Long id, ProjectManagerDto projectManagerDto) {
		mustExist(id);
		projectManagerDto.setId(id);
		repo.save(mapper.toEntity(projectManagerDto));
	}
	
	private void mustExist(Long id) {
		if(!has(id))
			throw new ReferencedEntityNotFoundException(ProjectManager.class, id);
	}

	public void delete(Long id) {
		mustExist(id);
		repo.deleteById(id);
	}

	public List<ProjectManagerDto> getAllOverdue(Date endDate) {
//		ProjectManager manager = new ProjectManager();
//		manager.setSizeOfProjects(projService.getAll(endDate).size());
		List<ProjectManagerDto> results = new ArrayList<ProjectManagerDto>();
		for (ProjectManagerDto manager : repo.findProjectsByProjectsEndDateLessThan(endDate).stream().map(mapper::toDto).collect(Collectors.toSet())) {
			
			manager.setSizeOfProjects(projService.getAll(endDate).size());
			results.add(manager);
		}
		
		return results;
	}
}