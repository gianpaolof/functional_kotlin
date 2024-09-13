import org.opentest4j.AssertionFailedError
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo

class ToDoListOwner(override val name: String) : ScenarioActor {

    fun canSeeTheList(listName: String, items: List<String>): Step = {
        val expectedList = createList(listName, items)

        val list = getToDoList(name, listName)

        expectThat(list).isEqualTo(expectedList)
    }

    fun cannotSeeTheList(listName: String): Step = {

        expectThrows<AssertionFailedError> {
            getToDoList(name, listName)
        }
    }
}
fun ToDoListOwner.asUser(): User = User(name)

private fun createList(listName: String, items: List<String>): ToDoList =
    ToDoList(ListName(listName), items.map(::ToDoItem))