package com.example.githubuserslist.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.githubuserslist.app.theme.GithubUsersListTheme
import com.example.githubuserslist.data.model.GUserModel
import com.example.githubuserslist.ui.user.UserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubUsersListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }


    @Composable
    fun MyApp() {
        val gUsers = viewModel.gUserList
        MaterialTheme {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "TimberContributorList")
                    },
                )

                if (gUsers.isEmpty()) {
                    LoadingState()
                } else {
                    UsersListContent(gUsers)
                }
            }
        }
    }

    @Composable
    fun UsersListContent(gUsers: SnapshotStateList<GUserModel>) {
        val listState = rememberLazyListState()
        Column {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
                if ((gUsers.size - 1) - listState.firstVisibleItemIndex == listState.layoutInfo.visibleItemsInfo.size - 1) {
                    viewModel.getGithubUsersList()
                }
                items(items = gUsers, itemContent = {
                    GithubUserListItem(item = it)
                })
            }
        }
    }

    @Composable
    fun GithubUserListItem(item: GUserModel) {
        val mContext = LocalContext.current
        Card(
            elevation = 4.dp, modifier = Modifier
                .clickable {
                    val i = Intent(mContext, UserActivity::class.java)
                    i.putExtra("username", item.login)
                    mContext.startActivity(i)
                }
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = item.avatar_url,
                    contentDescription = "avatar",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(48.dp)
                        .border(BorderStroke(1.dp, Color.Red)),
                    contentScale = ContentScale.Fit
                )
                Column {
                    Text(item.login)
                    Text("${item.id}")
                }
            }
        }
    }

    @Composable
    fun LoadingState() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Text(text = "Loading...", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GithubUsersListTheme {
        Greeting("Android")
    }
}