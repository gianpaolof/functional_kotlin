
import java.time.LocalDate
import java.time.format.DateTimeFormatter
data class HtmlPage(val raw: String)


fun renderPage(todoList: ToDoList): HtmlPage =
    HtmlPage(
        """
        <!DOCTYPE html>
        <html>
        <head>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
            <title>Zettai - a ToDoList application</title>
        </head>
        <body>
        <div id="container">
        <div class="row justify-content-md-center"> 
        <div class="col-md-center">
            <h1>Zettai</h1>
            <h2>ToDo List ${todoList.listName.name}</h2>
            <table class="table table-hover">
                <thead>
                    <tr>
                      <th>Name</th>
                      <th>Due Date</th>
                      <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                ${todoList.renderItems()}
                </tbody>
            </table>
            </div>
        </div>
        </div>
        </body>
        </html>
    """.trimIndent()
    )

private fun ToDoList.renderItems() =
    items.map(::renderItem).joinToString("")

private fun renderItem(it: ToDoItem): String = """<tr>
              <td>${it.description}</td>
              <td>${it.dueDate?.toIsoString().orEmpty()}</td>
              <td>${it.status}</td>
            </tr>""".trimIndent()


fun LocalDate.toIsoString(): String = format(DateTimeFormatter.ISO_LOCAL_DATE)

fun String?.toIsoLocalDate(): LocalDate? =
    unlessNullOrEmpty { LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE) }

fun String.toStatus(): ToDoStatus = ToDoStatus.valueOf(this)

fun renderHtml(todoList: ToDoList): HtmlPage =
    HtmlPage(
        """
    <html>
        <body>
            <h1>Zettai</h1>
            <h2>${todoList.listName.name}</h2>
            <table>
                <tbody>${renderItems(todoList.items)}</tbody>
            </table>
        </body>
    </html>
    """.trimIndent()
    )

private fun renderItems(items: List<ToDoItem>) =
    items.map {
        """<tr><td>${it.description}</td></tr>""".trimIndent()
    }.joinToString("")