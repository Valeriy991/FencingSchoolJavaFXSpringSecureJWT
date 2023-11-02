package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Training;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalTime;
import java.util.List;

public interface TrainingService {

    @POST("{idApprentice}/{idTrainer}")
    Call<ResponseResult<Training>> post(@Path("idApprentice") long idApprentice, @Path("idTrainer") long idTrainer,
                                        @Query("numGym") long numGym,
                                        @Query("date") String date, @Query("timeStart") LocalTime timeStart,
                                        @Header("Authorization") String authHeader);


    @GET("{id}")
    Call<ResponseResult<Training>> get(@Path("id") long id, @Header("Authorization") String authHeader);

    @GET("./getByApprentice")
    Call<ResponseResult<List<Training>>> getByApprentice(@Query("id") long id,
                                                         @Header("Authorization") String authHeader);

    @GET("./getByTrainer")
    Call<ResponseResult<List<Training>>> getByTrainer(@Query("id") long id,
                                                      @Header("Authorization") String authHeader);

    @DELETE("{id}")
    Call<ResponseResult<Training>> delete(@Path("id") long id,
                                          @Header("Authorization") String authHeader);
}
