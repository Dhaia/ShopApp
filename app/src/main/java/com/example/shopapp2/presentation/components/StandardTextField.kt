package com.example.shopapp2.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp2.R

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun StandardTextField(
    text: String,
    onTextChange: (String) -> Unit,
    errorText: String,
    placeholder: String = "",
    keyboardType: KeyboardType,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    maxLength: Int = 40,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    onKeyboardDonePressed: (Boolean) -> Unit,
    ) {

    Column(modifier = Modifier.fillMaxWidth()) {

        TextField(
            value = text,
            onValueChange = {
                if (it.length <= maxLength) {
                    onTextChange(it)
                }
            },
            maxLines = maxLines,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.body1
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            isError = errorText != "",
            singleLine = singleLine,
            trailingIcon = if (isPasswordToggleDisplayed) {
                val icon: @Composable () -> Unit = {
                    IconButton(
                        onClick = {
                            onPasswordToggleClick(!isPasswordVisible)
                        },
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            },
                            contentDescription = if (isPasswordVisible) {
                                stringResource(id = R.string.password_visible_content_description)
                            } else {
                                stringResource(id = R.string.password_hidden_content_description)
                            }
                        )
                    }
                }
                icon
            } else null,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester = focusRequester),
            keyboardActions = KeyboardActions(onDone = {
                onKeyboardDonePressed(true)
                keyboardController?.hide()
            }),
        )

        ShowErrorText(isError = errorText != "", errorText = errorText)
    }
}

@ExperimentalAnimationApi
@Composable
private fun ShowErrorText(
    isError: Boolean,
    errorText: String
) {

    val enter = remember { fadeIn(animationSpec = TweenSpec(300, easing = FastOutLinearInEasing)) }
    val exit = remember { fadeOut(animationSpec = TweenSpec(100, easing = FastOutSlowInEasing)) }

    Box(Modifier.defaultMinSize(minHeight = 6.dp)) {
        AnimatedVisibility(
            visible = isError,
            enter = enter,
            exit = exit,
        ) {
            Text(
                text = errorText,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.error
                ),
                modifier = Modifier.padding(start = 2.dp),
            )
        }
    }
}