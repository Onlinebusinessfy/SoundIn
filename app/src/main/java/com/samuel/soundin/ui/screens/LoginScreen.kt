package com.samuel.soundin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuel.soundin.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit
){
    // Snackbar state -- lives here
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope ()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "SoundIn")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }, // End topbar function
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ) { paddingValues ->
        LoginContent(
            paddingValues = paddingValues,
            snackbarHostState = snackbarHostState,
            scope = scope,
            onNavigateToRegister = onNavigateToRegister
        )
    }
}

@Composable
fun LoginContent(
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onNavigateToRegister: () -> Unit
){
    //local state -- lives here
    var email by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }
    var rememberSession by remember { mutableStateOf(value = false) }
    var emailError by remember {mutableStateOf(value = false)}
    var passwordError by remember {mutableStateOf(value = false)}
    var passwordVisible by remember {mutableStateOf(value = false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .padding(24.dp)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image( painter = painterResource(R.drawable.sdgku_logo),
            contentDescription = "SoundIn Logo"
        )
        Text(
            text = "SoundIn",
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it ; emailError = false},
            label = {Text("Email Address")},
            isError = emailError,
            supportingText = {
                if (emailError){
                    Text(text = "Enter a valid email address")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) // End OutlinedTextField email
        // Challenge create the OutlinedTextField for password

        OutlinedTextField(
            value = password,
            onValueChange = {password = it ; passwordError = false},
            label = {Text("Password")},
            isError = passwordError,
            supportingText = {
                if (passwordError){
                    Text(text = "Enter a valid password")
                }
            },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = {passwordVisible = !passwordVisible}
                ){
                    Icon(
                        imageVector = if (passwordVisible){
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) // End of the OutlinedText password
        // Remember session Switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Remember Session",
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = rememberSession,
                onCheckedChange = {rememberSession = it}
            )
        }
        //Log In with Validation
        Button(
            onClick = {
                emailError = !email.contains("@") || !email.contains(".")
                passwordError = password.length < 6
                if(emailError || passwordError){
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Please review the marked fields",
                            actionLabel = "Dismiss"
                        )
                    }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Welcome to SoundIn",
                            actionLabel = "Ok"
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Login")
        }
        // Link for registration
        TextButton(
            onClick = onNavigateToRegister
        ) {
            Text(text = "Don't have an account? Register")
        }

    } //End column

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen(
        onNavigateToRegister = { }
    )
}