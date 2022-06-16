package com.ai.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.model.Project;

public interface ProjectRepo extends JpaRepository<Project, Integer> {
	Project findByProjectId(String id);
	
	
	
	@Query("select p from Project p join p.staffs s where s.staffId =: staffId ")
	List<Project> findProjectByStaffId(String staffId);
}
