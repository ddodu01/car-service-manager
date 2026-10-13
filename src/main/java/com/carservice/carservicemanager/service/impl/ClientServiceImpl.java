package com.carservice.carservicemanager.service.impl;

import com.carservice.carservicemanager.model.entity.Client;
import com.carservice.carservicemanager.repository.ClientRepository;
import com.carservice.carservicemanager.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client save(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        existing.setName(client.getName());
        existing.setSurname(client.getSurname());
        existing.setPhone(client.getPhone());
        existing.setEmail(client.getEmail());
        existing.setAddress(client.getAddress());
        return clientRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> findByNameOrSurname(String criteriu) {
        return clientRepository.findByNameContainingOrSurnameContaining(criteriu, criteriu);
    }

    @Override
    public boolean existsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }
}