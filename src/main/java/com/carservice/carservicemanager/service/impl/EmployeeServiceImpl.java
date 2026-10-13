package com.carservice.carservicemanager.service.impl;

import com.carservice.carservicemanager.model.entity.Employee;
import com.carservice.carservicemanager.repository.EmployeeRepository;
import com.carservice.carservicemanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee save(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        existing.setName(employee.getName());
        existing.setSurname(employee.getSurname());
        existing.setEmail(employee.getEmail());
        existing.setRole(employee.getRole());
        return employeeRepository.save(existing);
    }

    @Override
    public void deactivate(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setRole("INACTIVE");
        employeeRepository.save(employee);
    }

    @Override
    public boolean existsByUsername(String username) {
        return employeeRepository.existsByUsername(username);
    }

    @Override
    public void resetPassword(Long id, String newPassword) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);
    }
}