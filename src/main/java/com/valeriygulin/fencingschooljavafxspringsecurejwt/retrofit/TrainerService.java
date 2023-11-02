package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Trainer;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface TrainerService {
    @POST(".")
    Call<ResponseResult<Trainer>> post(@Body Trainer trainer, @Header("Authorization") String authHeader);

    @GET(".")
    Call<ResponseResult<List<Trainer>>> getAll(@Header("Authorization") String authHeader);

    @GET("{id}")
    Call<ResponseResult<Trainer>> get(@Path("id") long id, @Header("Authorization") String authHeader);

    @PUT(".")
    Call<ResponseResult<Trainer>> put(@Body Trainer trainer, @Header("Authorization") String authHeader);

    @DELETE("{id}")
    Call<ResponseResult<Trainer>> delete(@Path("id") long id, @Header("Authorization") String authHeader);
}
