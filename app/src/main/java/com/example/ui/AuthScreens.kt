package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.BrainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: BrainViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }
    val authError by viewModel.authError.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                        MaterialTheme.colorScheme.background
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // App Identity Logo Card
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = "Second Brain Logo",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(48.dp)
                )
            }

            Text(
                text = "Daily Second Brain",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Your daily companion for productivity, finances, notes, and diary management.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Sign In",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    authError?.let {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = "Error Logo",
                                    tint = MaterialTheme.colorScheme.error
                                )
                                Text(
                                    text = it,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Email Address") },
                        leadingIcon = { Icon(Icons.Default.Email, "Email") },
                        placeholder = { Text("e.g. user@example.com") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("login_email_input"),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text("Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, "Password") },
                        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                                Icon(
                                    imageVector = if (showPassword.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Show/Hide Password"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("login_password_input"),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    Button(
                        onClick = {
                            viewModel.login(email.value, password.value, onLoginSuccess)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("login_submit_button"),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Log In", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    TextButton(
                        onClick = {
                            email.value = "demo@example.com"
                            password.value = "demo"
                            viewModel.login("demo@example.com", "demo", onLoginSuccess)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("💡 Quick Access: Enter Sandbox Mode", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }

            // Social Logins
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "or sign in with third-party networks",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val socials = listOf(
                        Icons.Default.CloudQueue to "Google",
                        Icons.Default.AlternateEmail to "Apple",
                        Icons.Default.Language to "Facebook"
                    )
                    socials.forEach { item ->
                        IconButton(
                            onClick = {
                                // Simulate OAuth register/log login
                                email.value = "${item.second.lowercase()}@oauth.com"
                                password.value = "oauth_secret"
                                viewModel.register(
                                    name = "${item.second} user",
                                    email = "${item.second.lowercase()}@oauth.com",
                                    pass = "oauth_secret",
                                    onSuccess = {
                                        viewModel.login("${item.second.lowercase()}@oauth.com", "oauth_secret", onLoginSuccess)
                                    }
                                )
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                        ) {
                            Icon(
                                imageVector = item.first,
                                contentDescription = item.second,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = "Register",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { onNavigateToRegister() }
                        .testTag("navigate_register_button")
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: BrainViewModel,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isVerified = remember { mutableStateOf(false) } // Represents "verification email sent/verified status"
    val showPassword = remember { mutableStateOf(false) }
    val authError by viewModel.authError.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                        MaterialTheme.colorScheme.background
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = "Register Icon",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(36.dp)
                )
            }

            Text(
                text = "Join Second Brain App",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Create Account",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    authError?.let {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.Error, "Error", tint = MaterialTheme.colorScheme.error)
                                Text(it, color = MaterialTheme.colorScheme.onErrorContainer, fontSize = 13.sp)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text("Display Name") },
                        leadingIcon = { Icon(Icons.Default.Person, "Name") },
                        placeholder = { Text("e.g. Veri Agustian") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("register_name_input"),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Email Address") },
                        leadingIcon = { Icon(Icons.Default.Email, "Email") },
                        placeholder = { Text("e.g. veri@brandy.com") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("register_email_input"),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text("Create Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, "Password") },
                        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                                Icon(
                                    imageVector = if (showPassword.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("register_password_input"),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    // Email verification form helper checkbox
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isVerified.value = !isVerified.value }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Checkbox(
                            checked = isVerified.value,
                            onCheckedChange = { isVerified.value = it }
                        )
                        Text(
                            text = "Simulate mock real-time verification email",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                    }

                    if (isVerified.value) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verified Icon",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Ready to send secure signup verification link!",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.register(name.value, email.value, password.value) {
                                viewModel.login(email.value, password.value, onRegisterSuccess)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("register_submit_button"),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Register Now", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account? ",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = "Sign In",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { onNavigateToLogin() }
                        .testTag("navigate_login_button")
                )
            }
        }
    }
}
