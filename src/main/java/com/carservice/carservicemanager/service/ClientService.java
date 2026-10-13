package com.carservice.carservicemanager.service;

import com.carservice.carservicemanager.model.entity.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAll();
    Optional<Client> getById(Long id);
    Client save(Client client);
    Client update(Long id, Client client);
    void delete(Long id);
    List<Client> findByNameOrSurname(String criteriu);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}