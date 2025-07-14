package com.example.demo.service;

import com.example.demo.dto.VisitorRequestDTO;
import com.example.demo.dto.VisitorResponseDTO;
import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository repository;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public VisitorResponseDTO create(VisitorRequestDTO dto) {
        Visitor visitor = new Visitor(
                idGenerator.getAndIncrement(),
                dto.getName(),
                dto.getAge(),
                dto.getGender()
        );
        repository.save(visitor);
        return toDTO(visitor);
    }

    public List<VisitorResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VisitorResponseDTO update(Long id, VisitorRequestDTO dto) {
        repository.removeIf(v -> v.getId().equals(id));
        Visitor updated = new Visitor(id, dto.getName(), dto.getAge(), dto.getGender());
        repository.save(updated);
        return toDTO(updated);
    }

    public void delete(Long id) {
        repository.removeIf(v -> v.getId().equals(id));
    }

    private VisitorResponseDTO toDTO(Visitor visitor) {
        return new VisitorResponseDTO(visitor.getId(), visitor.getName(), visitor.getAge(), visitor.getGender());
    }
}
