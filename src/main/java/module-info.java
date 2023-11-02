module com.example.fencingschooljavafxspringsecurejwt {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.annotation;
    requires spring.data.commons;
    requires java.prefs;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires okhttp3;
    requires retrofit2;
    requires retrofit2.converter.jackson;


    opens com.valeriygulin.fencingschooljavafxspringsecurejwt to javafx.fxml;
    opens com.valeriygulin.fencingschooljavafxspringsecurejwt.model to javafx.base;
    exports com.valeriygulin.fencingschooljavafxspringsecurejwt;
    exports com.valeriygulin.fencingschooljavafxspringsecurejwt.controller;
    opens com.valeriygulin.fencingschooljavafxspringsecurejwt.controller to javafx.fxml;
    exports com.valeriygulin.fencingschooljavafxspringsecurejwt.dto to com.fasterxml.jackson.databind;
    exports com.valeriygulin.fencingschooljavafxspringsecurejwt.model to com.fasterxml.jackson.databind;
}