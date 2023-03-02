package com.example.githubuserslist.ui.user

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.githubuserslist.app.theme.GithubUsersListTheme
import com.example.githubuserslist.data.model.GUserModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : ComponentActivity() {
    private val viewModel: UserActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.extras?.getString("username")!!
        viewModel.getGithubUserData(username = username)
        setContent {
            GithubUsersListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val user: State<GUserModel?> = viewModel.gUser.observeAsState()
                    user.value?.let { MyApp(it) }
                }
            }
        }
    }

    @Composable
    fun MyApp(user: GUserModel) {
        MaterialTheme {
            Column {
                TopAppBar(
                    title = {
                        Text(text = user.login)
                    },
                )
                UserCard(user = user)
            }
        }
    }

    @Composable
    fun UserCard(
        user: GUserModel
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            AsyncImage(
                model = user.avatar_url,
                contentDescription = "avatar",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(100.dp)
                    .border(BorderStroke(1.dp, Color.Red)),
                contentScale = ContentScale.Fit
            )
            Column {
                Text(text = user.login)
                Text(text = user.company)
                Text(text = user.getAccountCreation().toString())
                Row {
                    Text("Followers:")
                    Text(text = "${user.followers}")
                }
            }
        }

    }

}