package com.ukejee.planetapp.ui.planet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ukejee.planetapp.theme.PlanetAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailsScreen(viewModel: PlanetViewModel) {
    val planet = viewModel.selectedPlanet.collectAsState().value

    planet?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = { Text(text = "Details") },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.onBackPressed?.invoke() }
                    ) {
                        Icon(
                            modifier = Modifier.size(64.dp),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                        )
                    }
                }
            )
            Details(
                name = planet.name,
                population = planet.population,
                rotationPeriod = planet.rotationPeriod,
                orbitalPeriod = planet.orbitalPeriod,
                diameter = planet.diameter,
                climate = planet.climate,
                gravity = planet.gravity,
                terrain = planet.terrain,
                surfaceWater = planet.surfaceWater
            )
        }
    }

}

@Composable
fun Details(
    name: String,
    population: String,
    rotationPeriod: String,
    orbitalPeriod: String,
    diameter: String,
    climate: String,
    gravity: String,
    terrain: String,
    surfaceWater: String
    ) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Text(
            text = name,
            fontSize = 28.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Population: ",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = population,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Text(
                text = "Rotation Period: ",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = rotationPeriod,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Text(
                text = "Orbital Period: ",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = orbitalPeriod,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        Row (
            modifier = Modifier.padding(top = 2.dp)
        ){
            Text(
                text = "Diameter: ",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = diameter,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        Row (
            modifier = Modifier.padding(top = 2.dp)
        ){
            Text(
                text = "Climate: ",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = climate,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        Row (
            modifier = Modifier.padding(top = 2.dp)
        ){
            Text(
                text = "Gravity: ",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = gravity,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        Row (
            modifier = Modifier.padding(top = 2.dp)
        ){
            Text(
                text = "Terrain: ",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = terrain,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        Row (
            modifier = Modifier.padding(top = 2.dp)
        ){
            Text(
                text = "Surface Water: ",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = surfaceWater,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun DetailsPreview() {
    PlanetAppTheme {
        Details(
            name = "Earth",
            population = "1,000,000",
            rotationPeriod = "23",
            orbitalPeriod = "304",
            diameter = "10465",
            climate = "Arid",
            gravity = "1 standard",
            terrain = "Desert",
            surfaceWater = "29"
        )
    }
}