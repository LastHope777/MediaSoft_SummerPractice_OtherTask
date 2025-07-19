package com.example.demo.service;

import com.example.demo.dto.RestaurantRequestDTO;
import com.example.demo.dto.RestaurantResponseDTO;
import com.example.demo.model.CuisineType;
import com.example.demo.model.Restaurant;
import com.example.demo.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository repository;

    @InjectMocks
    private RestaurantService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveRestaurantAndReturnDTO() {
        Long id = 1L;

        Restaurant old = new Restaurant(id, "Old", "Desc", CuisineType.RUSSIAN, BigDecimal.TEN, BigDecimal.ZERO);
        RestaurantRequestDTO dto = new RestaurantRequestDTO("New Name", "New Desc", CuisineType.EUROPEAN, BigDecimal.valueOf(500));

        Restaurant updated = new Restaurant(id, dto.getName(), dto.getDescription(), dto.getCuisineType(), dto.getAverageBill(), BigDecimal.ZERO);

        when(repository.findById(id)).thenReturn(Optional.of(old));
        when(repository.save(any(Restaurant.class))).thenReturn(updated);

        var result = service.update(id, dto);

        assertEquals("New Name", result.getName());
        assertEquals("New Desc", result.getDescription());
        verify(repository).save(any(Restaurant.class));
    }

    @Test
    void getAll_shouldReturnListOfRestaurants() {
        Restaurant r = new Restaurant(1L, "A", "B", CuisineType.RUSSIAN,
                BigDecimal.ONE, BigDecimal.ZERO);
        when(repository.findAll()).thenReturn(List.of(r));

        List<RestaurantResponseDTO> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("A", result.get(0).getName());
    }

    @Test
    void delete_shouldRemoveById() {
        service.delete(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void update_shouldModifyRestaurantAndReturnDTO() {
        Restaurant existing = new Restaurant(1L, "Old", "Old desc", CuisineType.CHINESE,
                new BigDecimal("500"), BigDecimal.ZERO);

        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "New Name", "New Desc", CuisineType.ITALIAN, new BigDecimal("700"));

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Restaurant.class))).thenReturn(existing);

        RestaurantResponseDTO updated = service.update(1L, dto);

        assertNotNull(updated);
        assertEquals("New Name", updated.getName());
        verify(repository).save(any(Restaurant.class));
    }
}
