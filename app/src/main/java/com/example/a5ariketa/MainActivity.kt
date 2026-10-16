package com.example.a5ariketa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5ariketa.ui.theme._5AriketaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _5AriketaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GGIKalkulua(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GGIKalkulua(modifier: Modifier = Modifier) {
    var pisua by remember { mutableStateOf("") }
    var altuera by remember { mutableStateOf("") }
    var adina by remember { mutableStateOf("") }
    var sexua by remember { mutableStateOf("") }

    // Pasar los textos a números (si están vacíos o mal, dan null)
    val pisuaZ = pisua.replace(',', '.').toDoubleOrNull()
    val altueraZ = altuera.replace(',', '.').toDoubleOrNull()
    val adinaZ = adina.toDoubleOrNull()
    val sexuaZ = when (sexua.trim().lowercase()) {
        "g", "h" -> 1.0
        "e", "m" -> 0.0
        else -> null
    }

    // Cálculo
    val gmi: Double? =
        if (pisuaZ != null && altueraZ != null && altueraZ > 0) {
            val metroak = altueraZ / 100
            pisuaZ / (metroak * metroak)
        } else null

    val ggi: Double? =
        if (gmi != null && adinaZ != null && sexuaZ != null) {
            1.20 * gmi + 0.23 * adinaZ - 10.8 * sexuaZ - 5.4
        } else null

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.irudia),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 60.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.titulo),
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = pisua,
                onValueChange = { pisua = it },
                label = { Text(stringResource(R.string.pisua)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = altuera,
                onValueChange = { altuera = it },
                label = { Text(stringResource(R.string.altuera)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = adina,
                onValueChange = { adina = it },
                label = { Text(stringResource(R.string.adina)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = sexua,
                onValueChange = { sexua = it },
                label = { Text(stringResource(R.string.sexua)) },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(
                    R.string.gmi_emaitza,
                    if (gmi != null) "%.2f".format(gmi) else "-"
                ),
                fontSize = 20.sp,
                color = Color.White
            )

            Text(
                text = stringResource(
                    R.string.ggi_emaitza,
                    if (ggi != null) "%.2f".format(ggi) else "-"
                ),
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GatzaPreview() {
    _5AriketaTheme {
        GGIKalkulua()
    }
}