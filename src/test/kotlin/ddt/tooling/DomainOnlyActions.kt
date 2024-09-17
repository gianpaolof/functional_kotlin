package ddt.tooling


import ListName
import ToDoItem
import ToDoList
import ToDoListHub
import User
import com.ubertob.fotf.zettai.domain.ToDoListFetcherFromMap
import com.ubertob.fotf.zettai.domain.ToDoListStore
import com.ubertob.pesticide.core.DdtProtocol
import com.ubertob.pesticide.core.DomainOnly
import com.ubertob.pesticide.core.Ready
import ddt.ToDoListOwnerDDT

/*
Each of our concrete actions needs to inherit the ZettaiActions interface.
 */
class DomainOnlyActions : ZettaiActions{
    override val protocol: DdtProtocol = DomainOnly
    override fun prepare() = Ready

    private val store: ToDoListStore = mutableMapOf()
    private val fetcher = ToDoListFetcherFromMap(store)

    private val hub = ToDoListHub(fetcher)


    override fun getToDoList(user: User, listName: ListName): ToDoList? =
        hub.getList(user, listName)


    override fun addListItem(user: User, listName: ListName, item: ToDoItem) {
        hub.addItemToList(user, listName, item)
    }

    override fun allUserLists(user: User): List<ListName> {
        TODO("Not yet implemented")
    }

    override fun createList(user: User, listName: ListName) {
        TODO("Not yet implemented")
    }

    override fun ToDoListOwnerDDT.`starts with a list`(listName: String, items: List<String>) {
        val newList = ToDoList.build(listName, items)
        fetcher.assignListToUser(user, newList)
    }

}