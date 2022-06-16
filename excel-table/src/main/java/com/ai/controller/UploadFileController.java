package com.ai.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ai.repo.StaffRepo;
import com.ai.service.FileService;
import com.ai.service.SalaryService;

@Controller
public class UploadFileController {
	@Autowired
	private FileService service;
	@Autowired
	private SalaryService salaryService;
	@Autowired
	private StaffRepo staffrepo;
	
	
	@GetMapping("/")
    public String index(Model model) {
        return "uploadform";
    }
		
	@PostMapping("/file")
	public String uploadMultipartFile(@RequestParam("uploadfile")MultipartFile file,Model model) {
		try {
			service.store(file);
		
			model.addAttribute("error", "File uploaded successfully!");
		}catch (Exception e) {
			model.addAttribute("error", "Fail! -> uploaded filename: " + file.getOriginalFilename());
		}
		return "uploadform";
	}
	
	
	
	@PostMapping("/salary")
	public String multipartFile(@RequestParam("uploadSalary") MultipartFile file, Model model) {
	
		try{
			
			salaryService.store(file);
			model.addAttribute("message","file upload successfully");
			
		}
		catch(Exception e){
			model.addAttribute("message", "fail uplaod filename: "+ e.getMessage()+ file.getOriginalFilename());
		}
		return "uploadform";
		
	}
	
	
	/**
	 * Download File
	 *
	 
	@GetMapping("/file")
	public ResponseEntity<InputStreamResource> downloadFile() {
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "attachment; filename=Team Structure.xlsx");
		
		return ResponseEntity
                .ok()
                .headers(header)
                .body(new InputStreamResource(service.loadFile()));
	}
	*/
}
