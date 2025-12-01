package com.fipe.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "code")
@ToString
public class Brand {
    private String code;
    private String name;
}
