package com.ukejee.planetapp.ui.planet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ukejee.planetapp.theme.PlanetAppTheme


@Composable
fun PlanetListScreen(viewModel: PlanetViewModel) {

    val planets = viewModel.getPlanets().collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(
            planets
        ) { planet ->
            PlanetListItem(
                planetName = planet.name,
                planetPopulation = planet.population,
                planetClimate = planet.climate
            )
        }
        val loadState = planets.loadState.mediator

        item {
            if (loadState?.refresh == LoadState.Loading) {
                Column(
                    modifier = Modifier
                        .fillParentMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "Refresh Loading"
                    )

                    CircularProgressIndicator(color = Color.Blue)
                }
            }

            if (loadState?.append == LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = Color.Blue)
                }
            }

            if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                val isPaginatingError = (loadState.append is LoadState.Error) || planets.itemCount > 1
                val error = if (loadState.append is LoadState.Error)
                    (loadState.append as LoadState.Error).error
                else
                    (loadState.refresh as LoadState.Error).error

                val modifier = if (isPaginatingError) {
                    Modifier.padding(8.dp)
                } else {
                    Modifier.fillParentMaxSize()
                }
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (!isPaginatingError) {
                        Icon(
                            modifier = Modifier.size(64.dp),
                            imageVector = Icons.Rounded.Warning, contentDescription = null
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = error.message ?: error.toString(),
                        textAlign = TextAlign.Center,
                    )

                    Button(
                        onClick = {
                            planets.refresh()
                        },
                        content = {
                            Text(text = "Refresh")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green,
                            contentColor = Color.White,
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun PlanetListItem(planetName: String, planetPopulation: String, planetClimate: String) {
    Column(
        modifier = Modifier.padding(top = 4.dp)
            .background(color = Color.White)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Planet Name:", color = Color.Black)
            Text(text = planetName, color = Color.Black)

        }
        Spacer(modifier = Modifier.padding(top = 4.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Population:", color = Color.Black)
            Text(text = planetPopulation, color = Color.Black)

        }
        Spacer(modifier = Modifier.padding(top = 4.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Climate:", color = Color.Black)
            Text(text = planetClimate, color = Color.Black)

        }
        Spacer(modifier = Modifier.padding(top = 4.dp))
        HorizontalDivider(thickness = 1.dp)
    }
}

@Preview
@Composable
fun PlanetListItemPreview() {
    PlanetAppTheme {
        Column {
            PlanetListItem(planetName = "Earth", planetPopulation = "1 Billion", planetClimate = "36 degress")
            PlanetListItem(planetName = "Earth", planetPopulation = "1 Billion", planetClimate = "36 degress")
            PlanetListItem(planetName = "Earth", planetPopulation = "1 Billion", planetClimate = "36 degress")
        }
    }
}


fun <T: Any> LazyListScope.items(
    items: LazyPagingItems<T>,
    key: ( (T) -> Any )? = null,
    contentType: ( (T) -> Any )? = null,
    itemContent: @Composable LazyItemScope.(T) -> Unit
) {
    items(
        items.itemCount,
        key = items.itemKey(key),
        contentType = items.itemContentType(contentType)
    ) loop@ { i ->
        val item = items[i] ?: return@loop
        itemContent(item)
    }
}