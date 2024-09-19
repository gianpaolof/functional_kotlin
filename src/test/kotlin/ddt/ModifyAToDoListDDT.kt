package ddt

import com.ubertob.pesticide.core.DDT
import ddt.tooling.ToDoListOwnerDDT
import ddt.tooling.ZettaiDDT
import ddt.tooling.allActions

class ModifyAToDoListDDT : ZettaiDDT(allActions()) {

    val ann by NamedActor(::ToDoListOwnerDDT)


    @DDT
    fun `the list owner can add new items`() = ddtScenario {
        setUp {
            ann.`starts with a list`("diy", emptyList())
        }.thenPlay(
            ann.`can add #item to the #listname`("paint the shelf", "diy"),
            ann.`can add #item to the #listname`("fix the gate", "diy"),
            ann.`can add #item to the #listname`("change the lock", "diy"),
            ann.`can see #listname with #itemnames`(
                "diy", listOf(
                    "fix the gate", "paint the shelf", "change the lock"
                )
            )
        )
    }
}