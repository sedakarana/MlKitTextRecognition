package com.sedakarana.navigation.navigation

sealed class Screen(val route: String) { //Oluşturulan ekranların yönetimini sağlar
    object HomeScreen: Screen("home_screen")
    object ImageScreen: Screen("image_screen")
}