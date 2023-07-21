package com.msusman.compose.cardstack.sample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.msusman.compose.cardstack.CardStack
import com.msusman.compose.cardstack.Direction
import com.msusman.compose.cardstack.Duration
import com.msusman.compose.cardstack.StackState
import com.msusman.compose.cardstack.SwipeDirection
import com.msusman.compose.cardstack.SwipeMethod
import com.msusman.compose.cardstack.rememberStackState
import com.msusman.compose.cardstack.sample.ui.theme.ComposeCardStackTheme

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCardStackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        val stackState = rememberStackState()
                        Box(modifier = Modifier.weight(1f)) {
                            MyCardStack(stackState)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            ElevatedButton(onClick = {
                                stackState.swipe(
                                    Direction.values().filterNot { it == Direction.None }.random()
                                )
                            }) {
                                Icon(painter = painterResource(id = R.drawable.baseline_swipe_24), contentDescription = null)
                                Text(text = "Swipe Randomly")
                            }
                            ElevatedButton(onClick = { stackState.rewind() }) {
                                Icon(painter = painterResource(id = R.drawable.baseline_replay_circle_filled_24), contentDescription = null)
                                Text(text = "Rewind")
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun MyCardStack(stackState: StackState) {

    CardStack(
        modifier = Modifier.padding(16.dp),
        stackState = stackState,
        cardElevation = 10.dp,
        scaleRatio = 0.95f,
        rotationMaxDegree = 20,
        displacementThreshold = 120.dp,
        animationDuration = Duration.NORMAL,
        visibleCount = 3,
        stackDirection = Direction.Bottom,
        swipeDirection = SwipeDirection.FREEDOM,
        swipeMethod = SwipeMethod.AUTOMATIC_AND_MANUAL,
        items = items,
        onSwiped = { index ->
            Log.d(TAG, "onSwiped index:$index ")
        }
    ) {
        MyImageCard(item = it)
    }
}

