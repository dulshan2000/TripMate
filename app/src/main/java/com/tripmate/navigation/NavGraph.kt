package com.tripmate.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.tripmate.core.ui.theme.TripMateTheme
import com.tripmate.feature.auth.AuthGate
import com.tripmate.feature.home.HomeScreen
import com.tripmate.feature.explore.ExploreScreen
import com.tripmate.feature.plan.PlanScreen
import com.tripmate.feature.community.CommunityScreen
import com.tripmate.feature.profile.ProfileScreen
import com.tripmate.R

sealed class Dest(val route: String, val label: String, @DrawableRes val icon: Int) {
  data object Home: Dest("home","Home", android.R.drawable.ic_menu_mylocation)
  data object Explore: Dest("explore","Explore", android.R.drawable.ic_menu_search)
  data object Plan: Dest("plan","Plan", android.R.drawable.ic_menu_my_calendar)
  data object Community: Dest("community","Community", android.R.drawable.ic_menu_share)
  data object Profile: Dest("profile","Profile", android.R.drawable.ic_menu_myplaces)
  data object PostDetail: Dest("post/{id}","Post", android.R.drawable.ic_menu_view) {
    fun of(id: String) = "post/$id"
  }
  data object Auth: Dest("auth","Auth", android.R.drawable.ic_lock_lock)
}

@Composable
fun TripMateApp() {
  TripMateTheme {
    val nav = rememberNavController()
    Scaffold(
      bottomBar = {
        val currentRoute = currentRoute(nav)
        if (currentRoute != Dest.Auth.route) {
          BottomBar(nav, currentRoute)
        }
      }
    ) { padding ->
      NavHost(nav, startDestination = Dest.Auth.route, modifier = Modifier.padding(padding)) {
        composable(Dest.Auth.route) { AuthGate(nav) }
        composable(Dest.Home.route) { HomeScreen(nav) }
        composable(Dest.Explore.route) { ExploreScreen(nav) }
        composable(Dest.Plan.route) { PlanScreen(nav) }
        composable(Dest.Community.route) { CommunityScreen(nav) }
        composable(Dest.Profile.route) { ProfileScreen(nav) }
        composable(Dest.PostDetail.route) { backStack ->
          val id = backStack.arguments?.getString("id") ?: ""
          Text("Post details for $id")
        }
      }
    }
  }
}

@Composable private fun BottomBar(nav: NavHostController, current: String?) {
  val items = listOf(Dest.Home, Dest.Explore, Dest.Plan, Dest.Community, Dest.Profile)
  NavigationBar {
    items.forEach { d ->
      NavigationBarItem(
        selected = d.route == current,
        onClick = {
          nav.navigate(d.route) {
            popUpTo(nav.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
          }
        },
        icon = { Icon(painterResource(id = d.icon), contentDescription = d.label) },
        label = { Text(d.label) },
        colors = NavigationBarItemDefaults.colors()
      )
    }
  }
}

@Composable private fun currentRoute(nav: NavHostController): String? {
  val backStackEntry by nav.currentBackStackEntryAsState()
  return backStackEntry?.destination?.route
}
