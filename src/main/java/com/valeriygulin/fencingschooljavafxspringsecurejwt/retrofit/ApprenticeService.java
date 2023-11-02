package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Apprentice;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApprenticeService {
    @POST(".")
    Call<ResponseResult<Apprentice>> post(@Body Apprentice apprentice);

    @GET(".")
    Call<ResponseResult<List<Apprentice>>> getAll(@Header("Authorization") String authHeader);

    @GET("{id}")
    Call<ResponseResult<Apprentice>> get(@Path("id") long id, @Header("Authorization") String authHeader);

    @PUT(".")
    Call<ResponseResult<Apprentice>> put(@Body Apprentice apprentice, @Header("Authorization") String authHeader);

    @DELETE("{id}")
    Call<ResponseResult<Apprentice>> delete(@Path("id") long id, @Header("Authorization") String authHeader);
}
