package com.example.githubuserslist.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserslist.common.NetworkResult
import com.example.githubuserslist.data.model.GUserModel
import com.example.githubuserslist.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    val gUserList = mutableStateListOf<GUserModel>()

    var since by mutableStateOf(0)

    init {
        getGithubUsersList()
    }

    fun getGithubUsersList() = viewModelScope.launch {
        when (val result =
            githubRepository.getGithubUserList(since, 30)) {
            is NetworkResult.Success -> {
                result.data?.let {
                    if (since == 0) {
                        gUserList.clear()
                        gUserList.addAll(it)
                    } else {
                        gUserList.addAll(it)
                    }
                    since = gUserList.last().id
                }
            }
            else -> {}
        }
    }
}