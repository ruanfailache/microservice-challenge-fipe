package com.fipe.infrastructure.adapter.out.rest.adapter;

import com.fipe.domain.exception.ExternalServiceException;
import com.fipe.domain.model.Brand;
import com.fipe.domain.model.Vehicle;
import com.fipe.domain.port.out.client.ProcessorClientPort;
import com.fipe.infrastructure.adapter.out.rest.client.ProcessorClient;
import com.fipe.infrastructure.adapter.out.rest.dto.request.processor.ProcessorUpdateVehicleOutRequest;
import com.fipe.infrastructure.adapter.out.rest.dto.response.processor.ProcessorBrandOutResponse;
import com.fipe.infrastructure.adapter.out.rest.dto.response.processor.ProcessorVehicleOutResponse;
import com.fipe.infrastructure.adapter.out.rest.mapper.ProcessorBrandOutMapper;
import com.fipe.infrastructure.adapter.out.rest.mapper.ProcessorVehicleOutMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProcessorClientAdapter implements ProcessorClientPort {

    private static final Logger LOG = Logger.getLogger(ProcessorClientAdapter.class);

    @Inject
    @RestClient
    ProcessorClient processorClient;

    @Inject
    ProcessorVehicleOutMapper processorVehicleOutMapper;

    @Inject
    ProcessorBrandOutMapper processorBrandOutMapper;

    public List<Brand> getAllBrands(String authorization) {
        try {
            return processorClient.getAllBrands(authorization).stream()
                    .map(processorBrandOutMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Error fetching brands from FIPE API", e);
            throw new ExternalServiceException("Failed to fetch brands from PROCESSOR API", e);
        }
    }

    public Optional<Brand> getBrandByCode(String authorization, String code) {
        try {
            ProcessorBrandOutResponse response = processorClient.getBrandByCode(authorization, code);
            Brand brand = processorBrandOutMapper.toDomain(response);
            return Optional.of(brand);
        } catch (WebApplicationException e) {
            if (e.getResponse().getStatus() == 404) {
                return Optional.empty();
            }
            LOG.errorf(e, "Error fetching brand by code: %s", code);
            throw new ExternalServiceException("Failed to fetch brand by code from PROCESSOR API", e);
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching brand by code: %s", code);
            throw new ExternalServiceException("Failed to fetch brand by code from PROCESSOR API", e);
        }
    }

    public List<Vehicle> getVehiclesByBrandCode(String authorization, String brandCode) {
        try {
            return processorClient.getVehiclesByBrand(authorization, brandCode).stream()
                    .map(processorVehicleOutMapper::toDomain)
                    .toList();
        } catch (Exception e) {
            LOG.error("Error fetching brands from FIPE API", e);
            throw new ExternalServiceException("Failed to fetch vehicles by branch from PROCESSOR API", e);
        }
    }

    @Override
    public Vehicle updateVehicle(String authorization, String brandCode, ProcessorUpdateVehicleOutRequest request) {
        try {
            ProcessorVehicleOutResponse response = processorClient.updateVehicle(authorization, brandCode, request);
            return processorVehicleOutMapper.toDomain(response);
        } catch (Exception e) {
            LOG.error("Error fetching brands from FIPE API", e);
            throw new ExternalServiceException("Failed to update vehicle from PROCESSOR API", e);
        }
    }
}
