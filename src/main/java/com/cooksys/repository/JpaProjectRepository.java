package com.cooksys.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.dto.ProjectDto;
import com.cooksys.entity.Project;

public interface JpaProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findAllById(Long id);

	List<Project> findAllByEndDateLessThan(Date endDate);

	Collection<Project> findProjectManagerByManagerId(Long id);

	Collection<Project> findAllProjectManagerByEndDateLessThanOrderByManagerProjects(Date endDate);

}