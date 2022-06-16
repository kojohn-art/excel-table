package com.ai.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.model.Team;

public interface TeamRepo extends JpaRepository<Team, Integer>{
	Team findByName(String name);
	
	@Query("select t from Staff s join s.teams t where s.staffId =: staffId")
	List<Team> findTeamsByStaffId(String staffId);
}
