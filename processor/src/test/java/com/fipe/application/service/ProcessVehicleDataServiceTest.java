package com.fipe.application.service;

import com.fipe.domain.exception.VehicleDataProcessingException;
import com.fipe.domain.model.Model;
import com.fipe.domain.model.VehicleData;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.domain.port.out.repository.VehicleDataRepositoryPort;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class ProcessVehicleDataServiceTest {
    
    @Inject
    ProcessVehicleDataService processVehicleDataService;
    
    @InjectMock
    FipeClientPort fipeClientPort;
    
    @InjectMock
    VehicleDataRepositoryPort vehicleDataRepositoryPort;
    
    @BeforeEach
    void setUp() {
        Mockito.reset(fipeClientPort, vehicleDataRepositoryPort);
    }
    
    @Test
    void shouldProcessVehicleDataSuccessfully() {
        // Given
        String brandCode = "1";
        String brandName = "Fiat";
        
        List<Model> models = Arrays.asList(
                new Model("001", "Palio 1.0"),
                new Model("002", "Uno 1.0"),
                new Model("003", "Strada 1.4")
        );
        
        when(fipeClientPort.fetchModelsByBrand(brandCode)).thenReturn(models);
        when(vehicleDataRepositoryPort.exists(anyString(), anyString())).thenReturn(false);
        when(vehicleDataRepositoryPort.save(any(VehicleData.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        
        // When
        processVehicleDataService.processVehicleData(brandCode, brandName);
        
        // Then
        verify(fipeClientPort, times(1)).fetchModelsByBrand(brandCode);
        verify(vehicleDataRepositoryPort, times(3)).exists(anyString(), anyString());
        verify(vehicleDataRepositoryPort, times(3)).save(any(VehicleData.class));
    }
    
    @Test
    void shouldNotProcessWhenNoModelsFound() {
        // Given
        String brandCode = "1";
        String brandName = "Fiat";
        
        when(fipeClientPort.fetchModelsByBrand(brandCode)).thenReturn(Collections.emptyList());
        
        // When
        processVehicleDataService.processVehicleData(brandCode, brandName);
        
        // Then
        verify(fipeClientPort, times(1)).fetchModelsByBrand(brandCode);
        verify(vehicleDataRepositoryPort, never()).save(any(VehicleData.class));
    }
    
    @Test
    void shouldSkipExistingVehicleData() {
        // Given
        String brandCode = "1";
        String brandName = "Fiat";
        
        List<Model> models = Arrays.asList(
                new Model("001", "Palio 1.0"),
                new Model("002", "Uno 1.0")
        );
        
        when(fipeClientPort.fetchModelsByBrand(brandCode)).thenReturn(models);
        when(vehicleDataRepositoryPort.exists(brandCode, "001")).thenReturn(true);
        when(vehicleDataRepositoryPort.exists(brandCode, "002")).thenReturn(false);
        when(vehicleDataRepositoryPort.save(any(VehicleData.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        
        // When
        processVehicleDataService.processVehicleData(brandCode, brandName);
        
        // Then
        verify(vehicleDataRepositoryPort, times(2)).exists(anyString(), anyString());
        verify(vehicleDataRepositoryPort, times(1)).save(any(VehicleData.class));
    }
    
    @Test
    void shouldThrowExceptionWhenFipeClientFails() {
        // Given
        String brandCode = "1";
        String brandName = "Fiat";
        
        when(fipeClientPort.fetchModelsByBrand(brandCode))
                .thenThrow(new RuntimeException("API error"));
        
        // When & Then
        assertThrows(VehicleDataProcessingException.class, 
                () -> processVehicleDataService.processVehicleData(brandCode, brandName));
    }
    
    @Test
    void shouldContinueProcessingWhenSavingOneModelFails() {
        // Given
        String brandCode = "1";
        String brandName = "Fiat";
        
        List<Model> models = Arrays.asList(
                new Model("001", "Palio 1.0"),
                new Model("002", "Uno 1.0"),
                new Model("003", "Strada 1.4")
        );
        
        when(fipeClientPort.fetchModelsByBrand(brandCode)).thenReturn(models);
        when(vehicleDataRepositoryPort.exists(anyString(), anyString())).thenReturn(false);
        when(vehicleDataRepositoryPort.save(any(VehicleData.class)))
                .thenAnswer(invocation -> invocation.getArgument(0))
                .thenThrow(new RuntimeException("DB error"))
                .thenAnswer(invocation -> invocation.getArgument(0));
        
        // When
        processVehicleDataService.processVehicleData(brandCode, brandName);
        
        // Then
        verify(vehicleDataRepositoryPort, times(3)).save(any(VehicleData.class));
    }
}
