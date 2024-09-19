package ddt.tooling

import ListName
import ToDoItem
import ToDoList
import User
import com.ubertob.pesticide.core.DdtActor
import strikt.api.Assertion
import strikt.api.expectThat
import strikt.assertions.*
/*to represent the Zettai
user that owns some to-do lists, we can create an actor called ToDoListOwnerThe actor can only act on the application using the DDT actions. For this
reason, we need to pass the ZettaiAction interface as a generic parameter to the
actor.Our actors are representations of the real human beings that use our software. Their
steps must represent the interactions they could have with our system. Hence, writing
our tests as a list of actor steps nudges us toward writing in a way that’s closer to
the actual human interaction than the technical implementation.
Moreover, without the actors, we’ll clutter the tests themselves with a lot of compli-
cated assertions. For this reason, we want to keep all assertions inside the actors’
methods, so tests will stay readable and clean*/
class ToDoListOwnerDDT (override val name: String) : DdtActor<ZettaiActions>() {

    val user = User(name)

    fun `can see #listname with #itemnames`(listName: String, expectedItems: List<String>) =
        step(listName, expectedItems) {
            val list = getToDoList(user, ListName.fromUntrustedOrThrow(listName))
            expectThat(list)
                .isNotNull()
                .itemNames
                .containsExactlyInAnyOrder(expectedItems)
        }

    fun `cannot see #listname`(listName: String) = step(listName) {
        val list = getToDoList(user, ListName.fromUntrustedOrThrow(listName))
        expectThat(list).isNull()
    }

    fun `cannot see any list`() = step {
        val lists = allUserLists(user)
        expectThat(lists)
            .isEmpty()
    }

    fun `can see the lists #listNames`(expectedLists: Set<String>) = step(expectedLists) {
        val lists = allUserLists(user)
        expectThat(lists)
            .map(ListName::name)
            .containsExactly(expectedLists)
    }

    fun `can create a new list called #listname`(listName: String) = step(listName) {
        createList(user, ListName.fromUntrustedOrThrow(listName))
    }

    fun `can add #item to the #listname`(itemName: String, listName: String) =
        step(itemName, listName) {
            val item = ToDoItem(itemName)
            addListItem(user, ListName.fromUntrustedOrThrow(listName), item)
        }

    private val Assertion.Builder<ToDoList>.itemNames: Assertion.Builder<List<String>>
        get() = get { items.map { it.description } }



}

