package com.valeriygulin.fencingschooljavafxspringsecurejwt.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Role {
    private Long id;

    @NonNull
    private String name;


}
