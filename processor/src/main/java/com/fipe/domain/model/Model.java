package com.fipe.domain.model;

import java.util.Objects;

public class Model {
    
    private final String code;
    private final String name;
    
    public Model(String code, String name) {
        this.code = Objects.requireNonNull(code, "Code cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }
    
    public String getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(code, model.code);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
    
    @Override
    public String toString() {
        return "Model{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
