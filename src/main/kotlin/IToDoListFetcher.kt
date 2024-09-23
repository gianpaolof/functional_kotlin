typealias ToDoListFetcher = (user: User, listName: ListName) -> ToDoList?

interface IToDoListFetcher : ToDoListFetcher {

    override fun invoke(user: User, listName: ListName): ToDoList?

    fun assignListToUser(user: User, list: ToDoList): ToDoList?


}
