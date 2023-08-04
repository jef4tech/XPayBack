package com.example.xpayback.network

import com.example.xpayback.models.UserData
import com.example.xpayback.models.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author jeffin
 * @date 30/01/23
 */
interface RestApis {

    @GET("users")
    suspend fun  getAllUser(@Query("limit") limit: Int,
                           @Query("skip") skip: Int
    ): Response<UsersResponse>


    @GET("users/{userid}")
    suspend fun getUserData(@Path("userid") id: Int): Response<UserData>


}
