package com.example.githubuserslist.data.api

import com.example.githubuserslist.data.model.GUserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("users")
    suspend fun getGithubUserList(
        @Query("since") since: Int,
        @Query("per_page") per_page: Int
    ): Response<List<GUserModel>>

    @GET("users/{username}")
    suspend fun getGithubUser(
        @Path("username") username: String
    ): Response<GUserModel>
}