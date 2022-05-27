package com.eem.furnitureapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.eem.furnitureapp.arscreen.MainARActivity
import com.eem.furnitureapp.screenscompose.HomeCompose
import com.eem.furnitureapp.ui.theme.FurnitureAppTheme
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FurnitureAppTheme {
                // A surface container using the 'background' color from the theme
                HomeCompose(navigation = {navigateToAr()})
            }
        }
    }

    private fun navigateToAr() {
        val intent = Intent(this, MainARActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FurnitureAppTheme {
        Greeting("Android")
    }
}