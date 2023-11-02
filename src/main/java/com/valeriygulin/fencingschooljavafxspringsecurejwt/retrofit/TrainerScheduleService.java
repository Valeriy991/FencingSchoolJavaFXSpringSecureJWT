package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.TrainerSchedule;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalTime;

public interface TrainerScheduleService {

    @POST("{id}")
    Call<ResponseResult<TrainerSchedule>> post(@Path("id") long id, @Query("dayWeek") String dayWeek,
                                               @Query("start") LocalTime start, @Query("end") LocalTime end,
                                               @Header("Authorization") String authHeader);


    @GET("{id}")
    Call<ResponseResult<TrainerSchedule>> get(@Path("id") long id, @Header("Authorization") String authHeader);

    @GET("localTime/{id}")
    Call<ResponseResult<LocalTime[]>> get(@Path("id") long id, @Query("date") String date,
                                          @Header("Authorization") String authHeader);

    @DELETE("{id}")
    Call<ResponseResult<TrainerSchedule>> deleteById(@Path("id") long id,
                                                     @Header("Authorization") String authHeader);

    @DELETE("{id}")
    Call<ResponseResult<TrainerSchedule>> deleteByIdAndDayWeek(@Path("id") long id, @Query("dayWeek") String dayWeek,
                                                               @Header("Authorization") String authHeader);

}
