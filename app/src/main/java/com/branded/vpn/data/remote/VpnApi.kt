package com.branded.vpn.data.remote

import com.branded.vpn.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface VpnApi {
    @POST("v1/auth/login")
    suspend fun login(@Body body: Map<String, String>): Response<LoginResponse>

    @POST("v1/auth/signup")
    suspend fun signup(@Body body: Map<String, String>): Response<LoginResponse>

    @GET("v1/nodes")
    suspend fun getNodes(@Header("Authorization") token: String): Response<List<NodeResponse>>

    @GET("v1/user/subscription")
    suspend fun getSubscription(@Header("Authorization") token: String): Response<SubscriptionResponse>

    @POST("v1/user/subscription/refresh")
    suspend fun refreshSubscription(@Header("Authorization") token: String): Response<SubscriptionResponse>
}
