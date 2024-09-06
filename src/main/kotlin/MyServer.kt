import org.eclipse.jetty.server.Server

object MyServer {

    fun get(): Server {
        return Server(8080)
    }
}