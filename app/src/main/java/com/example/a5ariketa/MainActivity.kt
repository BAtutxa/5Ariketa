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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
                    GGIKalkulua(
                        modifier = Modifier.padding(innerPadding),
                        name = "GGI"
                    )
                }
            }
        }
    }
}

@Composable
fun GGIKalkulua(name: String, modifier: Modifier = Modifier) {
    var pisuaInput by remember { mutableStateOf("") }
    var altueraInput by remember { mutableStateOf("") }
    var adinaInput by remember { mutableStateOf("") }
    var sexuaInput by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondoa),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.izenburua),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            TextField(
                value = pisuaInput,
                onValueChange = { pisuaInput = it },
                label = { Text(stringResource(id = R.string.label_pisua)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            TextField(
                value = altueraInput,
                onValueChange = { altueraInput = it },
                label = { Text(stringResource(id = R.string.label_altuera)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            TextField(
                value = adinaInput,
                onValueChange = { adinaInput = it },
                label = { Text(stringResource(id = R.string.label_adina)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            TextField(
                value = sexuaInput,
                onValueChange = { sexuaInput = it },
                label = { Text(stringResource(id = R.string.label_sexua)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            )

            val pisua = pisuaInput.toDoubleOrNull()
            val altueraCm = altueraInput.toDoubleOrNull()
            val adina = adinaInput.toIntOrNull()

            val sexuaLetra = sexuaInput.trim().lowercase()
            val sexuaBalioa = when (sexuaLetra) {
                "g", "h" -> 1
                "e", "m" -> 0
                else -> null
            }

            if (pisua != null && altueraCm != null && altueraCm > 0 && adina != null && sexuaBalioa != null) {
                val altueraM = altueraCm / 100.0
                val gmi = pisua / (altueraM * altueraM)
                val ggi = (1.20 * gmi) + (0.23 * adina) - (10.8 * sexuaBalioa) - 5.4

                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.gmi_emaitza, gmi),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.ggi_emaitza, ggi),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

            } else if (pisuaInput.isNotEmpty() || altueraInput.isNotEmpty() || adinaInput.isNotEmpty() || sexuaInput.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.Errorea),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

