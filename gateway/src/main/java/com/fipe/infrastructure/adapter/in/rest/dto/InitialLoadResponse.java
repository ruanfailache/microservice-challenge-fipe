package com.fipe.infrastructure.adapter.in.rest.dto;

public class InitialLoadResponse {
    
    private String message;
    private Integer brandsProcessed;
    private String status;
    
    public InitialLoadResponse() {
    }
    
    public InitialLoadResponse(String message, Integer brandsProcessed, String status) {
        this.message = message;
        this.brandsProcessed = brandsProcessed;
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Integer getBrandsProcessed() {
        return brandsProcessed;
    }
    
    public void setBrandsProcessed(Integer brandsProcessed) {
        this.brandsProcessed = brandsProcessed;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
