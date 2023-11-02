package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Admin;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class AdminRepository {
    private final ObjectMapper objectMapper;

    private AdminService service;

    public AdminRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "admin/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(AdminService.class);
    }

    private <T> T getData(Response<ResponseResult<T>> execute) throws IOException {
        if (execute.code() == 400) {
            String message = objectMapper.readValue(execute.errorBody().string(),
                    new TypeReference<ResponseResult<T>>() {
                    }).getMessage();
            throw new IllegalArgumentException(message);
        }
        return execute.body().getData();
    }

    public Admin post(Admin admin) throws IOException {
        Response<ResponseResult<Admin>> execute = this.service.post(admin).execute();
        return getData(execute);
    }

    public List<Admin> getAll(String authHeader) throws IOException {
        return service.getAll(authHeader).execute().body().getData();
    }

    public Admin get(long id, String authHeader) throws IOException {
        Response<ResponseResult<Admin>> execute = service.get(id, authHeader).execute();
        return getData(execute);
    }

    public Admin delete(long id, String authHeader) throws IOException {
        Response<ResponseResult<Admin>> execute = service.delete(id, authHeader).execute();
        return getData(execute);
    }


}
