import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object NetworkClient {
        val client: java.net.http.HttpClient = java.net.http.HttpClient.newBuilder()
                .version(java.net.http.HttpClient.Version.HTTP_1_1) 
                .connectTimeout(java.time.Duration.ofSeconds(5)) 
                .build()
        const val N8N_URL = "http://localhost:5678/webhook/registro"
        }