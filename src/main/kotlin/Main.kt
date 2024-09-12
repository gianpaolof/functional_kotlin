import org.eclipse.jetty.server.handler.AbstractHandler
import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
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
val app: HttpHandler = routes(
    "/todo/{user}/{list}" bind Method.GET to ::showList
)

fun showList(req: Request): Response {
    val user: String? = req.path("user")
    val list: String? = req.path("list")
    val htmlPage = """
                <html>
                <body>
                <h1>Zettai</h1>
                <p>Here is the list <b>$list</b> of user <b>$user</b></p>
                </body>
                </html>"""
    return Response(OK).body(htmlPage)
}

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
        app.asServer(Jetty(8080))
    } else {
        app.asServer(SunHttp(8080))
    }

    server.start()
}