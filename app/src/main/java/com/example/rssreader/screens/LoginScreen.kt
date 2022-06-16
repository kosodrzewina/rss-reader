package com.example.rssreader.screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rssreader.FeedActivity
import com.example.rssreader.MainActivity
import com.example.rssreader.R
import com.google.firebase.auth.FirebaseAuth

const val TAG = "LOGIN_SCREEN"

@Composable
fun LoginScreen(
    auth: FirebaseAuth,
    mainActivity: MainActivity,
    navHostController: NavHostController
) {
    if (auth.currentUser != null) {
        goToFeed(mainActivity)
    }

    var loginValue by remember {
        mutableStateOf("")
    }
    var passwordValue by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .scrollable(
                rememberScrollState(),
                orientation = Orientation.Vertical
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "RSS Reader",
            fontSize = 48.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.firebase),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .weight(1f)
        )
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, bottom = 64.dp)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = loginValue,
                    onValueChange = { loginValue = it },
                    label = { Text(text = "Login") },
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = passwordValue,
                    onValueChange = { passwordValue = it },
                    label = { Text(text = "Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else
                                    Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                )
                Row {
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        enabled = loginValue.isNotEmpty() && passwordValue.isNotEmpty(),
                        onClick = {
                            onRegister(
                                loginValue,
                                passwordValue,
                                auth,
                                mainActivity,
                            )
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Register")
                    }
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        enabled = loginValue.isNotEmpty() && passwordValue.isNotEmpty(),
                        onClick = {
                            onLogin(
                                loginValue,
                                passwordValue,
                                auth,
                                mainActivity,
                            )
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Log in")
                    }
                }
            }
        }
    }
}

private fun onRegister(
    login: String,
    password: String,
    auth: FirebaseAuth,
    mainActivity: MainActivity
) {
    auth
        .createUserWithEmailAndPassword(login, password)
        .addOnSuccessListener {
            Toast.makeText(mainActivity, "Successful registration", Toast.LENGTH_SHORT).show()
            goToFeed(mainActivity)
        }
        .addOnFailureListener {
            Toast.makeText(mainActivity, "An error occurred", Toast.LENGTH_SHORT).show()
            Log.e(TAG, it.toString())
        }
}

private fun onLogin(
    login: String,
    password: String,
    auth: FirebaseAuth,
    mainActivity: MainActivity,
) {
    auth
        .signInWithEmailAndPassword(login, password)
        .addOnSuccessListener {
            Toast.makeText(mainActivity, "Successful login", Toast.LENGTH_SHORT).show()
            goToFeed(mainActivity)
        }
        .addOnFailureListener {
            Toast.makeText(mainActivity, "An error occurred", Toast.LENGTH_SHORT).show()
            Log.e(TAG, it.toString())
        }
}

fun goToFeed(mainActivity: MainActivity) {
    mainActivity.startActivity(Intent(mainActivity, FeedActivity::class.java))
    mainActivity.finish()
}
