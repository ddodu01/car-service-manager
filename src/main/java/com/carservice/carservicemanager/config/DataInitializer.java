//package com.carservice.carservicemanager.config;
//
//import com.carservice.carservicemanager.model.entity.Employee;
//import com.carservice.carservicemanager.repository.EmployeeRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer implements CommandLineRunner {
//
//    private final EmployeeRepository employeeRepository;
//    private final PasswordEncoder passwordEncoder;
//
//  //  @Override
////    public void run(String... args) {
////        if (employeeRepository.findByUsername("admin").isEmpty()) {
////            Employee manager = new Employee();
////            manager.setName("Admin");
////            manager.setSurname("Manager");
////            manager.setUsername("admin");
////            manager.setPassword(passwordEncoder.encode("admin123"));
////            manager.setRole("MANAGER");
////            manager.setEmail("admin@carservice.com");
////            employeeRepository.save(manager);
////            System.out.println("Admin user creat: username=admin, password=admin123");
////        }
//    }
//}