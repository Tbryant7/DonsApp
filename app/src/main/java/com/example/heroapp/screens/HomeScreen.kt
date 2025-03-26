package com.example.heroapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.heroapp.model.McdItem
import com.example.heroapp.model.McdViewModel
import com.example.heroapp.navagation.AppBar
import com.example.heroapp.navagation.AppScreens
import com.example.heroapp.ui.theme.ColorSchemeType
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: McdViewModel
) {
    val context = LocalContext.current
    val itemList by viewModel.mcdItems.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showDrawer by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AppBar(
                    currentScreen = AppScreens.HomeScreen.name,
                    navController = navController,
                    navigateUp = { navController.navigateUp() },
                    context = context,
                    textToShare = "Check out the McDonald's Menu App powered by AI!",
                    onHelpClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                "This app uses the McDonald's API via RapidAPI. Built by Tannon with Jetpack Compose + AI."
                            )
                        }
                    },
                    onSettingsClick = {
                        showDrawer = true
                    }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(itemList) { item ->
                    McdItemCard(item = item) { selectedName ->
                        navController.navigate(AppScreens.DetailScreen.name + "/$selectedName")
                    }
                }
            }
        }

        // Right-side settings drawer
        if (showDrawer) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(250.dp)
                    .align(Alignment.TopEnd),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Settings", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Choose Theme:")
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.setThemeType(ColorSchemeType.LIGHT)
                            showDrawer = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Light")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.setThemeType(ColorSchemeType.DARK)
                            showDrawer = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Dark")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.setThemeType(ColorSchemeType.BLUE)
                            showDrawer = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Blue")
                    }
                }
            }
        }
    }
}

@Composable
fun McdItemCard(item: McdItem, onClick: (String) -> Unit = {}) {
    val imageUrl = item.imageUrl ?: "https://via.placeholder.com/300x180.png?text=No+Image"
    val painter = rememberImagePainter(data = imageUrl)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(item.name ?: "Unknown")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = painter,
                contentDescription = item.name ?: "Food Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = item.name ?: "Unnamed Item",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Calories: ${item.calories ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
