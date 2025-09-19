package com.tripmate.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tripmate.navigation.Dest
import com.tripmate.core.ui.theme.CoralAccent

@Composable
fun AuthGate(nav: NavController) {
  var email by remember { mutableStateOf("") }
  var pass by remember { mutableStateOf("") }
  Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
    Text("Welcome Back", style = MaterialTheme.typography.headlineMedium)
    Spacer(Modifier.height(16.dp))
    OutlinedTextField(value=email,onValueChange={email=it}, label={Text("Email Address")}, singleLine=true)
    Spacer(Modifier.height(8.dp))
    OutlinedTextField(value=pass,onValueChange={pass=it}, label={Text("Password")}, singleLine=true, visualTransformation=PasswordVisualTransformation())
    Spacer(Modifier.height(12.dp))
    Button(onClick={ nav.navigate(Dest.Home.route) }, colors=ButtonDefaults.buttonColors(containerColor = CoralAccent), modifier=Modifier.fillMaxWidth()) { Text("Log In") }
    Spacer(Modifier.height(8.dp))
    OutlinedButton(onClick={ /* stub */ }, modifier=Modifier.fillMaxWidth()) { Text("Continue with Google (stub)") }
  }
}
