package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Training;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class TrainingRepository {

    private final ObjectMapper objectMapper;

    private TrainingService service;

    public TrainingRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "training/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(TrainingService.class);
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

    public Training post(long idApprentice, long idTrainer, long numGym, String date, LocalTime timeStart, String authHeader) throws IOException {
        Response<ResponseResult<Training>> execute = this.service.post(idApprentice, idTrainer, numGym, date, timeStart, authHeader).execute();
        return getData(execute);
    }

    public Training get(long id, String authHeader) throws IOException {
        Response<ResponseResult<Training>> execute = service.get(id, authHeader).execute();
        return getData(execute);
    }

    public List<Training> getByApprentice(long id, String authHeader) throws IOException {
        Response<ResponseResult<List<Training>>> execute = service.getByApprentice(id, authHeader).execute();
        return getData(execute);
    }

    public List<Training> getByTrainer(long id, String authHeader) throws IOException {
        Response<ResponseResult<List<Training>>> execute = service.getByTrainer(id, authHeader).execute();
        return getData(execute);
    }

    public Training delete(long id, String authHeader) throws IOException {
        Response<ResponseResult<Training>> execute = service.delete(id, authHeader).execute();
        return getData(execute);
    }
}
