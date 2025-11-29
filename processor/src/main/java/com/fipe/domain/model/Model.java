package com.fipe.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "code")
@ToString
public class Model {
    
    @NonNull
    private final String code;
    
    @NonNull
    private final String name;
}
