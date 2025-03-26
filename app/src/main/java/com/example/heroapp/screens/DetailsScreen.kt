package com.example.heroapp.screens.details

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.example.heroapp.model.McdViewModel
import com.example.heroapp.navagation.AppBar
import com.example.heroapp.navagation.AppScreens
import kotlinx.coroutines.launch
import com.example.heroapp.ui.theme.ColorSchemeType


fun stripHtml(html: String): String {
    return html.replace(Regex("<.*?>"), "")
}

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

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showDrawer by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                selectedItem?.let {
                    AppBar(
                        currentScreen = AppScreens.DetailScreen.name,
                        navController = navController,
                        navigateUp = { navController.navigateUp() },
                        context = context,
                        textToShare = it.description ?: it.name ?: "",
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
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            selectedItem?.let { item ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column {
                            Image(
                                painter = rememberImagePainter(item.imageUrl ?: ""),
                                contentDescription = item.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                contentScale = ContentScale.FillBounds
                            )

                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = item.name ?: "Unnamed Item",
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = stripHtml(item.description ?: "No description available."),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "Calories: ${item.calories ?: "N/A"}",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                val priceText = item.price?.takeIf { it.isNotBlank() }?.let { "$$it" } ?: "N/A"
                                Text(
                                    text = "Price: $priceText",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            } ?: run {
                Text("Item not found.", modifier = Modifier.padding(16.dp))
            }
        }

        // Settings Drawer
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

                    Button(onClick = {
                        viewModel.setThemeType(ColorSchemeType.LIGHT)
                        showDrawer = false
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Light")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = {
                        viewModel.setThemeType(ColorSchemeType.DARK)
                        showDrawer = false
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Dark")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = {
                        viewModel.setThemeType(ColorSchemeType.BLUE)
                        showDrawer = false
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Blue")
                    }
                }
            }
        }
    }
}
