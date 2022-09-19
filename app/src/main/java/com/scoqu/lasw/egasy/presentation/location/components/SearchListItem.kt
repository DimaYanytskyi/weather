package com.scoqu.lasw.egasy.presentation.location.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.scoqu.lasw.egasy.domain.model.City

@Composable
fun SearchListItem(
    modifier: Modifier,
    city: City
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = Color.White)
        .clip(RoundedCornerShape(8.dp))
    ){
        Text(
            text = "${city.name.uppercase()}, ${city.countryCode.uppercase()}",
            style = MaterialTheme.typography.h6,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
    }
}