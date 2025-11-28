package com.fipe.infrastructure.adapter.out.client.response;

public class FipeBrandResponse {
    
    private String codigo;
    private String nome;
    
    public FipeBrandResponse() {
    }
    
    public FipeBrandResponse(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }
    
    public String getCode() {
        return codigo;
    }
    
    public void setCode(String codigo) {
        this.codigo = codigo;
    }
    
    public String getName() {
        return nome;
    }
    
    public void setName(String nome) {
        this.nome = nome;
    }
}
