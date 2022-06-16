package com.ai.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ai.model.Position;
import com.ai.model.Project;
import com.ai.model.Staff;
import com.ai.model.Team;
import com.ai.model.TeamStructure;
import com.ai.repo.PositionRepo;
import com.ai.repo.ProjectRepo;
import com.ai.repo.StaffRepo;
import com.ai.repo.TeamRepo;
import com.ai.util.ExcelUtil;

@Service
public class FileService {

	@Autowired
	private PositionRepo positionRepo;
	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private StaffRepo staffRepo;
	@Autowired
	private TeamRepo teamRepo;

	// Store File Data to database
	public void store(MultipartFile file) throws IOException {
		try {
			List<TeamStructure> list = ExcelUtil.parseExcelFile(file.getInputStream());

			for (TeamStructure ts : list) {
				Staff st = staffRepo.findBystaffId(ts.getStaffId());
				Position p = positionRepo.findByName(ts.getPosition());
				Project pt = projectRepo.findByProjectId(ts.getProject());
				Team te = teamRepo.findByName(ts.getTeam());
				Staff staff = new Staff();
				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
				if (st == null) {

					if (p != null) {
						staff.setPositions(p);
					} else {
						Position position = new Position();
						position.setName(ts.getPosition());
						positionRepo.save(position);
					}

					if (te != null) {
						staff.setTeams(Arrays.asList(te));
					} else {
						Team team = new Team();
						team.setName(ts.getTeam());
						teamRepo.save(team);
					}

					if (pt != null) {
						staff.setProjects(Arrays.asList(pt));
					} else {
						Project pj = new Project();
						pj.setName(ts.getProjectName());
						pj.setProjectId(ts.getProject());
						projectRepo.save(pj);
					}

					staff.setName(ts.getName());
					staff.setStaffId(ts.getStaffId());

					staffRepo.save(staff);

				} else {
					// List<Project> pjList=staff.getProjects().stream().filter( a ->
					// a.getProjectId().equals(ts.getProject())).collect(Collectors.toList());
					List<Team> teamList = teamRepo.findTeamsByStaffId(staff.getStaffId());

					for (Team t : teamList) {
						if (!ts.getTeam().equals(t.getName())) {
							staff.setTeams(Arrays.asList(t));
						} else {

							List<Project> pjList = projectRepo.findProjectByStaffId(staff.getStaffId());

							for (Project pp : pjList) {
								if (!ts.getProject().equals(pp.getProjectId())) {
									staff.setProjects(Arrays.asList(pp));
								} else {

									if (!ts.getPosition().equals(staff.getPositions())) {
										staff.setPositions(p);
										staffRepo.save(staff);
									} else {

									}
								}
							}

						}
					}

				}
			}
		}

		catch (IOException e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
	}

	
	
	
	

}
