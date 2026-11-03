package edu.temple.countdownservice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.temple.countdownservice.ui.theme.CountdownServiceTheme
const val TimerKey = "startValue"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            CountdownServiceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TimerComposable(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TimerComposable(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    var timerValue by rememberSaveable() { mutableStateOf("") }
    val startValue = timerValue.toIntOrNull()

    Column(modifier.fillMaxSize().padding(32.dp),
        Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Enter Starting Countdown Value")

        TextField(value = timerValue,
            onValueChange = {timerValue = it},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        Button(onClick = {
            startValue?.let {
                val intent = Intent(context, MyTimerService::class.java)
                intent.putExtra(TimerKey, it)
                context.startService(intent)
            }
        }, enabled = startValue != null && startValue >= 0) {
            Text(text = "Start Countdown")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CountdownServiceTheme {
        TimerComposable()
    }
}
