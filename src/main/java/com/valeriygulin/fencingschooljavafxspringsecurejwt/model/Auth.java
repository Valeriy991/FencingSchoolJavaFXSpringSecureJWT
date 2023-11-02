package com.valeriygulin.fencingschooljavafxspringsecurejwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Auth {
    private Map<String, String> data;
}
