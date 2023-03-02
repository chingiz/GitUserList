package com.example.githubuserslist.ui.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserslist.common.NetworkResult
import com.example.githubuserslist.data.model.GUserModel
import com.example.githubuserslist.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserActivityViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    var gUser = MutableLiveData<GUserModel>()

    fun getGithubUserData(username: String) = viewModelScope.launch {
        when (val result = githubRepository.getGithubUser(username)) {
            is NetworkResult.Success -> {
                result.data.let {
                    gUser.postValue(it)
                }
            } else -> {}
        }
    }
}