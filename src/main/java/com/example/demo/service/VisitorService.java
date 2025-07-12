package com.example.demo.service;

import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorService {
    private final VisitorRepository repository;

    public void save(Visitor visitor) {
        repository.save(visitor);
    }

    public void remove(Visitor visitor) {
        repository.remove(visitor);
    }

    public List<Visitor> findAll() {
        return repository.findAll();
    }
}
