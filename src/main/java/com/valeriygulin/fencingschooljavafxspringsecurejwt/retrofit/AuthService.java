package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Map;

public interface AuthService {
    @POST(".")
    Call<ResponseResult<Map<String, String>>> post(@Query("userName") String userName, @Query("password") String password);

    @GET(".")
    Call<ResponseResult<Boolean>> get(@Query("token") String token);
}
