package com.example.shopapp2.presentation.login.password_reset

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp2.presentation.components.StandardTextField
import com.example.shopapp2.presentation.login.LoginViewModel
import com.example.shopapp2.presentation.util.NavigationScreens

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun PasswordResetScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        if (loginViewModel.isLoading.value) {
            Dialog(
                onDismissRequest = { },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                CircularProgressIndicator()
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                navController.popBackStack()
                navController.navigate(NavigationScreens.LoginScreen.route)
            }) {
                Icon(
                    Icons.Filled.ArrowBackIos,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onSurface
                )
            }
            Text(
                text = "Back",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )
        }

        Spacer(modifier = Modifier.padding(17.dp))

        Text(
            text = "Reset Password",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            ),
        )


        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Enter your registered email below and you'll receive an" +
                    " email with instructions on how to reset your password",
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            ),
            color = MaterialTheme.colors.onSurface.copy(0.4f)
        )

        Spacer(modifier = Modifier.padding(17.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            ShowTextFieldAndButton(navController, loginViewModel)
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ShowTextFieldAndButton(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val (emailText, setEmailText) = rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    val (emailKeyboardDonePressed, onEmailKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }
    val isEmailError = remember { mutableStateOf(false) }
    val (resetPasswordButtonPressed, onSignButtonPressed) = rememberSaveable { mutableStateOf(false) }
    isEmailError.value =
        emailText.isEmpty() && (emailKeyboardDonePressed || resetPasswordButtonPressed)

    ProgressBarRangeInfo

    // Email Text Field
    StandardTextField(
        text = emailText,
        onTextChange = setEmailText,
        errorText =
        if (isEmailError.value) "Enter a valid email"
        else "",
        focusRequester = focusRequester,
        keyboardController = keyboardController,
        placeholder = "Example@gmail.com",
        onKeyboardDonePressed = onEmailKeyboardDonePressed,
        keyboardType = KeyboardType.Email
    )

    Spacer(modifier = Modifier.padding(15.dp))

    // Send Email
    Button(
        onClick = {
            onSignButtonPressed(true)
            resetPassword(emailText, navController, loginViewModel)
        },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(45.dp),
    ) {
        Text(
            text = "Send Email",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
        )
    }

    Spacer(modifier = Modifier.padding(10.dp))
}

fun resetPassword(emailText: String, navController: NavController, loginViewModel: LoginViewModel) {
    if (emailText.isNotEmpty()) {
        loginViewModel.resetPassword(email = emailText, navController = navController)
    }
}
