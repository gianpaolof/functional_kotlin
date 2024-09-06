import org.eclipse.jetty.server.handler.AbstractHandler
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Jetty
import org.http4k.server.SunHttp
import org.http4k.server.asServer





val htmlPage = """
<html>
<body>
<h1 style="text-align:center; font-size:3em;" >
Hello Functional World!
</h1>
</body>
</html>"""

val handler: HttpHandler = { Response(Status.OK).body(htmlPage) }

fun main() {

    /*
    // Simulate multiple API calls
    repeat(15) {
        limitedFetchWeather()
    }

    repeat(15) {
        limitedFetchWeatherClock()
    }
     */
    val useJetty = true // Toggle this to switch between servers

    val server = if (useJetty) {
        handler.asServer(Jetty(8080))
    } else {
        handler.asServer(SunHttp(8080))
    }

    server.start()
}