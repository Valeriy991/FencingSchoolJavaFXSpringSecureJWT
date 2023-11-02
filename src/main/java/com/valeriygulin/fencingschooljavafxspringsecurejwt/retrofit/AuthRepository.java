package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Map;

public class AuthRepository {
    private final ObjectMapper objectMapper;

    private AuthService service;

    public AuthRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "auth/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(AuthService.class);
    }

    private <T> T getData(Response<ResponseResult<T>> execute) throws IOException {
        if (execute.code() == 400) {
            String message = objectMapper.readValue(execute.errorBody().string(),
                    new TypeReference<ResponseResult<T>>() {
                    }).getMessage();
            throw new IllegalArgumentException(message);
        } else if (execute.code() == 403) {
            throw new IllegalArgumentException("Неверный логин и пароль!");
        }
        return execute.body().getData();
    }

    public Map<String, String> post(String userName, String password) throws IOException {
        Response<ResponseResult<Map<String, String>>> execute = this.service.post(userName, password).execute();
        return getData(execute);
    }

    public boolean tokenIsAlive(String token) throws IOException {
        Response<ResponseResult<Boolean>> execute = this.service.get(token).execute();
        return getData(execute);
    }
}
