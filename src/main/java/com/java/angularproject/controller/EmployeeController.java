package com.java.angularproject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.angularproject.exception.ResourceNotFoundException;
import com.java.angularproject.model.Employee;
import com.java.angularproject.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employees")
	@CrossOrigin(origins = "http://localhost:4200")
	public Employee createEmployee(@RequestBody Employee employee) {
		
		return employeeRepository.save(employee);
	}
	@GetMapping("/employees/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found : "+id));
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/employees/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employee) {
		Employee updateemployee=employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found : "+id));
	
		updateemployee.setFirstName(employee.getFirstName());
		updateemployee.setLastName(employee.getLastName());
		updateemployee.setEmailId(employee.getEmailId());
		
		Employee employee2 = employeeRepository.save(updateemployee);
		return ResponseEntity.ok(employee2);
	}
	
	
	@DeleteMapping("/employees/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found : "+id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
	
	  


}


