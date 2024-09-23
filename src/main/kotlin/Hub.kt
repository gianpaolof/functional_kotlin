/*
this is the HUB, the business logic is all here
anything inside the hub is domain related,
it is functionally pure, and can communicate with external components only using
its “spokes”—the arrows around it. In this way, the business logic can change
without any change on the technical layers, like our HTTP functions. At the
same time, if we need to change a technical detail, we don’t have to touch
the business logic at all

in the hexagonkt example, the hub is the appointment data model
 */

interface ZettaiHub {
    fun getList(user: User, listName: ListName): ToDoList?
    fun addItemToList(user: User, listName: ListName, item: ToDoItem): ToDoList?
}

class ToDoListHub(private val fetcher: IToDoListFetcher) : ZettaiHub {

    override fun getList(user: User, listName: ListName): ToDoList? =
        fetcher(user, listName)

    override fun addItemToList(user: User, listName: ListName, item: ToDoItem): ToDoList? =
        fetcher(user, listName)?.run {
            val newList = copy(items = items.replaceItem(item))
            fetcher.assignListToUser(user, newList)
        }

    private fun List<ToDoItem>.replaceItem(item: ToDoItem) = filterNot { it.description == item.description } + item
}
