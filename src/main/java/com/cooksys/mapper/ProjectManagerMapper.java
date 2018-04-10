package com.cooksys.mapper;

import java.util.Set;

import org.mapstruct.Mapper;

import com.cooksys.dto.ProjectManagerDto;
import com.cooksys.entity.Project;
import com.cooksys.entity.ProjectManager;

@Mapper(componentModel = "spring", uses = { ReferenceMapper.class })
public interface ProjectManagerMapper {

	ProjectManager toEntity(ProjectManagerDto dto);
	
	ProjectManagerDto toDto(ProjectManager entity);
}
