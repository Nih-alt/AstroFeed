package com.apprication.astrofeed.ui.theme

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apprication.astrofeed.viewmodel.MultiApodViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.apprication.astrofeed.model.ApodResponse
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Switch
import androidx.navigation.NavController
import com.apprication.astrofeed.viewmodel.ThemeViewModel
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ApodListScreen(viewModel: MultiApodViewModel = viewModel(), navController: NavController, themeViewModel: ThemeViewModel) {
    val apods by viewModel.apods.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val isDark by themeViewModel.isDarkTheme.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AstroFeed") },
                actions = {
                    Switch(
                        checked = isDark,
                        onCheckedChange = { themeViewModel.toggleTheme() }
                    )
                }
            )
        }) { paddingValues ->
        if (loading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(apods) { item ->
                    ApodListItem(item) {
                        val json = Uri.encode(Gson().toJson(item))
                        navController.navigate("apod_detail/$json")
                    }
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ApodListItem(item: ApodResponse, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(item.url),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(item.title, style = MaterialTheme.typography.subtitle1)
            Text(item.date, style = MaterialTheme.typography.caption)
        }
    }
}


