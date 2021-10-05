package com.example.shopapp2.presentation.profile

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.Facebook
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.shopapp2.R
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.components.StandardTextField
import com.example.shopapp2.presentation.util.NavigationScreens

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel
) {

    if (profileViewModel.isLoading.value) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            CircularProgressIndicator()
        }
    }

    val facebookIconColor =
        if (isSystemInDarkTheme())
            Color(0xFF3b5998)
        else
            Color(0xFF4267B2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "My Profile",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )

        val (personalInformationExtended, setPersonalInformationExtended) = remember {
            mutableStateOf(
                false
            )
        }
        val (aboutUsExtended, setAboutUsExtended) = remember { mutableStateOf(false) }

        Box(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .clickable {
                navController.navigate(NavigationScreens.MyOrdersScreen.route)
            }
            .padding(16.dp)
        ) {
            Text("Orders", fontWeight = FontWeight.SemiBold)
        }

        Divider()
        BoxWithAnimatedContentSize(
            "Personal information",
            personalInformationExtended,
            setPersonalInformationExtended,
            {
                PersonalInformationContent(
                    userViewModel,
                    profileViewModel
                )
            })

        Divider()

        BoxWithAnimatedContentSize(
            "About us",
            aboutUsExtended,
            setAboutUsExtended,
            { AboutUsContent() })

        Divider()

        val openDialog = remember { mutableStateOf(false) }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(
                        text = "Are you sure you want to log out?",
                        fontWeight = FontWeight.Bold
                    )
                },
                confirmButton = {
                    val color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.error
                    } else {
                        MaterialTheme.colors.secondary
                    }
                    val textColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.onError
                    } else {
                        MaterialTheme.colors.onSecondary
                    }
                    Button(
                        onClick = {
                            profileViewModel.logUserOut(navController)
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = color
                        )
                    ) {
                        Text("Confirm", color = textColor)
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { openDialog.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
        // Log out
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .clickable {
                openDialog.value = true
            }
            .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Log out", fontWeight = FontWeight.SemiBold,
                )
                Icon(Icons.Filled.Logout, contentDescription = "")
            }
        }

        Spacer(modifier = Modifier.padding(25.dp))
        Text(
            text = "Follow us on Social Media", fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.padding(8.dp))
        SocialMediaItems(facebookIconColor)
    }

}

@Composable
private fun AboutUsContent() {
    Text(
        text = "We are a small shop that offers high quality products",
        fontSize = 14.sp, color = MaterialTheme.colors.onBackground.copy(0.7f)
    )
}

@Composable
private fun BoxWithAnimatedContentSize(
    title: String,
    extendedState: Boolean,
    setExtendedState: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.surface)
        .clickable { setExtendedState(!extendedState) }
        .animateContentSize(tween(400))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                )

                val angle: Float by animateFloatAsState(
                    targetValue = if (extendedState) 90F else 0F,
                    animationSpec = tween(
                        durationMillis = 400, // duration
                        easing = FastOutSlowInEasing
                    ),
                )

                Icon(
                    Icons.Filled.ArrowForwardIos,
                    contentDescription = "Localized description",
                    Modifier
                        .rotate(angle),
                )
            }
            if (extendedState) {
                Spacer(modifier = Modifier.padding(5.dp))

                content()
            }
        }
    }
}

@Composable
private fun SocialMediaItems(facebookIconColor: Color) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Facebook
            Surface(
                elevation = 3.dp, shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Row(
                    modifier = Modifier
                        .clickable { }
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

            Spacer(modifier = Modifier.padding(8.dp))

            // Google
            Surface(
                elevation = 3.dp, shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(top = 5.dp, bottom = 5.dp, end = 10.dp, start = 8.dp),
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
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
private fun PersonalInformationContent(
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel
) {

    Spacer(modifier = Modifier.height(5.dp))

    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    // The states of the texts
    val (nameText, setNameText) = rememberSaveable { mutableStateOf("") }
    val (phoneNumberText, setPhoneNumberText) = rememberSaveable { mutableStateOf("") }
    val (stateText, setStateText) = rememberSaveable { mutableStateOf("") }
    val (districtText, setDistrictText) = rememberSaveable { mutableStateOf("") }
    LaunchedEffect(key1 = true) {
        setNameText(userViewModel.user.value.name)
        setPhoneNumberText(userViewModel.user.value.phoneNumber)
        setStateText(userViewModel.user.value.state)
        setDistrictText(userViewModel.user.value.district)
    }

    // States to know if the keyboard done button of each one of the textFields was pressed
    val (nameKeyboardDonePressed, onNameKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }
    val (phoneNumberKeyboardDonePressed, onPhoneNumberKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }
    val (stateKeyboardDonePressed, onStateKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }
    val (districtKeyboardDonePressed, onDistrictPasswordKeyboardDonePressed) = rememberSaveable {
        mutableStateOf(
            false
        )
    }

    // Initialize the errors states
    val (saveButtonPressed, onSaveButtonPressed) =
        rememberSaveable { mutableStateOf(false) }
    val isNameError = remember { mutableStateOf(false) }
    val isPhoneNumberError = remember { mutableStateOf(false) }
    val isStateError = remember { mutableStateOf(false) }
    val isDistrictError = remember { mutableStateOf(false) }

    // Specify the errors
    isNameError.value = nameText.isEmpty() && (nameKeyboardDonePressed || saveButtonPressed)
    isPhoneNumberError.value =
        phoneNumberText.length != 10 && (phoneNumberKeyboardDonePressed || saveButtonPressed)
    isStateError.value = stateText.isEmpty() && (stateKeyboardDonePressed || saveButtonPressed)
    isDistrictError.value =
        districtText.isEmpty() && (districtKeyboardDonePressed || saveButtonPressed)

    // Email Text above Text field
    Text(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 5.dp),
        text = "Email",
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onSurface.copy(0.75f)
        )
    )
    // Email Text Field
    TextField(
        value = userViewModel.user.value.email,
        onValueChange = {},
        enabled = false
    )

    Spacer(modifier = Modifier.padding(7.dp))

    // Name Text above Text field
    Text(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 5.dp),
        text = "Name",
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onSurface.copy(0.75f)

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

    Spacer(modifier = Modifier.padding(7.dp))

    // Phone Text above Text field
    Text(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 5.dp),
        text = "Phone Number",
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onSurface.copy(0.75f)

        )
    )
    // Phone Number Text Field
    StandardTextField(
        text = phoneNumberText,
        onTextChange = setPhoneNumberText,
        errorText =
        if (isPhoneNumberError.value) "*Required"
        else "",
        focusRequester = focusRequester,
        keyboardController = keyboardController,
        placeholder = "0560107710",
        onKeyboardDonePressed = onPhoneNumberKeyboardDonePressed,
        keyboardType = KeyboardType.Phone
    )

    Spacer(modifier = Modifier.padding(7.dp))

    // State Text above Text field
    Text(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 5.dp),
        text = "State",
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onSurface.copy(0.75f)
        )
    )
    // State Text Field
    StandardTextField(
        text = stateText,
        onTextChange = setStateText,
        errorText =
        if (isStateError.value) "*Required"
        else "",
        focusRequester = focusRequester,
        keyboardController = keyboardController,
        placeholder = "Loris",
        onKeyboardDonePressed = onStateKeyboardDonePressed,
        keyboardType = KeyboardType.Email
    )

    Spacer(modifier = Modifier.padding(7.dp))

    // District Text above Text field
    Text(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 5.dp),
        text = "District",
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
        )
    )
    // District Text Field
    StandardTextField(
        text = districtText,
        onTextChange = setDistrictText,
        errorText =
        if (isDistrictError.value) "*Required"
        else "",
        focusRequester = focusRequester,
        keyboardController = keyboardController,
        placeholder = "Ney york",
        onKeyboardDonePressed = onDistrictPasswordKeyboardDonePressed,
        keyboardType = KeyboardType.Email
    )

    Spacer(modifier = Modifier.padding(8.dp))

    // Save button
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                onSaveButtonPressed(true)
                if (phoneNumberText.length == 10 && nameText.isNotEmpty() &&
                    stateText.isNotEmpty() && districtText.isNotEmpty()
                ) {
                    if (nameText == userViewModel.user.value.name &&
                        phoneNumberText == userViewModel.user.value.phoneNumber &&
                        stateText == userViewModel.user.value.state &&
                        districtText == userViewModel.user.value.district
                    ) {
                        Toast.makeText(context, "Data is the same", Toast.LENGTH_SHORT).show()
                    } else {
                        val updatedData = hashMapOf<String, Any>(
                            "name" to nameText,
                            "phoneNumber" to phoneNumberText,
                            "state" to stateText,
                            "district" to districtText
                        )

                        profileViewModel.updateUserData(
                            updatedData
                        )
                        userViewModel.updateUserData()
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Save")
        }
    }
}
