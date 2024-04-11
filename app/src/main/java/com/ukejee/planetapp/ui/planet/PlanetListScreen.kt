package com.ukejee.planetapp.ui.planet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey


@Composable
fun PlanetListScreen(viewModel: PlanetViewModel) {

    val planets = viewModel.getPlanets().collectAsLazyPagingItems()

    LazyColumn {
        items(
            planets
        ) { planet ->
            Row {
                Text(text = planet.name)
            }
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