package com.eem.furnitureapp.arscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.eem.furnitureapp.model.Furniture
import com.eem.furnitureapp.ui.theme.Yellow200

@Composable
fun FurnitureItem(modifier: Modifier, furniture: Furniture, onClickAction: () -> Unit) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(1f, 1f, 1f, 0.5f))
            .clickable {
                onClickAction.invoke()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painterResource(id = furniture.imgSrc), contentDescription = "")
            Column(Modifier.padding(16.dp)) {
                Text(text = furniture.name, color = Color.White)
                Row {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Next Arrow",
                        tint = Yellow200
                    )
                    Text(
                        text = furniture.rate,
                        color = Color.White,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(text = "$ ${furniture.price}", color = Color.White)
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun FurniturePreview() {
//    FurnitureAppTheme {
//        // A surface container using the 'background' color from the theme
//        FurnitureItem(Modifier, Furniture("Sandy Chair", "4.5", "150.0", R.drawable.chair_a))
//    }
//}