package com.thiha.efficientweatherapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thiha.efficientweatherapp.screens.Search.SearchScreen
import com.thiha.efficientweatherapp.screens.about.AboutScreen
import com.thiha.efficientweatherapp.screens.favourite.FavoritesScreen
import com.thiha.efficientweatherapp.screens.main.MainScreen
import com.thiha.efficientweatherapp.screens.main.MainViewModel
import com.thiha.efficientweatherapp.screens.setting.SettingScreen
import com.thiha.efficientweatherapp.screens.splash.SplashScreen


@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherNavigation() {
    val  navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}", arguments = listOf(
            navArgument(name = "city"){
                type = NavType.StringType
            }
        )){ navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel  = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = city)
            }

        }
        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.FavouriteScreen.name){
            FavoritesScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name){
            SettingScreen(navController = navController)
        }
    }
}