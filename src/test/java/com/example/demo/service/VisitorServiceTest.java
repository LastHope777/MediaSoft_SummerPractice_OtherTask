package com.example.demo.service;

import com.example.demo.dto.VisitorRequestDTO;
import com.example.demo.dto.VisitorResponseDTO;
import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class VisitorServiceTest {

    @Mock
    private VisitorRepository repository;

    @InjectMocks
    private VisitorService service;

    public VisitorServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveVisitor() {
        VisitorRequestDTO dto = new VisitorRequestDTO("Иван", 25, "М");
        Visitor saved = new Visitor(1L, "Иван", 25, "М");

        when(repository.save(any(Visitor.class))).thenReturn(saved);

        VisitorResponseDTO result = service.create(dto);

        assertEquals("Иван", result.getName());
        verify(repository).save(any(Visitor.class));
    }


    @Test
    void getAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(new Visitor(1L, "Аня", 30, "Ж")));
        assertEquals(1, service.getAll().size());
    }

    @Test
    void delete_shouldRemoveById() {
        service.delete(1L);
        verify(repository).deleteById(1L);
    }
    }
