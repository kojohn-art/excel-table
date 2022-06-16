package com.ai.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ai.model.Position;
import com.ai.model.Salary;
import com.ai.model.Staff;
import com.ai.repo.PositionRepo;
import com.ai.repo.StaffRepo;
import com.ai.util.SalaryUtil;

@Service
public class SalaryService {
	@Autowired
	private PositionRepo positionRepo;

	@Autowired
	private StaffRepo staffRepo;

	public void store(MultipartFile file) {
		try {
			List<Salary> salary = SalaryUtil.parseExcelFile(file.getInputStream());

			for (Salary s : salary) {
				
				Staff  st=staffRepo.findBystaffId(s.getStaffId());
				Position p = positionRepo.findByName(s.getPosition());
				
				if(st == null ) {
					Staff staff = new Staff();

					if(p== null) {
						Position position = new Position();
						position.setName(s.getPosition());
						positionRepo.save(position);
						
						staff.setName(s.getName());
						staff.setStaffId(s.getStaffId());
						staff.setPositions(position);
						staff.setSalary(s.getBasicPay());
						staffRepo.save(staff);
					}else {
						
						staff.setName(s.getName());
						staff.setStaffId(s.getStaffId());
						staff.setPositions(p);
						staff.setSalary(s.getBasicPay());
						staffRepo.save(staff);
						
					}
					

				}else {
				
					st.setSalary(s.getBasicPay());	
					staffRepo.save(st);
				}
					
				}

				
				
				
				
				

				// Hein htet Aung
//				List<Staff> list = staffRepo.findByStaffId(s.getStaffId());
//				for (Staff ss : list) {
//					ss.setSalary(s.getBasicPay());
//					staffRepo.save(ss);
//				}

			

		} catch (IOException e) {
			throw new RuntimeException("Fail -> " + e.getMessage());
		}

	}

}
