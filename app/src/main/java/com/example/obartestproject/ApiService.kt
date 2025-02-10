package com.example.obartestproject

import android.util.Base64
import com.example.obartestproject.model.FetchedUserInfoModel
import com.example.obartestproject.model.RegistrationResponse
import com.example.obartestproject.model.body.RegistrationBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("address")
    suspend fun sendUserInfo(
        @Header("Authorization") authHeader: String,
        @Body userLocationRequest: RegistrationBody,
    ): retrofit2.Response<RegistrationResponse>

    @Headers("Content-Type: application/json")
    @GET("address")
    suspend fun fetchUserInfo(
        @Header("Authorization") authHeader: String,
    ): retrofit2.Response<FetchedUserInfoModel>
}

object AuthUtil {
    fun getBasicAuthHeader(username: String, password: String): String {
        val credentials = "$username:$password"
        val base64Encoded = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        return "Basic $base64Encoded"
    }
}