package com.carservice.carservicemanager.service;

import com.carservice.carservicemanager.model.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAll();
    Optional<Employee> getById(Long id);
    Employee save(Employee employee);
    Employee update(Long id, Employee employee);
    void deactivate(Long id);
    boolean existsByUsername(String username);
    void resetPassword(Long id, String newPassword);
}