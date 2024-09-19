package ddt.tooling


import ListName
import ToDoItem
import ToDoList
import User
import com.ubertob.pesticide.core.DdtActions
import com.ubertob.pesticide.core.DdtProtocol
import com.ubertob.pesticide.core.DomainDrivenTest

/*
This is the interface for our generic actions with the method we already
defined. It has to inherit from DdtActions.*/
interface ZettaiActions : DdtActions<DdtProtocol> {
    fun ToDoListOwnerDDT.`starts with a list`(listName: String, items: List<String>)
    fun ToDoListOwnerDDT.`starts with some lists`(lists: Map<String, List<String>>) =
        lists.forEach { (listName, items) ->
            `starts with a list`(listName, items)
        }


    fun getToDoList(user: User, listName: ListName): ToDoList?
    fun addListItem(user: User, listName: ListName, item: ToDoItem)
    fun allUserLists(user: User): List<ListName>
    fun createList(user: User, listName: ListName)
}
typealias ZettaiDDT = DomainDrivenTest<ZettaiActions>

fun allActions() = setOf(
    DomainOnlyActions(),
    HttpActions()
)
