package com.eem.furnitureapp.screenscompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.eem.furnitureapp.R
import com.eem.furnitureapp.ui.theme.Blue200
import com.eem.furnitureapp.ui.theme.FurnitureAppTheme
import com.eem.furnitureapp.ui.theme.Green200
import com.eem.furnitureapp.ui.theme.White700
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun HomeCompose(modifier: Modifier = Modifier, navigation: ()->Unit) {

    val state = rememberPagerState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White700
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f), color = White700
                    ) {
                        Row(horizontalArrangement = Arrangement.End) {
                            Text(
                                text = "Furni",
                                color = Green200,
                                modifier = Modifier.padding(end = 5.dp, top = 15.dp),
                                fontSize = 28.sp
                            )
                        }
                    }
                    Surface(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        color = Blue200,
                        shape = RoundedCornerShape(bottomStart = 30.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.Start) {
                            Text(
                                text = "House",
                                color = White700,
                                modifier = Modifier.padding(start = 5.dp, top = 15.dp),
                                fontSize = 28.sp,
                            )
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    HorizontalPager(
                        state = state,
                        count = 3
                    ) { page ->
                        val painter = rememberImagePainter(R.drawable.chair1)
                        Column() {
                            Surface(Modifier.height(60.dp)) {}
                            Image(
                                painter = painter,
                                contentDescription = "",
                                Modifier
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.Start) {
                DotsIndicator(
                    totalDots = 3,
                    selectedIndex = state.currentPage,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 16.dp),
                text = "Make Your",
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = Blue200
            )
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "Dream House",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Green200
            )
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                text = "Modern furniture make you\nwant to stay in",
                fontSize = 18.sp,
                fontWeight = FontWeight.Thin,
                color = Green200
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navigation.invoke() },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row() {
                        Text(text = "Place", color = White700, fontSize = 16.sp)
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Next Arrow",
                            tint = White700
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier
) {
    LazyRow(
        modifier = modifier
            .wrapContentHeight(), horizontalArrangement = Arrangement.Center
    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(13.dp)
                        .clip(CircleShape)
                        .background(color = Green200)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(13.dp)
                        .clip(CircleShape)
                        .background(color = Color.LightGray)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun homePreview() {
    FurnitureAppTheme {
        // A surface container using the 'background' color from the theme
        HomeCompose(navigation = {})
    }
}