package com.carservice.carservicemanager.repository;

import com.carservice.carservicemanager.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String username);
    List<Client> findByNameContainingOrSurnameContaining(String name, String surname);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}