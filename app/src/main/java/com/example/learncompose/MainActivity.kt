package com.example.learncompose

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
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
    var showOnBoarding by rememberSaveable  { mutableStateOf(true) }

    Surface(modifier = modifier.fillMaxSize().systemBarsPadding(), color = MaterialTheme.colorScheme.surface) {
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
fun Greetings(modifier: Modifier = Modifier, names: List<String> = List(50) { "$it" }) {
    // Maintain a map of expanded states for each card
    val expandedStates = rememberSaveable { mutableStateOf(mutableMapOf<String, Boolean>()) }

    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names, key = { it }) { name ->
            greeting(
                name = name,
                isExpanded = expandedStates.value[name] ?: false,
                onCardClicked = { expanded ->
                    expandedStates.value[name] = expanded
                }
            )
        }
    }
}

//adding card contents
@Composable
fun greeting(
    name: String,
    isExpanded: Boolean,
    onCardClicked: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        CardContent(name, isExpanded, onCardClicked)
    }
}

@Composable
private fun CardContent(name: String, isExpanded: Boolean, onCardClicked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clickable { onCardClicked(!isExpanded) } // Toggle expand state
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ", color = MaterialTheme.colorScheme.secondary)
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = Color.Black
            )
            if (isExpanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    LearnComposeTheme  {
        onBoardingScreen(onGetStartedClicked = {})
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    //storing state information for button actions
//    var expanded = rememberSaveable  { mutableStateOf(false) }
//
//    //adding extra space for showing the expanded ui
//    val extraPadding by animateDpAsState(if (expanded.value) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
//
//    Surface(color = MaterialTheme.colorScheme.primary,
//             modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
//        Row(modifier = modifier.padding(25.dp)) {
//            //adding bottom padding to avoid crashes
//            Column(modifier = Modifier.weight(1f).padding(bottom=extraPadding.coerceAtLeast(0.dp))) {
//                Text(
//                    text = "Hello "
//                )
//                Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold))
//            }
//            ElevatedButton(
//                onClick = { expanded.value = !expanded.value }
//            ) {
//                Text(if (expanded.value) "Show Less" else "Show More")
//            }
//        }
//    }
//}


@Preview(showBackground = true, widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES, name = "greetingpreviewdark")
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