package com.apprication.astrofeed.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.apprication.astrofeed.model.ApodResponse
import com.apprication.astrofeed.viewmodel.BookmarkViewModel
import androidx.compose.runtime.getValue
import com.apprication.astrofeed.model.toEntity
import android.content.Intent
import androidx.compose.material.icons.filled.Share


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApodDetailScreen(apod: ApodResponse, bookmarkViewModel: BookmarkViewModel = viewModel()) {
    val isBookmarked by bookmarkViewModel.isBookmarked.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(apod.date) {
        bookmarkViewModel.checkBookmark(apod.date)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = apod.title, maxLines = 1, style = MaterialTheme.typography.h6
                    )
                },
                actions = {
                    // ⭐ Bookmark
                    IconButton(onClick = {
                        bookmarkViewModel.toggleBookmark(apod.toEntity())
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

                    // 📤 Share
                    IconButton(onClick = {
                        val shareText = buildString {
                            append("📅 ${apod.date}\n")
                            append("🌌 ${apod.title}\n\n")
                            append("${apod.explanation}\n\n")
                            if (apod.media_type == "image") {
                                append("🖼 ${apod.url}")
                            }
                        }

                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }

                        val chooser = Intent.createChooser(intent, "Share this APOD via")
                        context.startActivity(chooser)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colors.surface,
                    titleContentColor = MaterialTheme.colors.onSurface,
                    actionIconContentColor = MaterialTheme.colors.onSurface
                )
            )

        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = rememberAsyncImagePainter(apod.url), contentDescription = "NASA Image", modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = apod.date, style = MaterialTheme.typography.caption
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = apod.explanation, style = MaterialTheme.typography.body1
            )
        }
    }
}