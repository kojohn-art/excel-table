package com.ai.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.model.Position;

public interface PositionRepo extends JpaRepository<Position, Integer> {
	Position findByName(String name);
	
	
	
	
}
