package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Apprentice;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ApprenticeRepository {
    private final ObjectMapper objectMapper;

    private ApprenticeService service;

    public ApprenticeRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "apprentice/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(ApprenticeService.class);
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

    public Apprentice post(Apprentice apprentice) throws IOException {
        Response<ResponseResult<Apprentice>> execute = this.service.post(apprentice).execute();
        return getData(execute);
    }

    public List<Apprentice> getAll(String authHeader) throws IOException {
        return service.getAll(authHeader).execute().body().getData();
    }

    public Apprentice get(long id, String authHeader) throws IOException {
        Response<ResponseResult<Apprentice>> execute = service.get(id, authHeader).execute();
        return getData(execute);
    }

    public Apprentice delete(long id, String authHeader) throws IOException {
        Response<ResponseResult<Apprentice>> execute = service.delete(id, authHeader).execute();
        return getData(execute);
    }

    public Apprentice put(Apprentice apprentice, String authHeader) throws IOException {
        Response<ResponseResult<Apprentice>> execute = this.service.put(apprentice, authHeader).execute();
        return getData(execute);
    }

}
