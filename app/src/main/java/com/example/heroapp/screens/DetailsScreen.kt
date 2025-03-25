package com.example.heroapp.screens.details

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.heroapp.model.McdViewModel
import com.example.heroapp.navagation.AppBar
import com.example.heroapp.navagation.AppScreens

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: McdViewModel,
    itemName: String?
) {
    val itemList = viewModel.mcdItems.observeAsState(emptyList()).value
    val selectedItem = itemList.firstOrNull { it.name == itemName }

    Scaffold(
        topBar = {
            selectedItem?.let {
                AppBar(
                    currentScreen = AppScreens.DetailScreen.name,
                    navController = navController,
                    navigateUp = { navController.navigateUp() },
                    context = LocalContext.current,
                    textToShare = it.description ?: it.name ?: "",
                    modifier = Modifier
                )
            }
        }
    ) {
        selectedItem?.let { item ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = rememberImagePainter(item.imageUrl ?: ""),
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = item.name ?: "Unnamed Item",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = item.description ?: "No description available.",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Calories: ${item.calories ?: "?"}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Price: $${item.price ?: "?"}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } ?: run {
            Text("Item not found.", modifier = Modifier.padding(16.dp))
        }
    }
}
