import android.os.Build
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import util.HTTP_REQUEST_TIMEOUT_SECONDS
import java.util.concurrent.TimeUnit

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(OkHttp) {
    config(this)

    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(HTTP_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }
    }
}