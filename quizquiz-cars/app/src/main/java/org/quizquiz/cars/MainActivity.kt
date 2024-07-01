/*
 * Copyright 2024 Hieu Luu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quizquiz.cars

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.car.app.connection.CarConnection
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.quizquiz.cars.data.PlacesRepository
import org.quizquiz.cars.data.model.Place
import org.quizquiz.cars.data.model.toIntent
import org.quizquiz.cars.ui.theme.QuizQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val carConnectionType by CarConnection(this).type.observeAsState(initial = -1)
            QuizQuizTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Text(
                            text = "Places",
                            style = MaterialTheme.typography.displayLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                        ProjectionState(
                            carConnectionType = carConnectionType,
                            modifier = Modifier.padding(8.dp)
                        )
                        PlaceList(places = PlacesRepository().getPlaces())
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceList(places: List<Place>) {
    val context = LocalContext.current

    LazyColumn {
        items(places.size) {
            val place = places[it]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        2.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        context.startActivity(place.toIntent(Intent.ACTION_VIEW))
                    }
                    .padding(8.dp)
            ) {
                Icon(
                    Icons.Default.Place,
                    "Place icon",
                    modifier = Modifier.align(CenterVertically),
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
                Column {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = place.description,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

            }
        }
    }
}

@Composable
fun ProjectionState(carConnectionType: Int, modifier: Modifier = Modifier) {
    // Important: The CarConnection API informs you of any change to projection state, but it has nothing to do with whether your own app
    // is being displayed on the head unit at any given time. You can determine the current state of your own app on the head unit by
    // hooking into the Session and Screen Lifecycle interfaces, learn more:
    // https://developer.android.com/training/cars/apps#carappservice-session-screen-lifecycles
    val text = when (carConnectionType) {
        CarConnection.CONNECTION_TYPE_NOT_CONNECTED -> "Not projecting"
        CarConnection.CONNECTION_TYPE_NATIVE -> "Running on Android Automotive OS"
        CarConnection.CONNECTION_TYPE_PROJECTION -> "Projecting"
        else -> "Unknown connection type"
    }

    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}