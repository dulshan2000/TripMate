package com.tripmate.feature.plan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Itin(val id:String, val title:String)

@Composable
fun PlanScreen(nav: NavController) {
  var items by remember { mutableStateOf(listOf(
    Itin("1","Sigiriya 08:00"), Itin("2","Yala 06:00"), Itin("3","Ella 14:00")
  )) }
  Column(Modifier.fillMaxSize().padding(16.dp)) {
    Text("Plan", style = MaterialTheme.typography.headlineSmall)
    LazyColumn {
      items(items, key={it.id}) { it ->
        Card(Modifier.fillMaxWidth().padding(vertical=6.dp)) {
          Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(it.title)
            Row {
              TextButton(onClick = { /* move up stub */ }) { Text("↑") }
              TextButton(onClick = { /* move down stub */ }) { Text("↓") }
            }
          }
        }
      }
    }
    Spacer(Modifier.height(8.dp))
    Card(Modifier.fillMaxWidth()) {
      Column(Modifier.padding(12.dp)) {
        Text("Trip Summary", style = MaterialTheme.typography.titleMedium)
        Text("Days: 5  • Places: 5  • Est. Cost: $125  • Best Season: Dec-Apr")
      }
    }
  }
}
