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
        VehicleData vehicleData = new VehicleData(null, brandCode, brandName, code, model, null, null, null);
        
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
                () -> new VehicleData(null, null, "Fiat", "001", "Palio", null, null, null));
    }
    
    @Test
    void shouldThrowExceptionWhenCodeIsNull() {
        // When & Then
        assertThrows(NullPointerException.class, 
                () -> new VehicleData(null, "1", "Fiat", null, "Palio", null, null, null));
    }
    
    @Test
    void shouldBeEqualWhenBrandCodeAndCodeAreTheSame() {
        // Given
        VehicleData data1 = new VehicleData(null, "1", "Fiat", "001", "Palio 1.0", null, null, null);
        VehicleData data2 = new VehicleData(null, "1", "Fiat", "001", "Palio 1.4", null, null, null);
        
        // When & Then
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }
}
