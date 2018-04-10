package com.cooksys.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.dto.ProjectManagerDto;
import com.cooksys.entity.Project;
import com.cooksys.entity.ProjectManager;

public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Long>{

	ProjectManager findAllById(Long id);

	List<Project> findManagerById(Long id);

	Collection<ProjectManager> findProjectsByProjectsEndDateLessThanOrderBySizeOfProjects(
			Date endDate);

	void SizeOfProjects(Integer sizeOfProjects);

	List<ProjectManager> findProjectsByProjectsEndDateLessThan(Date endDate);
}
