package com.example.application.services;

import com.example.application.data.Estudiante;
import com.example.application.data.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public List<Estudiante> findAll() {
        return repository.findAll();
    }

    public void save(Estudiante estudiante) {
        repository.save(estudiante);
    }

    public void delete(Estudiante estudiante) {
        repository.delete(estudiante);
    }
}