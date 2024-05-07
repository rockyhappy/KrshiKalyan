package com.devrachit.krishi.presentation.authScreens.signupScreen.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.devrachit.krishi.ui.theme.primaryVariantColor1

@Composable
fun SwitchWithIconExample(modifier: Modifier, checked : Boolean, onCheckedChange: (Boolean) -> Unit) {


    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
//            checkedThumbColor = primaryVariantColor1,
            checkedTrackColor = primaryVariantColor1,
        ),
        modifier=modifier,
        thumbContent = if (checked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        }
    )

}