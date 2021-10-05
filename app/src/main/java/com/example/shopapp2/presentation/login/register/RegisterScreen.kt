package com.example.shopapp2.presentation.login.register

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp2.domain.models.User
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.components.StandardTextField
import com.example.shopapp2.presentation.explore.clothes.ClothesViewModel
import com.example.shopapp2.presentation.explore.shoes.ShoesViewModel
import com.example.shopapp2.presentation.explore.watches.WatchesViewModel
import com.example.shopapp2.presentation.home.HomeViewModel
import com.example.shopapp2.presentation.login.LoginViewModel
import com.example.shopapp2.presentation.theme.Purple500
import com.example.shopapp2.presentation.util.NavigationScreens

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun RegisterScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    userViewModel: UserViewModel,
    homeViewModel: HomeViewModel,

    watchesViewModel: WatchesViewModel,
    clothesViewModel: ClothesViewModel,
    shoesViewModel: ShoesViewModel
) {
    ShowContent(
        navController, loginViewModel, userViewModel, homeViewModel,
        watchesViewModel = watchesViewModel,
        clothesViewModel = clothesViewModel,
        shoesViewModel = shoesViewModel
    )
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
private fun ShowContent(
    navController: NavController,
    loginViewModel: LoginViewModel,
    userViewModel: UserViewModel,
    homeViewModel: HomeViewModel,

    watchesViewModel: WatchesViewModel,
    clothesViewModel: ClothesViewModel,
    shoesViewModel: ShoesViewModel
) {

    val focusRequester = remember { FocusRequester() }
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    // The states of the texts
    val (nameText, setNameText) = rememberSaveable { mutableStateOf("") }
    val (emailText, setEmailText) = rememberSaveable { mutableStateOf("") }
    val (passwordText, setPasswordText) = rememberSaveable { mutableStateOf("") }
    val (confirmPasswordText, setConfirmPasswordText) = rememberSaveable { mutableStateOf("") }

    val (passwordVisibility, setPasswordVisibility) = rememberSaveable { mutableStateOf(false) }

    // States to know if the keyboard done button was pressed
    val (passwordKeyboardDonePressed, onPasswordKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }
    val (confirmPasswordKeyboardDonePressed, onConfirmPasswordKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }
    val (nameKeyboardDonePressed, onNameKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }
    val (emailKeyboardDonePressed, onEmailKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }

    // Initialize the errors states
    val (registerButtonPressed, onRegisterButtonPressed) = rememberSaveable { mutableStateOf(false) }
    val isPasswordError = remember { mutableStateOf(false) }
    val isConfirmPasswordError = remember { mutableStateOf(false) }
    val isNameError = remember { mutableStateOf(false) }
    val isEmailError = remember { mutableStateOf(false) }

    // Specify the errors
    isPasswordError.value =
        passwordText.count() < 6 && (passwordKeyboardDonePressed || registerButtonPressed)
    isConfirmPasswordError.value =
        passwordText != confirmPasswordText && (confirmPasswordKeyboardDonePressed || registerButtonPressed)
    isNameError.value = nameText.isEmpty() && (nameKeyboardDonePressed || registerButtonPressed)
    isEmailError.value = emailText.isEmpty() && (emailKeyboardDonePressed || registerButtonPressed)

    Content(
        nameText,
        setNameText,
        isNameError,
        focusRequester,
        keyboardController,
        onNameKeyboardDonePressed,
        emailText,
        setEmailText,
        isEmailError,
        onEmailKeyboardDonePressed,
        passwordText,
        setPasswordText,
        isPasswordError,
        onPasswordKeyboardDonePressed,
        passwordVisibility,
        setPasswordVisibility,
        confirmPasswordText,
        setConfirmPasswordText,
        isConfirmPasswordError,
        onConfirmPasswordKeyboardDonePressed,
        onRegisterButtonPressed,
        navController,
        loginViewModel,
        userViewModel,
        homeViewModel,
        watchesViewModel = watchesViewModel,
        clothesViewModel = clothesViewModel,
        shoesViewModel = shoesViewModel
    )
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
private fun Content(
    nameText: String,
    setNameText: (String) -> Unit,
    isNameError: MutableState<Boolean>,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    onNameKeyboardDonePressed: (Boolean) -> Unit,
    emailText: String,
    setEmailText: (String) -> Unit,
    isEmailError: MutableState<Boolean>,
    onEmailKeyboardDonePressed: (Boolean) -> Unit,
    passwordText: String,
    setPasswordText: (String) -> Unit,
    isPasswordError: MutableState<Boolean>,
    onPasswordKeyboardDonePressed: (Boolean) -> Unit,
    passwordVisibility: Boolean,
    setPasswordVisibility: (Boolean) -> Unit,
    confirmPasswordText: String,
    setConfirmPasswordText: (String) -> Unit,
    isConfirmPasswordError: MutableState<Boolean>,
    onConfirmPasswordKeyboardDonePressed: (Boolean) -> Unit,
    onRegisterButtonPressed: (Boolean) -> Unit,
    navController: NavController,
    loginViewModel: LoginViewModel,
    userViewModel: UserViewModel,
    homeViewModel: HomeViewModel,

    watchesViewModel: WatchesViewModel,
    clothesViewModel: ClothesViewModel,
    shoesViewModel: ShoesViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (loginViewModel.isLoading.value) {
            Dialog(
                onDismissRequest = { },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(top = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Create an account
            Text(
                text = "Create an account",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.padding(20.dp))

            // Email Text above Text field
            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp, start = 5.dp)
                    .align(Alignment.Start),
                text = "Name",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            // Name Text Field
            StandardTextField(
                text = nameText,
                onTextChange = setNameText,
                errorText =
                if (isNameError.value) "*Required"
                else "",
                focusRequester = focusRequester,
                keyboardController = keyboardController,
                placeholder = "Loris",
                onKeyboardDonePressed = onNameKeyboardDonePressed,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.padding(8.dp))

            // Email Text above Text field
            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp, start = 5.dp)
                    .align(Alignment.Start),
                text = "Email",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
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

            Spacer(modifier = Modifier.padding(8.dp))

            // Password text
            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp, start = 5.dp)
                    .align(Alignment.Start),
                text = "Password",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            // Password Text Field
            StandardTextField(
                text = passwordText,
                onTextChange = setPasswordText,
                errorText = if (isPasswordError.value) "Password must be more than 5 characters"
                else "",
                focusRequester = focusRequester,
                keyboardController = keyboardController,
                placeholder = "******",
                onKeyboardDonePressed = onPasswordKeyboardDonePressed,
                keyboardType = KeyboardType.Password,
                isPasswordVisible = passwordVisibility,
                onPasswordToggleClick = setPasswordVisibility
            )

            Spacer(modifier = Modifier.padding(8.dp))

            // Password text
            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp, start = 5.dp)
                    .align(Alignment.Start),
                text = "Confirm Password",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            // Confirm Password Text Field
            StandardTextField(
                text = confirmPasswordText,
                onTextChange = setConfirmPasswordText,
                errorText = if (isConfirmPasswordError.value) "This is not the same as your Password"
                else "",
                focusRequester = focusRequester,
                keyboardController = keyboardController,
                placeholder = "******",
                onKeyboardDonePressed = onConfirmPasswordKeyboardDonePressed,
                keyboardType = KeyboardType.Password,
                isPasswordVisible = passwordVisibility,
                onPasswordToggleClick = setPasswordVisibility
            )

            Spacer(modifier = Modifier.padding(30.dp))

            // Create an account Button
            Button(
                onClick = {
                    // Handle log in
                    onRegisterButtonPressed(true)
                    registerNewUser(
                        navController, loginViewModel, nameText, emailText,
                        passwordText, confirmPasswordText, isConfirmPasswordError,
                        userViewModel, homeViewModel,
                        watchesViewModel = watchesViewModel,
                        clothesViewModel = clothesViewModel,
                        shoesViewModel = shoesViewModel
                    )
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
            ) {

                Text(
                    text = "Create an account",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }

            Spacer(modifier = Modifier.padding(15.dp))

            // Already have an account? Log in
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an account? ")
                ClickableText(
                    text = AnnotatedString("Log in"),
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(NavigationScreens.LoginScreen.route)
                    },
                    style = TextStyle(
                        color = Purple500,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

fun registerNewUser(
    navController: NavController,
    loginViewModel: LoginViewModel,
    nameText: String,
    emailText: String,
    passwordText: String,
    confirmPasswordText: String,
    isConfirmPasswordError: MutableState<Boolean>,
    userViewModel: UserViewModel,
    homeViewModel: HomeViewModel,

    watchesViewModel: WatchesViewModel,
    clothesViewModel: ClothesViewModel,
    shoesViewModel: ShoesViewModel
) {
    if (nameText.isNotEmpty() && emailText.isNotEmpty() && passwordText.isNotEmpty()
        && confirmPasswordText.isNotEmpty() && !isConfirmPasswordError.value
    ) {
        val user = User(
            name = nameText,
            email = emailText
        )
        loginViewModel.registerNewUser(
            user = user,
            password = passwordText,
            navController = navController,
            userViewModel = userViewModel,
            homeViewModel = homeViewModel,
            watchesViewModel = watchesViewModel,
            clothesViewModel = clothesViewModel,
            shoesViewModel = shoesViewModel
        )
    }
}
