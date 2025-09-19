package com.tripmate.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(nav: NavController) {
  Column(Modifier.fillMaxSize().padding(16.dp)) {
    Text("Hello, Dulshan", style = MaterialTheme.typography.headlineSmall)
    OutlinedTextField(value="", onValueChange={}, placeholder={ Text("Where to next in Sri Lanka?") }, modifier=Modifier.fillMaxWidth())
    Spacer(Modifier.height(16.dp))
    Text("Featured Destinations", style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(8.dp))
    Text("Popular Attractions", style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(8.dp))
    Text("Upcoming Events", style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(8.dp))
    Text("Community Stories", style = MaterialTheme.typography.titleMedium)
  }
}
