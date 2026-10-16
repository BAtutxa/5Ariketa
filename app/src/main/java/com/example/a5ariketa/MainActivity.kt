package com.example.a5ariketa

import android.annotation.SuppressLint
import android.inputmethodservice.Keyboard
import android.os.*
import androidx.activity.compose.*
import androidx.activity.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import com.example.a5ariketa.ui.theme.*
import androidx.compose.foundation.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _5AriketaTheme {
                @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GGIKalkulua (modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun GGIKalkulua (modifier: Modifier) {
    var pisuaInput by remember {mutableStateOf("")}
    var altueraInput by remember {mutableStateOf("")}
    var adinaInput by remember {mutableStateOf("")}
    var sexuaInput by remember {mutableStateOf("")}

    val pisua = pisuaInput.toDoubleOrNull()
    val altueraCm = altueraInput.toDoubleOrNull()
    val adina = adinaInput.toIntOrNull()

    val sexuaBalioa = when (sexuaInput.trim().lowercase()) {
        "g", "h" -> 1
        "e", "m" -> 0
        else -> null
    }

    val gmi = if (pisua != null && altueraCm != null && altueraCm > 0) {
        val altueraM = altueraCm / 100
        pisua / (altueraM * altueraCm)
    } else {
        null
    }

    val ggi = if (gmi != null && adina != null && sexuaBalioa != null) {
        (1.20 * gmi)+(0.23*adina)-(10.8*sexuaBalioa)-5.4
    } else {
        null
    }

    Box (modifier = modifier.fillMaxSize()) {
        Image(
            painterResource(R.drawable.fondoa),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text (
                text="GGI Kalkulua:",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedTextField(
                value=pisuaInput,
                onValueChange = {pisuaInput = it},
                label = { Text("Pisua (kg):") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value=altueraInput,
                onValueChange = {altueraInput = it},
                label = { Text("Altuera (cm):") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value=adinaInput,
                onValueChange = {adinaInput = it},
                label = { Text("Adina:") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value=sexuaInput,
                onValueChange = { sexuaInput = it },
                label = { Text("Sexua (g/h = gizona, e/m = emakumea)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card (
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ){
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Kalkulatutako GMI emaitza: ${gmi?.let { "%.2f".format(it) } ?: "-"}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Kalkulatutako GGI portzentajea: ${ggi?.let { "%.2f".format(it) } ?: "-"} %",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    GGIKalkulua(Modifier)
}
