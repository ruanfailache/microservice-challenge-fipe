package com.fipe.application.usecase;

import com.fipe.domain.model.Model;
import com.fipe.domain.model.VehicleData;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.domain.port.out.repository.VehicleDataRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessVehicleDataUseCaseImplTest {
    
    @InjectMocks
    private ProcessVehicleDataUseCaseImpl processVehicleDataUseCase;
    
    @Mock
    private FipeClientPort fipeClientPort;
    
    @Mock
    private VehicleDataRepositoryPort vehicleDataRepositoryPort;
    
    @BeforeEach
    void setUp() {
        // Mocks are automatically reset by MockitoExtension
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
        processVehicleDataUseCase.processVehicleData(brandCode, brandName);
        
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
        processVehicleDataUseCase.processVehicleData(brandCode, brandName);
        
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
        processVehicleDataUseCase.processVehicleData(brandCode, brandName);
        
        // Then
        verify(vehicleDataRepositoryPort, times(2)).exists(anyString(), anyString());
        verify(vehicleDataRepositoryPort, times(1)).save(any(VehicleData.class));
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
        processVehicleDataUseCase.processVehicleData(brandCode, brandName);
        
        // Then
        verify(vehicleDataRepositoryPort, times(3)).save(any(VehicleData.class));
    }
}
