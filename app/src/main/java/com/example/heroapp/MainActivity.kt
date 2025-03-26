package com.example.heroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.heroapp.navagation.MovieNavigation
import com.example.heroapp.ui.theme.HeroAppTheme
import com.example.heroapp.model.McdViewModel
import com.example.heroapp.ui.theme.ColorSchemeType
import androidx.compose.runtime.getValue



class MainActivity : ComponentActivity() {
    private val viewModel: McdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(viewModel) {
                MovieNavigation()
            }
        }
    }
}

@Composable
fun MyApp(viewModel: McdViewModel, content: @Composable () -> Unit) {
    val themeType by viewModel.themeType.observeAsState(ColorSchemeType.LIGHT)

    HeroAppTheme(colorSchemeType = themeType) {
        content()
    }
}