package com.valeriygulin.fencingschooljavafxspringsecurejwt.retrofit;

import com.valeriygulin.fencingschooljavafxspringsecurejwt.dto.ResponseResult;
import com.valeriygulin.fencingschooljavafxspringsecurejwt.model.Account;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface AccountService {
    @GET(".")
    Call<ResponseResult<List<Account>>> getAll();
}
