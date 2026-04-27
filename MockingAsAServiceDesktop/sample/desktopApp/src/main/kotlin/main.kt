import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
//import sample.app.App // Esto importa automaticamente el App de ejemplo, como se sobreescribe debajo, lo comento
import java.awt.Dimension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import NetworkClient;
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// El main
fun main() = application {
// Propiedades de la ventana 
    Window(
        title = "MockingAsAService",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        // Funcionalidad compose
        App()
    }
}

// Colores estilo "Dark Mode" elegante
val BackgroundColor = Color(0xFF1E1E1E)
val CardColor = Color(0xFF2D2D2D)
val AccentColor = Color(0xFF6200EE)
val TextColor = Color(0xFFE0E0E0)

@Composable
fun App() {
    var prompt by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("Esperando instrucciones...") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Para poder llamar funciones suspend

    MaterialTheme(colors = darkColors(primary = AccentColor)) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackgroundColor
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título, equivalente a una etiqueta, es un componente
                Text(
                    text = "AI Endpoint Generator",
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Describe el endpoint que necesitas y la IA lo levantará por ti.",
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Caja de texto principal (Input), equivalente a un EditText
                Card(
                    backgroundColor = CardColor,
                    shape = RoundedCornerShape(12.dp),
                    elevation = 8.dp,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                // Texto de placeholder
                    TextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                        placeholder = { Text("Ej: Crea un GET en /usuarios que devuelva una lista de 3 personas con nombre y edad...") },
                        modifier = Modifier.fillMaxSize(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            textColor = TextColor
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                }

                // Separador
                Spacer(modifier = Modifier.height(16.dp))

                // Botón de acción
                Button(
                // Listener
                    onClick = {
                        // Aquí llamaremos a Ktor más adelante
                        isLoading = true
                        statusMessage = "Enviando comando a n8n..."
                        
                        scope.launch(Dispatchers.IO) {
                            try {
                                val jsonBody = """{"instruccion": "${prompt.replace("\"", "\\\"")}"}"""
                                
                                // Construcción de la request 
                                val request = HttpRequest.newBuilder()
                                    .uri(URI.create(NetworkClient.N8N_URL))
                                    .header("Content-Type", "application/json")
                                    .header("Accept", "application/json") 
                                    .header("User-Agent", "Kotlin/1.9.0") // Evita que n8n lo ignore
                                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                                    .build()
                        
                                // Envío con timeout para no quedar bloqueado
                                val response = NetworkClient.client.send(request, HttpResponse.BodyHandlers.ofString())
                        
                                withContext(Dispatchers.Main) {
                                    if (response.statusCode() in 200..299) {
                                        statusMessage = "Éxito: Endpoint registrado"
                                        prompt = ""
                                    } else {
                                        // Si falla, esto mostrará qué dice n8n exactamente
                                        statusMessage = "Error ${response.statusCode()}: ${response.body().take(30)}"
                                        println("DETALLE ERROR N8N: ${response.body()}")
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    statusMessage = "Fallo de conexión: ${e.localizedMessage}"
                                    e.printStackTrace()
                                }
                            } finally {
                                withContext(Dispatchers.Main) {
                                    isLoading = false
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    enabled = prompt.isNotBlank() && !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Spacer(Modifier.width(8.dp))
                        Text("GENERAR ENDPOINT")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Consola de Feedback (Output)
                Card(
                    backgroundColor = Color.Black,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(100.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(12.dp).fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "> $statusMessage",
                            color = Color(0xFF00FF00), // Verde consola
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}