package com.ai.repo;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.model.Position;
import com.ai.model.Project;
import com.ai.model.Staff;

public interface StaffRepo extends JpaRepository<Staff, Integer> {
	
//	List<Staff> findByStaffId(String staffId);
	Staff findBystaffId(String staffId);

	@Modifying
	@Transactional
	@Query(
			value="UPDATE staff s SET s.salary=? WHERE staff_id=?",
			nativeQuery = true
			)
	int updateStaffSalary(double salary, String staffId);
	
	@Modifying
	@Transactional
	@Query(
			value="UPDATE staff s SET s.name=?,s.password=?,s.signature=?  WHERE staff_id=? ",
			nativeQuery=true
	)
	int updateDataExceptSalary(String name,String password,String signature, String staffId);
	
//	@Modifying
//	@Transactional
//	@Query(
//			value="UPDATE staff s SET s.position=? WHERE staff_id=? ",
//			nativeQuery=true
//	)
//	int updatePosition(Position position,String id);

	@Modifying
	@Transactional
	@Query(
			value="UPDATE position p SET p.name=? where p.name=p.name ",
			nativeQuery=true
	)
	int updatePosition();
	
	
}
