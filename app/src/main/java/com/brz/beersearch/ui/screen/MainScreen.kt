package com.brz.beersearch.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.brz.beersearch.Route
import com.brz.beersearch.model.ResponseBeersItem
import com.brz.beersearch.ui.theme.Pastel
import com.brz.beersearch.vm.BeersViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavHostController, beersVm: BeersViewModel = viewModel()) {

    var inputText by remember { mutableStateOf("") }
    val beers by beersVm.beers.observeAsState()
    val isLoading by beersVm.isLoading.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(16.dp))
            TextField(
                label = { Text("Search") },
                value = inputText,
                onValueChange = {
                    inputText = it
                    beersVm.getBeers(inputText)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(16.dp))
            AnimatedVisibility(isLoading!!) {
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator()
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))

            AnimatedVisibility(visible = beers != null) {
                LazyColumn {
                    items(beers!!) { item ->
                        Row(Modifier.animateItemPlacement()) {
                            BeerItemView(
                                item,
                                onClickBeer = {
                                    navController.navigate(Route.BeerDetail.withId(item.id.toString()))
                                }
                            )
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun BeerItemView(beer: ResponseBeersItem, onClickBeer: () -> Unit = {}) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClickBeer() }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    Modifier
                        .height(150.dp)
                        .width(150.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Pastel,
                    ),


                    ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SubcomposeAsyncImage(
                            model = beer.image_url,
                            loading = {
                                CircularProgressIndicator()
                            },
                            contentDescription = "",
                            alignment = Alignment.Center
                        )
                    }

                }


                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = beer.name)

            }
        }
    }
}

