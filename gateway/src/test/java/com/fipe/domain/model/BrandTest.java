package com.fipe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrandTest {
    
    @Test
    void shouldCreateBrandWithValidData() {
        // Given
        String code = "1";
        String name = "Fiat";
        
        // When
        Brand brand = new Brand(code, name);
        
        // Then
        assertNotNull(brand);
        assertEquals(code, brand.getCode());
        assertEquals(name, brand.getName());
    }
    
    @Test
    void shouldThrowExceptionWhenCodeIsNull() {
        // Given
        String code = null;
        String name = "Fiat";
        
        // When & Then
        assertThrows(NullPointerException.class, () -> new Brand(code, name));
    }
    
    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        // Given
        String code = "1";
        String name = null;
        
        // When & Then
        assertThrows(NullPointerException.class, () -> new Brand(code, name));
    }
    
    @Test
    void shouldBeEqualWhenCodeIsTheSame() {
        // Given
        Brand brand1 = new Brand("1", "Fiat");
        Brand brand2 = new Brand("1", "Volkswagen");
        
        // When & Then
        assertEquals(brand1, brand2);
        assertEquals(brand1.hashCode(), brand2.hashCode());
    }
    
    @Test
    void shouldNotBeEqualWhenCodeIsDifferent() {
        // Given
        Brand brand1 = new Brand("1", "Fiat");
        Brand brand2 = new Brand("2", "Fiat");
        
        // When & Then
        assertNotEquals(brand1, brand2);
    }
}
