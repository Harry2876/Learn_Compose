package com.example.learncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learncompose.ui.theme.LearnComposeTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnComposeTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp( modifier: Modifier = Modifier) {
    var showOnBoarding by remember { mutableStateOf(true) }

    Surface(modifier.fillMaxSize()) {
        if (showOnBoarding){
            onBoardingScreen(onGetStartedClicked = { showOnBoarding = false })
        } else {
            Greetings()
        }
    }
}

//creating a onBoarding Screen
@Composable
fun onBoardingScreen(onGetStartedClicked: () -> Unit,
                     modifier: Modifier = Modifier) {

    Column(modifier = modifier.fillMaxSize().padding(16.dp),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment= Alignment.CenterHorizontally) {
        Text("Welcome To Learn Compose App")
        Button(modifier = Modifier
            .padding(vertical = 24.dp),
               onClick = onGetStartedClicked ) {
            Text("Get Started")
        }
    }
}


@Composable
fun Greetings(modifier: Modifier = Modifier,
              names: List<String> = listOf("Android", "there","Hariom","Joker")) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names)
            Greeting(name = name)
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    LearnComposeTheme  {
        onBoardingScreen(onGetStartedClicked = {})
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //storing state information for button actions
    var expanded = remember { mutableStateOf(false) }

    //adding extra space for showing the expanded ui
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(color = MaterialTheme.colorScheme.primary,
             modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(modifier = modifier.padding(25.dp)) {
            Column(modifier = Modifier.weight(1f).padding(bottom=extraPadding)) {
                Text(
                    text = "Hello "
                )
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Show Less" else "Show More")
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    LearnComposeTheme {
        Greetings()
    }
}


@Preview
@Composable
fun MyAppPreview() {
    LearnComposeTheme {
        MyApp(Modifier.fillMaxSize())
    }
}