package com.example.shopapp2.presentation.login.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.shopapp2.R
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.components.StandardTextField
import com.example.shopapp2.presentation.explore.clothes.ClothesViewModel
import com.example.shopapp2.presentation.explore.shoes.ShoesViewModel
import com.example.shopapp2.presentation.explore.watches.WatchesViewModel
import com.example.shopapp2.presentation.home.HomeViewModel
import com.example.shopapp2.presentation.login.LoginViewModel
import com.example.shopapp2.presentation.theme.Purple500
import com.example.shopapp2.presentation.theme.Purple700
import com.example.shopapp2.presentation.util.NavigationScreens
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import timber.log.Timber

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    userViewModel: UserViewModel,
    homeViewModel: HomeViewModel,

    watchesViewModel: WatchesViewModel,
    clothesViewModel: ClothesViewModel,
    shoesViewModel: ShoesViewModel
) {

    ShowContent(navController = navController, loginViewModel, userViewModel,
        homeViewModel, watchesViewModel, clothesViewModel, shoesViewModel)

}

// Stateful composable
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

    val (emailText, setEmailText) = rememberSaveable { mutableStateOf("") }
    val (passwordText, setPasswordText) = rememberSaveable { mutableStateOf("") }

    val isEmailError = remember { mutableStateOf(false) }
    val isPasswordError = remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    val (passwordKeyboardDonePressed, onPasswordKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }
    val (emailKeyboardDonePressed, onEmailKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }
    val (signButtonPressed, onLoginButtonPressed) = rememberSaveable { mutableStateOf(false) }
    val (passwordVisibility, setPasswordVisibility) = rememberSaveable { mutableStateOf(false) }

    isPasswordError.value =
        passwordText.count() < 6 && (passwordKeyboardDonePressed || signButtonPressed)
    isEmailError.value = emailText.isEmpty() && (emailKeyboardDonePressed || signButtonPressed)

    val facebookIconColor =
        if (isSystemInDarkTheme())
            Color(0xFF3b5998)
        else
            Color(0xFF4267B2)

    Content(
        facebookIconColor,
        emailText,
        setEmailText,
        isEmailError,
        focusRequester,
        keyboardController,
        onEmailKeyboardDonePressed,
        passwordText,
        setPasswordText,
        isPasswordError,
        onPasswordKeyboardDonePressed,
        passwordVisibility,
        setPasswordVisibility,
        navController,
        onLoginButtonPressed,
        loginViewModel,
        userViewModel,
        homeViewModel,
        watchesViewModel,
        clothesViewModel,
        shoesViewModel
    )
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
private fun Content(
    facebookIconColor: Color,
    emailText: String,
    setEmailText: (String) -> Unit,
    isEmailError: MutableState<Boolean>,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    onEmailKeyboardDonePressed: (Boolean) -> Unit,
    passwordText: String,
    setPasswordText: (String) -> Unit,
    isPasswordError: MutableState<Boolean>,
    onPasswordKeyboardDonePressed: (Boolean) -> Unit,
    passwordVisibility: Boolean,
    setPasswordVisibility: (Boolean) -> Unit,
    navController: NavController,
    onLoginButtonPressed: (Boolean) -> Unit,
    loginViewModel: LoginViewModel,
    userViewModel: UserViewModel,
    homeViewModel: HomeViewModel,

    watchesViewModel: WatchesViewModel,
    clothesViewModel: ClothesViewModel,
    shoesViewModel: ShoesViewModel
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                loginViewModel.signWithCredential(
                    credential = credential,
                    navController = navController,
                    userViewModel = userViewModel,
                    homeViewModel = homeViewModel,
                    watchesViewModel = watchesViewModel,
                    clothesViewModel = clothesViewModel,
                    shoesViewModel = shoesViewModel
                )
            } catch (e: ApiException) {
                Timber.d("Google sign in failed $e")
            }
        }
    val token = stringResource(R.string.web_client_id)
    val context = LocalContext.current

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
            // Log in with
            Text(
                text = "Log In With",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.padding(20.dp))

            // FACEBOOK || GOOGLE
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // FACEBOOK sign in with
                /*
                Surface(
                    elevation = 3.dp, shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Row(
                        modifier = Modifier
                            //.background(Color.Gray.copy(0.05f))
                            .clickable {

                            }
                            .padding(top = 5.dp, bottom = 5.dp, end = 10.dp, start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Facebook,
                            contentDescription = "",
                            modifier = Modifier.size(34.dp),
                            tint = facebookIconColor
                        )
                        Spacer(modifier = Modifier.padding(5.dp))

                        Text(
                            text = "FACEBOOK",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                */

                // Google sign in with
                Surface(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Row(
                        modifier = Modifier
                            //.background(Color.Gray.copy(0.05f)),
                            .clickable {

                                val gso =
                                    GoogleSignInOptions
                                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestIdToken(token)
                                        .requestEmail()
                                        .build()
                                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                                launcher.launch(googleSignInClient.signInIntent)

                            }
                            .padding(top = 5.dp, bottom = 5.dp, end = 20.dp, start = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(id = R.drawable.google_icon),
                            contentDescription = "",
                            modifier = Modifier.size(34.dp),
                            tint = Color(0xFFDD4B39)
                        )
                        Spacer(modifier = Modifier.padding(5.dp))

                        Text(
                            text = "GOOGLE",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(15.dp))

            //  ---- Or ----
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .width(95.dp),
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp),
                        text = "Or",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = TextStyle(
                            letterSpacing = 0.4.sp
                        )
                    )

                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .width(95.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.padding(15.dp))

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

            Spacer(modifier = Modifier.padding(10.dp))

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

            // "Forgot your password?"  Text
            ClickableText(
                text = AnnotatedString("Forgot your password?"),
                modifier = Modifier
                    .padding(top = 6.dp, end = 3.dp)
                    .align(Alignment.End),
                onClick = { navController.navigate(NavigationScreens.PasswordResetScreen.route) },
                style = TextStyle(
                    fontSize = 13.sp,
                    color = MaterialTheme.colors.onBackground.copy(0.6f)
                )
            )

            Spacer(modifier = Modifier.padding(30.dp))

            // Log in Button
            Button(
                onClick = {
                    // Handle log in
                    onLoginButtonPressed(true)
                    loginUser(
                        emailText, passwordText, navController,
                        loginViewModel, userViewModel, homeViewModel,
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
                    text = "Log in",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }

            Spacer(modifier = Modifier.padding(15.dp))

            // Create an account instead
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an account?")
                Spacer(modifier = Modifier.padding(2.dp))
                ClickableText(
                    text = AnnotatedString("Create one"),
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(NavigationScreens.RegisterScreen.route)
                    },
                    style = TextStyle(
                        color = Purple500,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Spacer(modifier = Modifier.padding(15.dp))
            // Skip
            ClickableText(
                text = AnnotatedString("Skip (For testing only)"),
                onClick = { navController.navigate(NavigationScreens.HomeScreen.route) },
                style = TextStyle(
                    color = Purple700,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

fun loginUser(
    emailText: String,
    passwordText: String,
    navController: NavController,
    loginViewModel: LoginViewModel,
    userViewModel: UserViewModel,
    homeViewModel: HomeViewModel,

    watchesViewModel: WatchesViewModel,
    clothesViewModel: ClothesViewModel,
    shoesViewModel: ShoesViewModel
) {
    if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
        loginViewModel.loginUser(
            email = emailText,
            password = passwordText,
            navController = navController,
            userViewModel,
            homeViewModel = homeViewModel,

            watchesViewModel = watchesViewModel,
            clothesViewModel = clothesViewModel,
            shoesViewModel = shoesViewModel
        )
    }
}
