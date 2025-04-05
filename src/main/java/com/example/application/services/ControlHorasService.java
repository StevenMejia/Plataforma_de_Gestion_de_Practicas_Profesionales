package com.example.application.services;

import com.example.application.data.ControlHoras;
import com.example.application.data.ControlHorasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControlHorasService {

    private final ControlHorasRepository repository;

    public ControlHorasService(ControlHorasRepository repository) {
        this.repository = repository;
    }

    public List<ControlHoras> findAll() {
        return repository.findAll();
    }

    public void save(ControlHoras horas) {
        repository.save(horas);
    }

    public void delete(ControlHoras horas) {
        repository.delete(horas);
    }
}