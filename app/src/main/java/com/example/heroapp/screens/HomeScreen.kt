package com.example.heroapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.heroapp.model.McdItem
import com.example.heroapp.model.McdViewModel
import com.example.heroapp.navagation.AppBar
import com.example.heroapp.navagation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: McdViewModel
) {
    val mcdResponse = viewModel.mcdItems.observeAsState()
    val itemList = viewModel.mcdItems.observeAsState(emptyList()).value

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = AppScreens.HomeScreen.name,
                navController = navController,
                navigateUp = { navController.navigateUp() },
                context = LocalContext.current,
                textToShare = "",
                modifier = Modifier
            )
        }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            LazyColumn {
                items(itemList) { item ->
                    McdItemRow(item = item) { selectedName ->
                        navController.navigate(AppScreens.DetailScreen.name + "/$selectedName")
                    }
                }
            }
        }
    }
}

@Composable
fun McdItemRow(item: McdItem, onClick: (String) -> Unit = {}) {
    val imageUrl = item.imageUrl ?: "https://via.placeholder.com/300x180.png?text=No+Image"
    val painter = rememberImagePainter(data = imageUrl)

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .clickable {
                onClick(item.name ?: "Unknown")
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Column {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(250.dp),
                shape = RectangleShape,
            ) {
                Image(
                    painter = painter,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Food Item"

                )
            }

            Text(
                text = item.name ?: "Unnamed Item",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "Calories: ${item.calories ?: "?"}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}
