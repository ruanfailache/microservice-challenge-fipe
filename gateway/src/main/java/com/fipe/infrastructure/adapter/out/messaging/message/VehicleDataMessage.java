package com.fipe.infrastructure.adapter.out.messaging.message;

public class VehicleDataMessage {
    
    private String brandCode;
    private String brandName;
    
    public VehicleDataMessage() {
    }
    
    public VehicleDataMessage(String brandCode, String brandName) {
        this.brandCode = brandCode;
        this.brandName = brandName;
    }
    
    public String getBrandCode() {
        return brandCode;
    }
    
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }
    
    public String getBrandName() {
        return brandName;
    }
    
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
