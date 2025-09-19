package com.tripmate.feature.community

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CommunityScreen(nav: NavController) {
  var tab by remember { mutableStateOf(0) }
  Column(Modifier.fillMaxSize()) {
    TabRow(selectedTabIndex = tab) {
      Tab(selected = tab==0, onClick={tab=0}, text={ Text("Feed") })
      Tab(selected = tab==1, onClick={tab=1}, text={ Text("Groups") })
    }
    if (tab==0) {
      Column(Modifier.padding(16.dp)) { Text("Feed grid placeholder") }
    } else {
      Column(Modifier.padding(16.dp)) { Text("Groups placeholder") }
    }
  }
}
