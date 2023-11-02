package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Admin;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface AdminService {
    @POST(".")
    Call<ResponseResult<Admin>> post(@Body Admin admin);

    @GET(".")
    Call<ResponseResult<List<Admin>>> getAll(@Header("Authorization") String authHeader);

    @GET("{id}")
    Call<ResponseResult<Admin>> get(@Path("id") long id, @Header("Authorization") String authHeader);


    @DELETE("{id}")
    Call<ResponseResult<Admin>> delete(@Path("id") long id, @Header("Authorization") String authHeader);
}
