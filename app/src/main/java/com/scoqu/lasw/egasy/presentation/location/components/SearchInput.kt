package com.scoqu.lasw.egasy.presentation.location.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.scoqu.lasw.egasy.ui.theme.Black40
import com.scoqu.lasw.egasy.ui.theme.White

@Composable
fun SearchInput(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(4.dp))
        .fillMaxWidth()
        .background(
            color = Color(Black40.toArgb())
        )
    ){
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color(White.toArgb()),
                contentDescription = "Search city",
                modifier = modifier
                    .padding(horizontal = 8.dp)
            )
            Box(
                modifier = modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = onValueChange,
                    singleLine = singleLine,
                    textStyle = MaterialTheme.typography.body1
                        .plus(TextStyle(color = Color(White.toArgb()))),
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                        .onFocusChanged {
                            onFocusChange(it)
                        }
                )
                if(isHintVisible) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.body1,
                        color = Color(White.toArgb())
                    )
                }
            }
        }
    }
}