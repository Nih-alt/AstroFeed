package com.apprication.astrofeed

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.apprication.astrofeed.model.ApodResponse
import com.apprication.astrofeed.ui.theme.ApodDetailScreen
import com.apprication.astrofeed.ui.theme.ApodListScreen
import com.apprication.astrofeed.ui.theme.AstroFeedTheme
import com.apprication.astrofeed.viewmodel.ThemeViewModel
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            AstroFeedTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "apod_list") {
                    composable("apod_list") {
                        ApodListScreen(navController = navController, themeViewModel = themeViewModel)
                    }
                    composable(
                        "apod_detail/{apodJson}",
                        arguments = listOf(navArgument("apodJson") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val apodJson = backStackEntry.arguments?.getString("apodJson")
                        val apod = Gson().fromJson(apodJson, ApodResponse::class.java)
                        ApodDetailScreen(apod)
                    }
                }
            }
        }

    }
}
