package com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;

import java.io.IOException;

public interface ControllerData<T> {
    void initData(T value) throws IOException;
}
