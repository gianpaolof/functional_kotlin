data class ToDoList(val listName: ListName, val items: List<ToDoItem>) {
    companion object {
        fun build(
            listName: String, items: List<String>
        ): ToDoList = ToDoList(ListName.fromUntrustedOrThrow(listName), items.map() { ToDoItem(it) })
    }
}
