import org.http4k.client.JettyClient
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.junit.jupiter.api.Assertions.fail

interface ScenarioActor {
    val name: String
}

interface Actions {
    fun getToDoList(user: String, listName: String): ToDoList
}


/*
The Step type alias represents an extension function
on the Actions interface that performs some action related
to the Actions contract (presumably fetching or manipulating to-do lists) without returning a specific value.
 example usage:
 class MyActionsImplementation : Actions {
   override fun getToDoList(user: String, listName: String): ToDoList {
       // ... logic to fetch the to-do list
   }
}

val printToDoList: Step = {
    val list = getToDoList("Alice", "Shopping")
    println(list)
}

val myActions = MyActionsImplementation()
myActions.printToDoList() // Call the 'printToDoList' step on 'myActions'
inside the lambda assigned to printToDoList,
you should be able to directly call functions defined in the Actions interface (like getToDoList) without any qualifiers.
 */
typealias Step = Actions.() -> Unit
/**
 * Facade class for the application
 */
class ApplicationForAT(private val client: HttpHandler, private val server: AutoCloseable) : Actions {

    override fun getToDoList(user: String, listName: String): ToDoList {

        val response = client(Request(Method.GET, "/todo/$user/$listName"))

        return if (response.status == Status.OK)
            parseResponse(response.bodyString())
        else
            fail(response.toMessage())
    }

    fun runScenario(vararg steps: Step) {
        server.use {
            steps.onEach {
                it(this)
            }
        }
    }

    private fun parseResponse(html: String): ToDoList {
        val nameRegex = "<h2>.*<".toRegex()
        val listName = ListName(extractListName(nameRegex, html))
        val itemsRegex = "<td>.*?<".toRegex()
        val items = itemsRegex.findAll(html)
            .map { ToDoItem(extractItemDesc(it)) }.toList()
        return ToDoList(listName, items)
    }

    private fun extractListName(nameRegex: Regex, html: String): String =
        nameRegex.find(html)?.value
            ?.substringAfter("<h2>")
            ?.dropLast(1)
            .orEmpty()

    private fun extractItemDesc(matchResult: MatchResult): String =
        matchResult.value.substringAfter("<td>").dropLast(1)

}

