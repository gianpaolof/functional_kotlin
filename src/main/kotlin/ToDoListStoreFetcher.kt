typealias ToDoListStore = MutableMap<User, MutableMap<ListName, ToDoList>>

data class ToDoListStoreFetcher(
    private val store: ToDoListStore
) : IToDoListFetcher {
    override fun invoke(user: User, listName: ListName): ToDoList? =
        store[user]?.get(listName)

    override fun assignListToUser(user: User, list: ToDoList): ToDoList? =
        store.compute(user) { _, value ->
            val listMap = value ?: mutableMapOf()
            listMap.apply { put(list.listName, list) }
        }?.let { list }

}