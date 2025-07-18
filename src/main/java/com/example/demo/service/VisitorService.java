package com.example.demo.service;

import com.example.demo.dto.VisitorRequestDTO;
import com.example.demo.dto.VisitorResponseDTO;
import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository repository;

    public VisitorResponseDTO create(VisitorRequestDTO dto) {
        Visitor visitor = new Visitor(null, dto.getName(), dto.getAge(), dto.getGender());
        Visitor saved = repository.save(visitor);
        return toDTO(saved);
    }

    public List<VisitorResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VisitorResponseDTO update(Long id, VisitorRequestDTO dto) {
        Visitor visitor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found with id: " + id));

        visitor.setName(dto.getName());
        visitor.setAge(dto.getAge());
        visitor.setGender(dto.getGender());

        return toDTO(repository.save(visitor));
    }


    public void delete(Long id) {
        repository.deleteById(id);
    }

    private VisitorResponseDTO toDTO(Visitor visitor) {
        return new VisitorResponseDTO(visitor.getId(), visitor.getName(), visitor.getAge(), visitor.getGender());
    }
}
