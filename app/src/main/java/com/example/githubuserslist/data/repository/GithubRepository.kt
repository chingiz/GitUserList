package com.example.githubuserslist.data.repository

import com.example.githubuserslist.common.BaseApiResponse
import com.example.githubuserslist.common.NetworkResult
import com.example.githubuserslist.data.api.ApiInterface
import com.example.githubuserslist.data.model.GUserModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class GithubRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun getGithubUserList(
        since: Int,
        per_page: Int
    ): NetworkResult<List<GUserModel>> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                apiInterface.getGithubUserList(
                    since, per_page
                )
            }
        }
    }

    suspend fun getGithubUser(
        username: String
    ): NetworkResult<GUserModel> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                apiInterface.getGithubUser(username)
            }
        }
    }
}