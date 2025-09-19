package com.tripmate.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(nav: NavController) {
  Column(Modifier.fillMaxSize().padding(16.dp)) {
    Text("Dulshan Perera", style = MaterialTheme.typography.headlineSmall)
    Text("Travel enthusiast exploring Sri Lanka")
    Spacer(Modifier.height(12.dp))
    Text("Trips Planned 12   • Reviews Written 5   • Friends 8")
    Spacer(Modifier.height(12.dp))
    Text("My Posts")
  }
}
