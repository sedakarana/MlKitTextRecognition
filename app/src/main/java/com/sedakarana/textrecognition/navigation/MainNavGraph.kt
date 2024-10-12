package com.sedakarana.navigation.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.sedakarana.textrecognition.view.screen.HomeScreen
import com.sedakarana.textrecognition.view.screen.ImageScreen
import com.sedakarana.textrecognition.viewModel.HomeViewModel

@SuppressLint("UnsafeOptInUsageError")
@ExperimentalPermissionsApi
@Composable


fun MainNavGraph(navController: NavHostController) { //Ekran geçişlerini kontrol etmemizi sağlar.NavHostController erkanlar arası geçiş yapmamızı kontrol eden parametredir.
    val homeViewModel: HomeViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(route = Screen.ImageScreen.route) {
            ImageScreen(navController = navController, viewModel = homeViewModel)
        }


    }
}