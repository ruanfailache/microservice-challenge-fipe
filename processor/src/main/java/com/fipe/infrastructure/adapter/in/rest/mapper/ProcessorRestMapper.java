package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.VehicleData;
import com.fipe.infrastructure.adapter.in.rest.dto.request.ProcessorUpdateVehicleOutRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessorBrandOutResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessorVehicleOutResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProcessorRestMapper {

    ProcessorBrandOutResponse toBrandResponse(VehicleData vehicleData);

    ProcessorBrandOutResponse toBrandResponse(Brand brand);

    ProcessorVehicleOutResponse toVehicleResponse(VehicleData vehicleData);

    List<ProcessorVehicleOutResponse> toVehicleResponseList(List<VehicleData> vehicleDataList);

    default void updateVehicleDataFromRequest(VehicleData vehicleData, ProcessorUpdateVehicleOutRequest request) {
        if (vehicleData == null || request == null) {
            return;
        }

        vehicleData.setModel(request.model());
        vehicleData.setObservations(request.observations());
    }
}
