package com.tripmate.feature.explore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class AttractionUi(val id: String, val name: String)

@Composable
fun ExploreScreen(nav: NavController) {
  var q by remember { mutableStateOf("") }
  val items = listOf(
    AttractionUi("sigiriya","Sigiriya Rock Fortress"),
    AttractionUi("toth","Temple of the Tooth"),
    AttractionUi("ella","Ella Rock Hike"),
  )
  Column(Modifier.fillMaxSize().padding(16.dp)) {
    OutlinedTextField(value=q, onValueChange={q=it}, placeholder={ Text("Explore Sri Lanka") }, modifier=Modifier.fillMaxWidth())
    Spacer(Modifier.height(8.dp))
    LazyColumn {
      items(items.filter { it.name.contains(q, true) }) { a ->
        Card(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
          Column(Modifier.padding(12.dp)) {
            Text(a.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Button(onClick = { /* add to itinerary */ }) { Text("Add to itinerary") }
          }
        }
      }
    }
  }
}
