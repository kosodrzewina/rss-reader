package com.example.rssreader.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
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
import com.example.rssreader.R
import com.google.firebase.auth.FirebaseAuth

const val TAG = "LOGIN_SCREEN"

@Composable
fun LoginScreen(auth: FirebaseAuth, context: Context, navHostController: NavHostController) {
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
                Button(
                    shape = RoundedCornerShape(16.dp),
                    enabled = loginValue.isNotEmpty() && passwordValue.isNotEmpty(),
                    onClick = {
                        onLogin(
                            loginValue,
                            passwordValue,
                            auth,
                            context,
                            navHostController
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

fun onLogin(
    login: String,
    password: String,
    auth: FirebaseAuth,
    context: Context,
    navHostController: NavHostController
) {
    auth
        .signInWithEmailAndPassword(login, password)
        .addOnSuccessListener {
            it.user.let {
                // TODO: navigate
            }
            Toast.makeText(context, "Successful login", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
            Log.e(TAG, it.toString())
        }
}
