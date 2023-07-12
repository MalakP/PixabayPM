package com.example.pixabaypm.ui.navigation

/**
 * Created by Piotr.Malak on 10/07/2023.

 */
interface PixabayDestinations {
    val route: String
}

object Search : PixabayDestinations {
    override val route = "search"
}

object Details : PixabayDestinations {
    override val route = "details"
}

object App : PixabayDestinations {
    override val route = "app"
}