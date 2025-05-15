package com.apprication.astrofeed.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.apprication.astrofeed.model.ApodEntity
import com.apprication.astrofeed.viewmodel.ApodViewModel
import com.apprication.astrofeed.viewmodel.BookmarkViewModel
import androidx.compose.material.icons.outlined.StarBorder


@Composable
fun ApodScreen(
    apodViewModel: ApodViewModel = viewModel(),
    bookmarkViewModel: BookmarkViewModel = viewModel()
) {
    val apod by apodViewModel.apodData.collectAsState()
    val isLoading by apodViewModel.isLoading.collectAsState()
    val error by apodViewModel.error.collectAsState()

    val isBookmarked by bookmarkViewModel.isBookmarked.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(apod?.date) {
        apod?.date?.let {
            bookmarkViewModel.checkBookmark(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = 12.dp),  // 👈 this is the new padding!
                title = {
                    Column {
                        Spacer(modifier = Modifier.height(4.dp))  // 👈 Push title text slightly down
                        Text(
                            text = apod?.title ?: "AstroFeed",
                            maxLines = 2,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface,
                elevation = 4.dp,
                actions = {
                    if (apod != null) {
                        IconButton(onClick = {
                            bookmarkViewModel.toggleBookmark(
                                ApodEntity(
                                    date = apod!!.date,
                                    title = apod!!.title,
                                    explanation = apod!!.explanation,
                                    url = apod!!.url,
                                    hdurl = apod!!.hdurl,
                                    media_type = apod!!.media_type
                                )
                            )
                            Toast.makeText(
                                context,
                                if (!isBookmarked) "Bookmarked!" else "Removed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Filled.Star else Icons.Outlined.StarBorder,
                                contentDescription = "Bookmark"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                error != null -> Text(
                    text = error ?: "Error",
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.Center)
                )

                apod != null -> {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        if (apod!!.media_type == "image") {
                            Image(
                                painter = rememberAsyncImagePainter(apod!!.url),
                                contentDescription = "NASA Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .padding(bottom = 12.dp)
                            )
                        }

                        Text(
                            text = apod!!.date,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Text(
                            text = apod!!.explanation,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }

}
