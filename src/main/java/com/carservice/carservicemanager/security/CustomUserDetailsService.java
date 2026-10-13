package com.carservice.carservicemanager.security;

import com.carservice.carservicemanager.model.entity.Client;
import com.carservice.carservicemanager.model.entity.Employee;
import com.carservice.carservicemanager.repository.ClientRepository;
import com.carservice.carservicemanager.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username).orElse(null);
        if (employee != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(employee.getUsername())
                    .password(employee.getPassword())
                    .authorities("ROLE_" + employee.getRole())
                    .build();
        }

        Client client = clientRepository.findByUsername(username).orElse(null);
        if (client != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(client.getUsername())
                    .password(client.getPassword())
                    .authorities("ROLE_CLIENT")
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}