package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.TrainerSchedule;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.time.LocalTime;

public class TrainerScheduleRepository {

    private final ObjectMapper objectMapper;

    private TrainerScheduleService service;

    public TrainerScheduleRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "trainer_schedule/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(TrainerScheduleService.class);
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

    public TrainerSchedule post(long id, String dayWeek, LocalTime start, LocalTime end, String authHeader) throws IOException {
        Response<ResponseResult<TrainerSchedule>> execute = this.service.post(id, dayWeek, start, end, authHeader).execute();
        return getData(execute);
    }


    public TrainerSchedule get(long id, String authHeader) throws IOException {
        Response<ResponseResult<TrainerSchedule>> execute = service.get(id, authHeader).execute();
        return getData(execute);
    }

    public LocalTime[] get(long id, String date, String authHeader) throws IOException {
        Response<ResponseResult<LocalTime[]>> execute = service.get(id, date, authHeader).execute();
        return getData(execute);
    }

    public TrainerSchedule deleteById(long id, String authHeader) throws IOException {
        Response<ResponseResult<TrainerSchedule>> execute = service.deleteById(id, authHeader).execute();
        return getData(execute);
    }

    public TrainerSchedule deleteByIdAndDayWeek(long id, String dayWeek, String authHeader) throws IOException {
        Response<ResponseResult<TrainerSchedule>> execute = service.deleteByIdAndDayWeek(id, dayWeek, authHeader).execute();
        return getData(execute);
    }
}
