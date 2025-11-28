package com.fipe.infrastructure.adapter.in.rest.dto;

public class BrandDTO {
    
    private String code;
    private String name;
    
    public BrandDTO() {
    }
    
    public BrandDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
