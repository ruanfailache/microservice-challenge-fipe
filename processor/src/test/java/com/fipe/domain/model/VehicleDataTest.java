package com.fipe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleDataTest {
    
    @Test
    void shouldCreateVehicleDataWithValidData() {
        // Given
        String brandCode = "1";
        String brandName = "Fiat";
        String code = "001001-1";
        String model = "Palio 1.0";
        
        // When
        VehicleData vehicleData = new VehicleData(brandCode, brandName, code, model);
        
        // Then
        assertNotNull(vehicleData);
        assertNull(vehicleData.getId());
        assertEquals(brandCode, vehicleData.getBrandCode());
        assertEquals(brandName, vehicleData.getBrandName());
        assertEquals(code, vehicleData.getCode());
        assertEquals(model, vehicleData.getModel());
        assertNotNull(vehicleData.getCreatedAt());
    }
    
    @Test
    void shouldThrowExceptionWhenBrandCodeIsNull() {
        // When & Then
        assertThrows(NullPointerException.class, 
                () -> new VehicleData(null, "Fiat", "001", "Palio"));
    }
    
    @Test
    void shouldThrowExceptionWhenCodeIsNull() {
        // When & Then
        assertThrows(NullPointerException.class, 
                () -> new VehicleData("1", "Fiat", null, "Palio"));
    }
    
    @Test
    void shouldBeEqualWhenBrandCodeAndCodeAreTheSame() {
        // Given
        VehicleData data1 = new VehicleData("1", "Fiat", "001", "Palio 1.0");
        VehicleData data2 = new VehicleData("1", "Fiat", "001", "Palio 1.4");
        
        // When & Then
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }
}
