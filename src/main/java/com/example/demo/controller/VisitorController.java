package com.example.demo.controller;

import com.example.demo.dto.VisitorRequestDTO;
import com.example.demo.dto.VisitorResponseDTO;
import com.example.demo.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService service;

    @PostMapping
    public VisitorResponseDTO create(@RequestBody VisitorRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<VisitorResponseDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public VisitorResponseDTO update(@PathVariable Long id, @RequestBody VisitorRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
