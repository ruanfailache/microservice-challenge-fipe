package com.fipe.application.usecase;

import com.fipe.domain.exception.InitialLoadException;
import com.fipe.domain.model.Brand;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.domain.port.out.publisher.VehicleDataPublisherPort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class InitialLoadUseCaseImplTest {
    
    @Inject
    InitialLoadUseCaseImpl initialLoadUseCase;
    
    @InjectMock
    FipeClientPort fipeClientPort;
    
    @InjectMock
    VehicleDataPublisherPort vehicleDataPublisherPort;
    
    @BeforeEach
    void setUp() {
        Mockito.reset(fipeClientPort, vehicleDataPublisherPort);
    }
    
    @Test
    void shouldExecuteInitialLoadSuccessfully() {
        // Given
        List<Brand> brands = Arrays.asList(
                new Brand("1", "Fiat"),
                new Brand("2", "Volkswagen"),
                new Brand("3", "Ford")
        );
        
        when(fipeClientPort.fetchAllBrands()).thenReturn(brands);
        doNothing().when(vehicleDataPublisherPort).publishBrandForProcessing(any(Brand.class));
        
        // When
        int result = initialLoadUseCase.executeInitialLoad();
        
        // Then
        assertEquals(3, result);
        verify(fipeClientPort, times(1)).fetchAllBrands();
        verify(vehicleDataPublisherPort, times(3)).publishBrandForProcessing(any(Brand.class));
    }
    
    @Test
    void shouldReturnZeroWhenNoBrandsFound() {
        // Given
        when(fipeClientPort.fetchAllBrands()).thenReturn(Collections.emptyList());
        
        // When
        int result = initialLoadUseCase.executeInitialLoad();
        
        // Then
        assertEquals(0, result);
        verify(fipeClientPort, times(1)).fetchAllBrands();
        verify(vehicleDataPublisherPort, never()).publishBrandForProcessing(any(Brand.class));
    }
    
    @Test
    void shouldThrowInitialLoadExceptionWhenFipeClientFails() {
        // Given
        when(fipeClientPort.fetchAllBrands()).thenThrow(new RuntimeException("API error"));
        
        // When & Then
        assertThrows(InitialLoadException.class, () -> initialLoadUseCase.executeInitialLoad());
        verify(fipeClientPort, times(1)).fetchAllBrands();
        verify(vehicleDataPublisherPort, never()).publishBrandForProcessing(any(Brand.class));
    }
    
    @Test
    void shouldContinueProcessingWhenPublishingOneBrandFails() {
        // Given
        List<Brand> brands = Arrays.asList(
                new Brand("1", "Fiat"),
                new Brand("2", "Volkswagen"),
                new Brand("3", "Ford")
        );
        
        when(fipeClientPort.fetchAllBrands()).thenReturn(brands);
        doNothing()
                .doThrow(new RuntimeException("Kafka error"))
                .doNothing()
                .when(vehicleDataPublisherPort).publishBrandForProcessing(any(Brand.class));
        
        // When
        int result = initialLoadUseCase.executeInitialLoad();
        
        // Then
        assertEquals(2, result); // 2 out of 3 should succeed
        verify(fipeClientPort, times(1)).fetchAllBrands();
        verify(vehicleDataPublisherPort, times(3)).publishBrandForProcessing(any(Brand.class));
    }
}
